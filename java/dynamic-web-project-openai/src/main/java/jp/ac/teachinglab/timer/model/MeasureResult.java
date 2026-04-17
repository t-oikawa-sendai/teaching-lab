/*
Program:
Lang:Java
Function:画面表示用の計測結果をまとめて保持するデータクラス
Note:JSPが“必要な情報だけ”取り出せるようにする
Author:Takashi Oikawa
Date:2026/04/17
LastUp:2026/04/17
Update:20260417
*/

package jp.ac.teachinglab.timer.model;

/*
	===== 初学者向けメモ =====
	このクラスは「データ入れ物」です（DTO/Bean のようなもの）。
	- Servlet は計測して結果をこのクラスへ詰める
	- JSP はこのクラスから値を取り出して表示する

	メリット：
	- JSPに計測ロジックを書かずに済み、役割分担がはっきりする
	- 画面に出したい項目が増減しても、Java側でまとめて管理できる
*/

public class MeasureResult {

	// 入力（素数探索の上限値）
	private int upperLimit;
	private long targetSeconds;

	// TIMER ① OS壁時計（ミリ秒）
	private long osStartMillis;
	private long osEndMillis;
	private String osStartText;
	private String osEndText;

	// TIMER ② JST（Asia/Tokyo）
	private String jstZoneId;
	private String jstStartText;
	private String jstEndText;

	// TIMER ③ ストップウォッチ（nanoTime）
	private long stopwatchElapsedNanos;

	// 重い処理（ループ）
	private long loopCount;
	private long checksum;
	private long foundPrimesTotal;
	private int lastPrime;

	/* ===== 画面で便利に使う “計算済み” 値（getterで返す） ===== */

	public long getOsElapsedMillis() {
		return osEndMillis - osStartMillis;
	}

	public long getStopwatchElapsedMillis() {
		return stopwatchElapsedNanos / 1_000_000L;
	}

	public String getStopwatchElapsedSecondsText() {
		// 例: "12.345 s" のように表示
		double seconds = stopwatchElapsedNanos / 1_000_000_000.0;
		return String.format("%.3f s", seconds);
	}

	/* ===== Getter / Setter（JSPから読みやすいように用意） ===== */

	public int getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(int upperLimit) {
		this.upperLimit = upperLimit;
	}

	public long getTargetSeconds() {
		return targetSeconds;
	}

	public void setTargetSeconds(long targetSeconds) {
		this.targetSeconds = targetSeconds;
	}

	public long getOsStartMillis() {
		return osStartMillis;
	}

	public void setOsStartMillis(long osStartMillis) {
		this.osStartMillis = osStartMillis;
	}

	public long getOsEndMillis() {
		return osEndMillis;
	}

	public void setOsEndMillis(long osEndMillis) {
		this.osEndMillis = osEndMillis;
	}

	public String getOsStartText() {
		return osStartText;
	}

	public void setOsStartText(String osStartText) {
		this.osStartText = osStartText;
	}

	public String getOsEndText() {
		return osEndText;
	}

	public void setOsEndText(String osEndText) {
		this.osEndText = osEndText;
	}

	public String getJstZoneId() {
		return jstZoneId;
	}

	public void setJstZoneId(String jstZoneId) {
		this.jstZoneId = jstZoneId;
	}

	public String getJstStartText() {
		return jstStartText;
	}

	public void setJstStartText(String jstStartText) {
		this.jstStartText = jstStartText;
	}

	public String getJstEndText() {
		return jstEndText;
	}

	public void setJstEndText(String jstEndText) {
		this.jstEndText = jstEndText;
	}

	public long getStopwatchElapsedNanos() {
		return stopwatchElapsedNanos;
	}

	public void setStopwatchElapsedNanos(long stopwatchElapsedNanos) {
		this.stopwatchElapsedNanos = stopwatchElapsedNanos;
	}

	public long getLoopCount() {
		return loopCount;
	}

	public void setLoopCount(long loopCount) {
		this.loopCount = loopCount;
	}

	public long getChecksum() {
		return checksum;
	}

	public void setChecksum(long checksum) {
		this.checksum = checksum;
	}

	public long getFoundPrimesTotal() {
		return foundPrimesTotal;
	}

	public void setFoundPrimesTotal(long foundPrimesTotal) {
		this.foundPrimesTotal = foundPrimesTotal;
	}

	public int getLastPrime() {
		return lastPrime;
	}

	public void setLastPrime(int lastPrime) {
		this.lastPrime = lastPrime;
	}
}

