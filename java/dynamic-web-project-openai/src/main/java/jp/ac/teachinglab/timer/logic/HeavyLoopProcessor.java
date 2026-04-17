/*
Program:
Lang:Java
Function:重いループ処理（素数探索）を実行し、checksum等を返す
Note:最適化で処理が消えにくいよう、結果を checksum として保持する
Author:Takashi Oikawa
Date:2026/04/17
LastUp:2026/04/17
Update:20260417
*/

package jp.ac.teachinglab.timer.logic;

import java.time.Duration;

public class HeavyLoopProcessor {

	/*
		===== 初学者向けメモ =====
		このクラスは「重い処理担当」です。

		要件：
		- 10〜20秒程度を目標にしたい
		- 固定回数ではなく、経過時間ベースでも良い
		- 最適化で消えにくいよう checksum を使う

		やっていること：
		- 指定された上限値まで、素数を数えます（エラトステネスの篩）。
		- それを “目標時間に達するまで” 繰り返します。
		- 計算のたびに checksum（値が変わる合計のようなもの）を更新し、結果を返します。
	*/

	public HeavyLoopResult runPrimeSearchUntil(int upperLimit, Duration targetDuration) {
		if (upperLimit < 2) {
			throw new IllegalArgumentException("上限値は 2 以上にしてください。");
		}
		if (targetDuration == null || targetDuration.isNegative() || targetDuration.isZero()) {
			throw new IllegalArgumentException("目標時間が不正です。");
		}

		long start = System.nanoTime();
		long targetNanos = targetDuration.toNanos();

		long loopCount = 0;
		long checksum = 0;
		long foundPrimesTotal = 0;
		int lastPrime = 2;

		/*
			ポイント：
			- while の条件は「経過時間」です。
			- 1回の素数探索が短すぎる場合に備えて、最低1回は必ず実行します。
		*/
		do {
			PrimeSearchResult one = countPrimesUpToBySieve(upperLimit);

			loopCount++;
			foundPrimesTotal += one.foundPrimes;
			lastPrime = one.lastPrime;

			/*
				checksum の更新（最適化対策の一例）
				- ループ回数、素数個数、最後の素数を混ぜて値が変わるようにします。
				- これにより「計算しても結果を使っていない」とみなされにくくなります。
			*/
			checksum = checksum ^ (one.foundPrimes * 31L + one.lastPrime * 131L + loopCount * 17L + one.bitMix);

		} while ((System.nanoTime() - start) < targetNanos);

		return new HeavyLoopResult(loopCount, checksum, foundPrimesTotal, lastPrime);
	}

	/**
	 * 2 から upperLimit までの素数を数えます（エラトステネスの篩）。
	 *
	 * 初学者向けメモ:
	 * - 「合成数（素数ではない数）」に印を付けていく方法です。
	 * - すべての数に割り算をするより高速になりやすく、上限 1,000万でも現実的に動きます。
	 */
	private PrimeSearchResult countPrimesUpToBySieve(int upperLimit) {
		// false=素数候補、true=合成数としてマークする配列
		boolean[] composite = new boolean[upperLimit + 1];

		// 0,1 は素数ではない（分かりやすく明示）
		if (upperLimit >= 0) composite[0] = true;
		if (upperLimit >= 1) composite[1] = true;

		/*
			篩の基本:
			- p が素数なら、p*p, p*(p+1), ... を合成数としてマークする
			- p の上限は √upperLimit までで十分
		*/
		int limit = (int) Math.sqrt(upperLimit);
		for (int p = 2; p <= limit; p++) {
			if (!composite[p]) {
				long start = (long) p * (long) p;
				for (long x = start; x <= upperLimit; x += p) {
					composite[(int) x] = true;
				}
			}
		}

		// 素数の数と最後の素数を数える
		long found = 0;
		int lastPrime = 2;

		/*
			最適化対策のための “軽い混ぜ物”
			- composite 配列の一部を使って bitMix を作る（結果として外に出す）
			- これにより「配列の計算が無意味」とみなされにくくします
		*/
		long bitMix = 0;

		for (int n = 2; n <= upperLimit; n++) {
			if (!composite[n]) {
				found++;
				lastPrime = n;
				// 素数の位置を少しだけ混ぜる（軽い処理）
				bitMix ^= (long) n * 0x9E3779B97F4A7C15L;
			}
		}

		return new PrimeSearchResult(found, lastPrime, bitMix);
	}

	/*
		内部用の小さな結果クラス（外に出さない）
	*/
	private static class PrimeSearchResult {
		final long foundPrimes;
		final int lastPrime;
		final long bitMix;

		PrimeSearchResult(long foundPrimes, int lastPrime, long bitMix) {
			this.foundPrimes = foundPrimes;
			this.lastPrime = lastPrime;
			this.bitMix = bitMix;
		}
	}

	/*
		外部（Servlet）に返す結果クラス
		- 1クラスに詰め込みすぎないため、戻り値をまとめて返します。
	*/
	public static class HeavyLoopResult {
		private final long loopCount;
		private final long checksum;
		private final long foundPrimesTotal;
		private final int lastPrime;

		public HeavyLoopResult(long loopCount, long checksum, long foundPrimesTotal, int lastPrime) {
			this.loopCount = loopCount;
			this.checksum = checksum;
			this.foundPrimesTotal = foundPrimesTotal;
			this.lastPrime = lastPrime;
		}

		public long getLoopCount() {
			return loopCount;
		}

		public long getChecksum() {
			return checksum;
		}

		public long getFoundPrimesTotal() {
			return foundPrimesTotal;
		}

		public int getLastPrime() {
			return lastPrime;
		}
	}
}

