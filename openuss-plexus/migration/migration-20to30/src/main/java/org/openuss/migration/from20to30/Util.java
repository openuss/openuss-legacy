package org.openuss.migration.from20to30;

public class Util {

	/**
	 * Transform character to boolean
	 * @param c - character
	 * @return true if character == '1'
	 */
	public static boolean toBoolean(Character c) {
		return c != null && c == '1';
	}

}
