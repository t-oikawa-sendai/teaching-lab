package com.example.timer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Java 時間計測デモ Servlet
 *
 * 【3種類のタイマー】
 *   ① System.currentTimeMillis() … OSの壁時計（ミリ秒単位）
 *   ② ZonedDateTime / Asia/Tokyo … 日本標準時タイマー
 *   ③ System.nanoTime()          … ストップウォッチ（ナノ秒→ミリ秒換算）
 *
 * 【重い処理】
 *   素数探索（試し割り法）: 2 〜 limit の範囲
 *   limit = 10,000,000 で概ね 10〜20秒（マシンの性能による）
 *
 * 【アクセス方法】
 *   ブラウザで http://localhost:8080/[プロジェクト名]/timer を開く
 */
@WebServlet("/timer")
public class TimerServlet extends HttpServlet {

    // =========================================================
    // GET: HTMLページを返す
    // =========================================================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(buildHtml());
    }

    // =========================================================
    // POST: 重いループを実行して計測結果をJSONで返す
    // =========================================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ---- 上限値をブラウザから受け取る（未指定なら 10,000,000） ----
        int limit = 10_000_000;
        String limitParam = req.getParameter("limit");
        if (limitParam != null && !limitParam.isEmpty()) {
            try {
                limit = Integer.parseInt(limitParam);
            } catch (NumberFormatException e) {
                // 不正な値は無視してデフォルト値を使う
            }
        }

        // =========================================================
        // ① OS壁時計タイマー 開始
        //    System.currentTimeMillis() は
        //    「1970年1月1日 00:00:00 UTC からのミリ秒数」を返す
        //    OSのシステム時刻に依存するため、管理者がOSの時刻を変えると
        //    結果がずれる可能性がある（通常は問題なし）
        // =========================================================
        long wallStart = System.currentTimeMillis();

        // =========================================================
        // ② 日本標準時タイマー 開始
        //    ZonedDateTime はタイムゾーン情報を持つ日時クラス
        //    ZoneId.of("Asia/Tokyo") = UTC+9（日本標準時）を指定
        //    ChronoUnit.MILLIS.between() で差分をミリ秒で取得する
        // =========================================================
        ZonedDateTime jstStart = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));

        // =========================================================
        // ③ ストップウォッチタイマー 開始
        //    System.nanoTime() は「高精度な経過時間計測専用」のカウンター
        //    絶対的な時刻（何時何分）は持たない
        //    OSのNTPによる時刻調整に影響されないため
        //    純粋な経過時間計測には最も正確
        // =========================================================
        long nanoStart = System.nanoTime();

        // ---------------------------------------------------------
        // 【重いループ処理】素数を探す（試し割り法）
        // 2 から limit までの数について、1とその数自身以外で
        // 割り切れなければ「素数」とカウントする
        // ---------------------------------------------------------
        int primeCount = findPrimes(limit);

        // ---- 終了時刻を記録 ----
        long wallEnd  = System.currentTimeMillis();
        ZonedDateTime jstEnd = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        long nanoEnd  = System.nanoTime();

        // ---- 経過時間を計算 ----
        long wallElapsedMs = wallEnd - wallStart;
        long jstElapsedMs  = ChronoUnit.MILLIS.between(jstStart, jstEnd);
        long nanoElapsedMs = (nanoEnd - nanoStart) / 1_000_000L; // ナノ秒→ミリ秒

        // ---- 日時フォーマット（表示用） ----
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

        // ---- JSON形式でブラウザへ返す ----
        resp.setContentType("application/json; charset=UTF-8");
        String json = String.format(
            "{"
            + "\"wallElapsed\":%d,"
            + "\"jstElapsed\":%d,"
            + "\"nanoElapsed\":%d,"
            + "\"primeCount\":%d,"
            + "\"limit\":%d,"
            + "\"jstStartStr\":\"%s\","
            + "\"jstEndStr\":\"%s\""
            + "}",
            wallElapsedMs,
            jstElapsedMs,
            nanoElapsedMs,
            primeCount,
            limit,
            jstStart.format(fmt),
            jstEnd.format(fmt)
        );
        resp.getWriter().write(json);
    }

    // =========================================================
    // 重いループ: 2 〜 limit の素数を数える（試し割り法）
    //
    // 【アルゴリズムの説明】
    //   素数とは「1とその数自身しか約数を持たない数」のこと
    //   ある数nが素数かどうかを確認するには
    //   2 〜 √n までの数で割り切れるか試せばよい（試し割り法）
    //   ※ √n より大きい約数があれば、必ず √n 以下の対の約数もあるため
    //
    // limit=10,000,000 のとき:
    //   素数の個数は 664,579 個（数学的に確認済み）
    //   処理時間の目安: 10〜20秒（Java 21、一般的なPC）
    // =========================================================
    private int findPrimes(int limit) {
        int count = 0;
        for (int n = 2; n <= limit; n++) {
            boolean isPrime = true;
            for (int i = 2; (long) i * i <= n; i++) {
                if (n % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                count++;
            }
        }
        return count;
    }

    // =========================================================
    // HTMLページを組み立てるメソッド
    // =========================================================
    private String buildHtml() {
        return """
            <!DOCTYPE html>
            <html lang="ja">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Java 時間計測デモ</title>
                <style>
                    * { box-sizing: border-box; margin: 0; padding: 0; }
                    body {
                        font-family: 'Segoe UI', 'Helvetica Neue', Arial, sans-serif;
                        background: #0d1117;
                        color: #e6edf3;
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        padding: 48px 20px;
                        min-height: 100vh;
                    }
                    h1 { font-size: 2rem; color: #58a6ff; margin-bottom: 8px; }
                    .subtitle {
                        color: #8b949e;
                        font-size: 0.92rem;
                        margin-bottom: 40px;
                        text-align: center;
                    }

                    /* ---- タイマーカード ---- */
                    .timers {
                        display: flex;
                        gap: 20px;
                        flex-wrap: wrap;
                        justify-content: center;
                        margin-bottom: 36px;
                    }
                    .timer-card {
                        background: #161b22;
                        border: 1px solid #30363d;
                        border-radius: 12px;
                        padding: 28px 28px 22px;
                        width: 290px;
                        text-align: center;
                        transition: border-color 0.4s, box-shadow 0.4s;
                    }
                    .timer-card.running {
                        border-color: #f0883e;
                        box-shadow: 0 0 16px rgba(240,136,62,0.25);
                    }
                    .timer-card.done {
                        border-color: #3fb950;
                        box-shadow: 0 0 16px rgba(63,185,80,0.25);
                    }
                    .timer-num {
                        font-size: 0.75rem;
                        color: #8b949e;
                        letter-spacing: 2px;
                        text-transform: uppercase;
                        margin-bottom: 6px;
                    }
                    .timer-name {
                        font-size: 1.05rem;
                        font-weight: 700;
                        color: #79c0ff;
                        margin-bottom: 20px;
                    }
                    .timer-value {
                        font-size: 2.4rem;
                        font-weight: 700;
                        color: #f0883e;
                        font-family: 'Courier New', monospace;
                        margin-bottom: 14px;
                        transition: color 0.4s;
                    }
                    .timer-card.done .timer-value { color: #3fb950; }
                    .timer-unit { font-size: 1rem; color: #8b949e; }
                    .timer-detail {
                        font-size: 0.78rem;
                        color: #6e7681;
                        line-height: 1.7;
                        margin-bottom: 12px;
                    }
                    .timer-api {
                        background: #0d1117;
                        border: 1px solid #21262d;
                        border-radius: 6px;
                        padding: 6px 10px;
                        font-size: 0.72rem;
                        color: #79c0ff;
                        font-family: 'Courier New', monospace;
                        display: inline-block;
                    }

                    /* ---- コントロールエリア ---- */
                    .controls {
                        display: flex;
                        align-items: center;
                        gap: 16px;
                        flex-wrap: wrap;
                        justify-content: center;
                        margin-bottom: 20px;
                    }
                    .limit-group {
                        display: flex;
                        align-items: center;
                        gap: 8px;
                        background: #161b22;
                        border: 1px solid #30363d;
                        border-radius: 8px;
                        padding: 8px 14px;
                    }
                    .limit-group label { font-size: 0.85rem; color: #8b949e; }
                    .limit-group input {
                        background: #0d1117;
                        border: 1px solid #30363d;
                        border-radius: 6px;
                        color: #e6edf3;
                        font-size: 0.9rem;
                        padding: 4px 10px;
                        width: 130px;
                        text-align: right;
                    }
                    .btn {
                        background: #238636;
                        color: white;
                        border: 1px solid #2ea043;
                        padding: 12px 36px;
                        font-size: 1rem;
                        border-radius: 8px;
                        cursor: pointer;
                        font-weight: 700;
                        transition: background 0.2s;
                    }
                    .btn:hover:not(:disabled) { background: #2ea043; }
                    .btn:disabled { background: #21262d; color: #6e7681; border-color: #30363d; cursor: not-allowed; }

                    /* ---- ステータスとプログレス ---- */
                    .status {
                        color: #8b949e;
                        font-size: 0.88rem;
                        margin-bottom: 20px;
                        height: 22px;
                    }
                    .progress-bar {
                        width: 500px;
                        max-width: 90vw;
                        height: 4px;
                        background: #21262d;
                        border-radius: 4px;
                        overflow: hidden;
                        margin-bottom: 30px;
                    }
                    .progress-fill {
                        height: 100%;
                        background: #f0883e;
                        width: 0%;
                        transition: width 0.1s linear;
                        border-radius: 4px;
                    }

                    /* ---- 結果サマリー ---- */
                    .result-box {
                        background: #161b22;
                        border: 1px solid #3fb950;
                        border-radius: 10px;
                        padding: 18px 28px;
                        text-align: center;
                        font-size: 0.9rem;
                        color: #8b949e;
                        display: none;
                        max-width: 600px;
                        line-height: 1.8;
                    }
                    .highlight { color: #ffa657; font-weight: 700; }
                    .green { color: #3fb950; font-weight: 700; }

                    /* ---- 凡例 ---- */
                    .legend {
                        margin-top: 40px;
                        background: #161b22;
                        border: 1px solid #21262d;
                        border-radius: 10px;
                        padding: 20px 28px;
                        max-width: 860px;
                        width: 100%;
                    }
                    .legend h3 { font-size: 0.9rem; color: #8b949e; margin-bottom: 14px; letter-spacing: 1px; }
                    .legend-item {
                        display: flex;
                        gap: 12px;
                        margin-bottom: 10px;
                        font-size: 0.83rem;
                        line-height: 1.5;
                    }
                    .legend-badge {
                        background: #0d1117;
                        border: 1px solid #30363d;
                        border-radius: 4px;
                        padding: 2px 8px;
                        color: #79c0ff;
                        font-family: 'Courier New', monospace;
                        white-space: nowrap;
                        font-size: 0.75rem;
                        align-self: flex-start;
                        margin-top: 2px;
                    }
                    .legend-desc { color: #8b949e; }
                    .legend-desc strong { color: #e6edf3; }
                </style>
            </head>
            <body>

                <h1>⏱ Java 時間計測デモ</h1>
                <p class="subtitle">
                    素数探索ループ（試し割り法）を 3種類のタイマーで計測します<br>
                    上限値 10,000,000 のとき 目安 10〜20秒（PCの性能による）
                </p>

                <!-- タイマーカード -->
                <div class="timers">
                    <!-- ① OS壁時計 -->
                    <div class="timer-card" id="card1">
                        <div class="timer-num">Timer ①</div>
                        <div class="timer-name">OS 壁時計</div>
                        <div class="timer-value" id="val1">
                            <span id="v1">--.---</span>
                            <span class="timer-unit">秒</span>
                        </div>
                        <div class="timer-detail">
                            OSのシステム時刻を利用<br>
                            精度: ミリ秒（1/1000秒）
                        </div>
                        <span class="timer-api">System.currentTimeMillis()</span>
                    </div>

                    <!-- ② JST -->
                    <div class="timer-card" id="card2">
                        <div class="timer-num">Timer ②</div>
                        <div class="timer-name">日本標準時 (JST)</div>
                        <div class="timer-value" id="val2">
                            <span id="v2">--.---</span>
                            <span class="timer-unit">秒</span>
                        </div>
                        <div class="timer-detail">
                            開始: <span id="jst-start">--:--:--.---</span><br>
                            終了: <span id="jst-end">--:--:--.---</span>
                        </div>
                        <span class="timer-api">ZonedDateTime / Asia/Tokyo</span>
                    </div>

                    <!-- ③ ストップウォッチ -->
                    <div class="timer-card" id="card3">
                        <div class="timer-num">Timer ③</div>
                        <div class="timer-name">ストップウォッチ</div>
                        <div class="timer-value" id="val3">
                            <span id="v3">--.---</span>
                            <span class="timer-unit">秒</span>
                        </div>
                        <div class="timer-detail">
                            高精度な経過時間計測<br>
                            精度: ナノ秒（→ミリ秒換算）
                        </div>
                        <span class="timer-api">System.nanoTime()</span>
                    </div>
                </div>

                <!-- コントロール -->
                <div class="controls">
                    <div class="limit-group">
                        <label for="limitInput">素数探索 上限値:</label>
                        <input type="number" id="limitInput" value="10000000" min="1000000" max="50000000" step="1000000">
                    </div>
                    <button class="btn" id="startBtn" onclick="startMeasurement()">▶ 計測スタート</button>
                </div>

                <div class="status" id="status">ボタンを押すと計測を開始します</div>

                <div class="progress-bar">
                    <div class="progress-fill" id="progressFill"></div>
                </div>

                <div class="result-box" id="resultBox"></div>

                <!-- 凡例 -->
                <div class="legend">
                    <h3>📖 各タイマーの特徴</h3>
                    <div class="legend-item">
                        <span class="legend-badge">currentTimeMillis</span>
                        <div class="legend-desc">
                            <strong>OSの壁時計</strong> — 1970/1/1からの経過ミリ秒。一番よく使われる。
                            NTP（インターネット時刻同期）でOSの時刻が変わると影響を受ける場合がある。
                        </div>
                    </div>
                    <div class="legend-item">
                        <span class="legend-badge">ZonedDateTime</span>
                        <div class="legend-desc">
                            <strong>タイムゾーン付き時刻</strong> — Asia/Tokyo (UTC+9) を明示指定。
                            内部的には currentTimeMillis と同じ時計を使っているため差はほぼゼロ。
                            タイムゾーンを意識した計算に使う。
                        </div>
                    </div>
                    <div class="legend-item">
                        <span class="legend-badge">nanoTime</span>
                        <div class="legend-desc">
                            <strong>ストップウォッチ専用</strong> — 絶対時刻は持たず「差分のみ」計測する。
                            NTPの影響を受けない高精度カウンター。
                            パフォーマンス計測には nanoTime が最も正確。
                        </div>
                    </div>
                </div>

                <script>
                    let liveInterval = null;
                    let browserStart = 0;
                    let fakeProgress = 0;
                    let progressInterval = null;

                    function startMeasurement() {
                        const btn = document.getElementById('startBtn');
                        const limit = document.getElementById('limitInput').value || 10000000;

                        btn.disabled = true;
                        document.getElementById('status').textContent = '⏳ サーバーで処理中... しばらくお待ちください';
                        document.getElementById('resultBox').style.display = 'none';

                        // カードをrunning状態に
                        ['card1','card2','card3'].forEach(id => {
                            document.getElementById(id).className = 'timer-card running';
                        });

                        // ブラウザ側のライブカウンター（参考用 — 実際の計測はサーバー側）
                        browserStart = performance.now();
                        liveInterval = setInterval(() => {
                            const s = ((performance.now() - browserStart) / 1000).toFixed(3);
                            ['v1','v2','v3'].forEach(id => {
                                document.getElementById(id).textContent = s;
                            });
                        }, 100);

                        // フェイクプログレスバー（処理中の雰囲気を出すだけ）
                        fakeProgress = 0;
                        document.getElementById('progressFill').style.width = '0%';
                        progressInterval = setInterval(() => {
                            fakeProgress += (100 - fakeProgress) * 0.012;
                            document.getElementById('progressFill').style.width = fakeProgress + '%';
                        }, 200);

                        // サーバーへPOSTリクエスト（計測依頼）
                        fetch(window.location.pathname, {
                            method: 'POST',
                            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                            body: 'limit=' + limit
                        })
                        .then(r => r.json())
                        .then(data => {
                            // ライブカウンターを止めて結果を表示
                            clearInterval(liveInterval);
                            clearInterval(progressInterval);
                            document.getElementById('progressFill').style.width = '100%';

                            // サーバー計測結果を表示
                            const w = (data.wallElapsed / 1000).toFixed(3);
                            const j = (data.jstElapsed  / 1000).toFixed(3);
                            const n = (data.nanoElapsed / 1000).toFixed(3);

                            document.getElementById('v1').textContent = w;
                            document.getElementById('v2').textContent = j;
                            document.getElementById('v3').textContent = n;

                            document.getElementById('jst-start').textContent = data.jstStartStr;
                            document.getElementById('jst-end').textContent   = data.jstEndStr;

                            // カードをdone状態に
                            ['card1','card2','card3'].forEach(id => {
                                document.getElementById(id).className = 'timer-card done';
                            });

                            document.getElementById('status').textContent = '✅ 計測完了！';

                            // 結果サマリーを表示
                            const diff = Math.max(
                                Math.abs(data.wallElapsed - data.nanoElapsed),
                                Math.abs(data.jstElapsed  - data.nanoElapsed)
                            );
                            const rb = document.getElementById('resultBox');
                            rb.style.display = 'block';
                            rb.innerHTML =
                                `発見した素数の数: <span class="highlight">${data.primeCount.toLocaleString()} 個</span>（2 〜 ${parseInt(data.limit).toLocaleString()} の範囲）<br>` +
                                `3タイマーの最大誤差: <span class="green">${diff} ms</span> — ` +
                                `この値が数ms以内なら正常です`;

                            btn.disabled = false;
                            btn.textContent = '▶ もう一度計測';
                        })
                        .catch(err => {
                            clearInterval(liveInterval);
                            clearInterval(progressInterval);
                            document.getElementById('status').textContent = '❌ エラー: ' + err;
                            btn.disabled = false;
                        });
                    }
                </script>

            </body>
            </html>
            """;
    }
}
