<%--
Program:
Lang:JSP
Function:結果画面（計測結果の表示）
Note:Servletが作成した結果（MeasureResult）を受け取り表示する
Author:Takashi Oikawa
Date:2026/04/17
LastUp:2026/04/17
Update:20260417
--%>

<%--
	===== 初学者向けメモ =====
	この result.jsp は「画面担当」です。
	- 役割：Servlet から渡された “結果データ” を読み取り、見やすく表示する。
	- JSPの中で重い処理や計測はしません（バグの元＆学習しづらい）。
	- /WEB-INF 配下に置くことで、JSPを直接URLで開けないようにしています。
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="jp.ac.teachinglab.timer.model.MeasureResult" %>
<%
	MeasureResult result = (MeasureResult) request.getAttribute("result");
	String errorMessage = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>計測結果 - Java時間計測（By openAI）</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
</head>
<body>
	<div class="container">
		<div class="topbar">
			<div>
				<h1 class="title">計測結果</h1>
				<p class="subtitle">開始/終了の時刻と、ストップウォッチの経過時間を表示します</p>
			</div>
			<div>
				<a class="btn-secondary" href="<%= request.getContextPath() %>/index.jsp">開始画面へ戻る</a>
			</div>
		</div>

		<% if (errorMessage != null) { %>
			<div class="error"><%= errorMessage %></div>
		<% } %>

		<% if (result != null) { %>
		<div class="cards">
			<div class="card">
				<h2>TIMER ① OS壁時計</h2>
				<div class="big"><%= result.getOsElapsedMillis() %> ms</div>
				<p class="desc">OSの壁時計（ミリ秒）で開始/終了を記録し、差分も計算しています。</p>
				<span class="chip">System.currentTimeMillis()</span>
				<div class="divider"></div>
				<dl class="kv">
					<dt>開始（OS壁時計）</dt><dd><%= result.getOsStartText() %></dd>
					<dt>終了（OS壁時計）</dt><dd><%= result.getOsEndText() %></dd>
				</dl>
			</div>

			<div class="card">
				<h2>TIMER ② 日本標準時（JST）</h2>
				<div class="big"><%= result.getJstZoneId() %></div>
				<p class="desc">Asia/Tokyo の “地域の時刻” として開始/終了を表示します。</p>
				<span class="chip">ZonedDateTime / Asia/Tokyo</span>
				<div class="divider"></div>
				<dl class="kv">
					<dt>開始（JST）</dt><dd><%= result.getJstStartText() %></dd>
					<dt>終了（JST）</dt><dd><%= result.getJstEndText() %></dd>
				</dl>
			</div>

			<div class="card">
				<h2>TIMER ③ ストップウォッチ</h2>
				<div class="big"><%= result.getStopwatchElapsedSecondsText() %></div>
				<p class="desc">System.nanoTime() の差分で「経過時間」を測ります（処理時間計測の基本）。</p>
				<span class="chip">System.nanoTime()</span>
				<div class="divider"></div>
				<dl class="kv">
					<dt>経過（ns）</dt><dd><%= result.getStopwatchElapsedNanos() %></dd>
					<dt>経過（ms換算）</dt><dd><%= result.getStopwatchElapsedMillis() %> ms</dd>
				</dl>
			</div>
		</div>

		<div class="info-box">
			<h3>実行した重い処理（素数探索）</h3>
			<div class="grid-2">
				<div class="card">
					<h2>入力条件</h2>
					<div class="big"><%= result.getUpperLimit() %></div>
					<p class="desc">「素数探索 上限値」（index.jsp の入力欄）です。</p>
					<span class="chip">ユーザー入力</span>
					<div class="divider"></div>
					<dl class="kv">
						<dt>上限値</dt><dd><%= result.getUpperLimit() %></dd>
						<dt>目標時間</dt><dd><%= result.getTargetSeconds() %> 秒（目安）</dd>
					</dl>
				</div>

				<div class="card">
					<h2>処理結果</h2>
					<div class="big">checksum=<%= result.getChecksum() %></div>
					<p class="desc">最適化で処理が消えにくいよう、計算結果を checksum として残します。</p>
					<span class="chip">HeavyLoopProcessor</span>
					<div class="divider"></div>
					<dl class="kv">
						<dt>ループ回数</dt><dd><%= result.getLoopCount() %></dd>
						<dt>見つけた素数（合計）</dt><dd><%= result.getFoundPrimesTotal() %></dd>
						<dt>最後に見つけた素数</dt><dd><%= result.getLastPrime() %></dd>
					</dl>
				</div>
			</div>
			<p class="notice">
				※ 計測時間はPC性能や負荷で変動します。目安として 10〜20秒程度になるように調整しています。
			</p>
		</div>
		<% } %>
	</div>
</body>
</html>
