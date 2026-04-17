<%--
Program:
Lang:JSP
Function:開始画面（3つのタイマー表示＋上限入力＋計測スタート）
Note:画面（JSP）と処理（Servlet/Java）を分けて理解できるようにする
Author:Takashi Oikawa
Date:2026/04/17
LastUp:2026/04/17
Update:20260417
--%>

<%--
	===== 初学者向けメモ =====
	この index.jsp は「画面担当」です。
	- 役割：入力フォームを表示し、ボタンで Servlet（/measure）へ送信するだけ。
	- “重い処理” や “時間計測” は JSP ではやりません（Java側に分離）。
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Java時間計測（By openAI）</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
</head>
<body>
	<div class="container">
		<div class="topbar">
			<div>
				<h1 class="title">Java時間計測（By openAI）</h1>
				<p class="subtitle">Servlet + JSP（Tomcat 10+ / Jakarta）で「時間の取り方の違い」を体感する教材</p>
			</div>
		</div>

		<div class="cards">
			<div class="card">
				<h2>TIMER ① OS壁時計</h2>
				<div class="big">00:00:00</div>
				<p class="desc">
					OSが持つ「今の時刻」をミリ秒で扱います。<br>
					開始/終了の“壁時計”を表示します。
				</p>
				<span class="chip">System.currentTimeMillis()</span>
			</div>

			<div class="card">
				<h2>TIMER ② 日本標準時（JST）</h2>
				<div class="big">Asia/Tokyo</div>
				<p class="desc">
					+9時間のタイムゾーンを意識した日時です。<br>
					「地域の時刻」として表示します。
				</p>
				<span class="chip">ZonedDateTime / Asia/Tokyo</span>
			</div>

			<div class="card">
				<h2>TIMER ③ ストップウォッチ</h2>
				<div class="big">0.000 s</div>
				<p class="desc">
					経過時間（差分）を測るのが得意です。<br>
					“今の日時”ではなく“経過”を測ります。
				</p>
				<span class="chip">System.nanoTime()</span>
			</div>
		</div>

		<form class="form-area" method="post" action="<%= request.getContextPath() %>/measure">
			<div class="field">
				<label class="label" for="upperLimit">素数探索 上限値</label>
				<input type="number" id="upperLimit" name="upperLimit" value="10000000" min="1000" step="1000">
				<div class="notice">値を大きくすると処理が重くなり、計測時間が長くなります。</div>
			</div>
			<button class="btn-primary" type="submit">計測スタート</button>
		</form>

		<div class="info-box">
			<h3>各タイマーの特徴</h3>
			<ul>
				<li><strong>OS壁時計</strong>：実際の「現在時刻」。時刻の表示に向きますが、時計合わせ等の影響を受けることがあります。</li>
				<li><strong>日本標準時（JST）</strong>：タイムゾーン（Asia/Tokyo）を明示した日時。国/地域の時刻として扱いやすいです。</li>
				<li><strong>ストップウォッチ</strong>：経過時間（差分）を正確に測る用途向け。処理時間の計測では基本的にこれを使います。</li>
			</ul>
		</div>
	</div>
</body>
</html>
