/*
Program:
Lang:Java
Function:System.nanoTime() で経過時間（ストップウォッチ）を測る
Note:日時ではなく「処理時間の差分」を測る用途向け
Author:Takashi Oikawa
Date:2026/04/17
LastUp:2026/04/17
Update:20260417
*/

package jp.ac.teachinglab.timer.util;

public class StopwatchUtil {

	/*
		初学者向けメモ:
		- System.nanoTime() は「現在日時」ではなく「単調増加する時間」です。
		- そのため “差分（経過時間）” を測るのに向いています。
		- start の値と end の値を引けば、経過ナノ秒が得られます。
	*/

	private long startNanos;
	private long endNanos;
	private boolean running;

	public StopwatchUtil() {
		this.running = false;
	}

	/**
	 * 計測開始
	 */
	public void start() {
		this.startNanos = System.nanoTime();
		this.endNanos = this.startNanos;
		this.running = true;
	}

	/**
	 * 計測終了
	 */
	public void stop() {
		this.endNanos = System.nanoTime();
		this.running = false;
	}

	/**
	 * 経過ナノ秒を返します。
	 * running 中は「今までの経過」を返します。
	 */
	public long getElapsedNanos() {
		long end = running ? System.nanoTime() : endNanos;
		return end - startNanos;
	}
}

