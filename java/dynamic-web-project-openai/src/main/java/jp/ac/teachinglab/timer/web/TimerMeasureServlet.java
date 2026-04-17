/*
Program:
Lang:Java
Function:画面からの要求を受け、重い処理を実行し、計測結果をJSPへ渡す
Note:Servlet（処理担当）。JSP（画面担当）とは役割分担する
Author:Takashi Oikawa
Date:2026/04/17
LastUp:2026/04/17
Update:20260417
*/

package jp.ac.teachinglab.timer.web;

/*
	===== 初学者向けメモ =====
	Servletは「ブラウザからの要求（HTTPリクエスト）」を受け取って処理する“入口”です。

	今回の流れ：
	1) index.jsp で入力（上限値）→ ボタン押下
	2) /measure に POST される
	3) この Servlet が受け取り、重い処理を実行し、各タイマーで計測する
	4) 結果を result.jsp に渡して表示する（forward）

	ポイント：
	- JSPは表示だけ。計測や重い処理はJavaクラス側に分割します。
	- 1クラスに詰め込まないため、時間取得・ストップウォッチ・重い処理は別クラスに分けます。
*/

import java.io.IOException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.ac.teachinglab.timer.logic.HeavyLoopProcessor;
import jp.ac.teachinglab.timer.logic.HeavyLoopProcessor.HeavyLoopResult;
import jp.ac.teachinglab.timer.model.MeasureResult;
import jp.ac.teachinglab.timer.util.StopwatchUtil;
import jp.ac.teachinglab.timer.util.TimeProvider;

public class TimerMeasureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
		“10〜20秒程度” を狙うための目標時間です。
		PC性能で前後するので、必要なら値を変えてOKです。
	*/
	private static final Duration TARGET_DURATION = Duration.ofSeconds(12);

	/*
		日本時間（JST）のタイムゾーンIDです。
		ZoneId.of("Asia/Tokyo") の文字列は、この教材で何度も出てくるので覚えると便利です。
	*/
	private static final ZoneId JST = ZoneId.of("Asia/Tokyo");

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 文字化け対策（フォームの日本語ラベル等があるため、最初に設定）
		request.setCharacterEncoding("UTF-8");

		// 1) 入力値の取得（未入力や不正値の場合はデフォルトを使う）
		int upperLimit = parseUpperLimitOrDefault(request.getParameter("upperLimit"), 10_000_000);

		// 2) 時刻取得クラス（壁時計/JST）とストップウォッチを準備
		TimeProvider timeProvider = new TimeProvider(JST);
		StopwatchUtil stopwatch = new StopwatchUtil();

		// 3) 計測開始（壁時計/JST/ストップウォッチ）
		long osStartMillis = timeProvider.getOsCurrentTimeMillis();
		ZonedDateTime jstStart = timeProvider.getJstNow();
		stopwatch.start();

		// 4) 重い処理（素数探索）を実行
		HeavyLoopProcessor processor = new HeavyLoopProcessor();
		HeavyLoopResult heavyResult;
		try {
			heavyResult = processor.runPrimeSearchUntil(upperLimit, TARGET_DURATION);
		} catch (IllegalArgumentException ex) {
			// 不正入力により処理できない場合は、開始画面へ戻すのではなく結果画面でエラーを表示
			request.setAttribute("errorMessage", "入力値が不正です: " + ex.getMessage());
			forwardToResult(request, response);
			return;
		}

		// 5) 計測終了
		long osEndMillis = timeProvider.getOsCurrentTimeMillis();
		ZonedDateTime jstEnd = timeProvider.getJstNow();
		stopwatch.stop();

		// 6) 画面表示用の結果データにまとめる（JSPが扱いやすい形に整える）
		MeasureResult result = new MeasureResult();
		result.setUpperLimit(upperLimit);
		result.setTargetSeconds(TARGET_DURATION.toSeconds());

		result.setOsStartMillis(osStartMillis);
		result.setOsEndMillis(osEndMillis);
		result.setOsStartText(timeProvider.formatOsMillis(osStartMillis));
		result.setOsEndText(timeProvider.formatOsMillis(osEndMillis));

		result.setJstZoneId(JST.getId());
		result.setJstStartText(timeProvider.formatZonedDateTime(jstStart));
		result.setJstEndText(timeProvider.formatZonedDateTime(jstEnd));

		result.setStopwatchElapsedNanos(stopwatch.getElapsedNanos());

		result.setLoopCount(heavyResult.getLoopCount());
		result.setChecksum(heavyResult.getChecksum());
		result.setFoundPrimesTotal(heavyResult.getFoundPrimesTotal());
		result.setLastPrime(heavyResult.getLastPrime());

		request.setAttribute("result", result);
		forwardToResult(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
			初学者向けメモ:
			- ブラウザで /measure を直接開いた場合（GET）にも対応します。
			- 本来は index.jsp のフォーム（POST）から使いますが、迷子になりにくいよう開始画面へ誘導します。
		*/
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

	private void forwardToResult(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
		dispatcher.forward(request, response);
	}

	private int parseUpperLimitOrDefault(String value, int defaultValue) {
		if (value == null || value.isBlank()) {
			return defaultValue;
		}
		try {
			int parsed = Integer.parseInt(value.trim());
			if (parsed < 1_000) {
				// 小さすぎるとすぐ終わってしまい「重い処理」になりにくいので最低値を決めます
				return 1_000;
			}
			return parsed;
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}
}

