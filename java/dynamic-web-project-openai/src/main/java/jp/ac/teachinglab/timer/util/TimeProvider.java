/*
Program:
Lang:Java
Function:OS壁時計と、日本時間（Asia/Tokyo）の現在時刻を取得・整形する
Note:「時刻取得」はServletから分離して見通しを良くする
Author:Takashi Oikawa
Date:2026/04/17
LastUp:2026/04/17
Update:20260417
*/

package jp.ac.teachinglab.timer.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeProvider {

	/*
		初学者向けメモ:
		- 「OS壁時計」は System.currentTimeMillis() で取れます。
		  これは “1970/01/01 00:00:00 UTC からの経過ミリ秒” です。
		- 「JST（Asia/Tokyo）」のようにタイムゾーンを持つ日時は ZonedDateTime が便利です。
	*/

	private final ZoneId jstZone;

	// 表示用のフォーマット（例: 2026-04-17 12:34:56.789 +09:00[Asia/Tokyo]）
	private final DateTimeFormatter zonedFormatter =
			DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSS XXX'['VV']'");

	public TimeProvider(ZoneId jstZone) {
		this.jstZone = jstZone;
	}

	/**
	 * OS壁時計の現在時刻（ミリ秒）を返します。
	 */
	public long getOsCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * JST（Asia/Tokyo）の現在時刻を返します。
	 */
	public ZonedDateTime getJstNow() {
		return ZonedDateTime.now(jstZone);
	}

	/**
	 * OS壁時計（ミリ秒）を “読みやすい文字列” にします。
	 * ※ OS壁時計はタイムゾーン情報を持っていないため、ここでは Instant を経由してJSTで表示します。
	 */
	public String formatOsMillis(long epochMillis) {
		Instant instant = Instant.ofEpochMilli(epochMillis);
		ZonedDateTime jstDateTime = ZonedDateTime.ofInstant(instant, jstZone);
		return formatZonedDateTime(jstDateTime);
	}

	/**
	 * ZonedDateTime を “読みやすい文字列” にします。
	 */
	public String formatZonedDateTime(ZonedDateTime dateTime) {
		return dateTime.format(zonedFormatter);
	}
}

