package org.openuss.migration.from20to30;

import java.util.regex.Pattern;

/**
 * Determines from the legacy description of a semester the best fitting period.
 * @author Ingo Dueppe
 *
 */
public class PeriodIdentifier {
	
	public static final long PERMANENT = 21L;
	public static final long SS00 = 22L;
	public static final long WS00_01 = 23L;
	public static final long SS01 = 24L;
	public static final long WS01_02 = 25L;
	public static final long SS02 = 26L;
	public static final long WS02_03 = 27L;
	public static final long SS03 = 28L;
	public static final long WS03_04 = 29L;
	public static final long SS04 = 30L;
	public static final long WS04_05 = 31L;
	public static final long SS05 = 32L;
	public static final long WS05_06 = 33L;
	public static final long SS06 = 34L;
	public static final long WS06_07 = 35L;
	public static final long SS07 = 36L;
	public static final long WS07_08 = 37L;
	
	private static final Pattern patternSS00 = Pattern.compile(".*S.*00([^\\d]*|\\z)",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternWS00_01 = Pattern.compile(".*(00).*[/\\-].*(01).*",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternSS01 = Pattern.compile(".*S.*01([^\\d]*|\\z)",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternWS01_02 = Pattern.compile(".*(01).*[/-].*(02).*",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternSS02 = Pattern.compile(".*S.*02([^\\d]*|\\z)",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternWS02_03 = Pattern.compile(".*(02).*[/-].*(03).*",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternSS03 = Pattern.compile(".*S.*03([^\\d]*|\\z)",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternWS03_04 = Pattern.compile(".*(03).*[/-].*(04).*",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternSS04 = Pattern.compile(".*S.*04([^\\d]*|\\z)",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternWS04_05 = Pattern.compile(".*(04).*[/-].*(05).*",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternSS05 = Pattern.compile(".*S.*05([^\\d]*|\\z)",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternWS05_06 = Pattern.compile(".*(05).*[/-].*(06).*",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternSS06 = Pattern.compile(".*S.*06([^\\d]*|\\z)",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternWS06_07 = Pattern.compile(".*(06).*[/-].*(07).*",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternSS07 = Pattern.compile(".*S.*07([^\\d]*|\\z)",Pattern.CASE_INSENSITIVE);
	private static final Pattern patternWS07_08 = Pattern.compile(".*(07).*[/-].*(08).*",Pattern.CASE_INSENSITIVE);
	
	public long find(String name) {
		
		if (patternSS00.matcher(name).matches()) {
			return SS00;
		} else if (patternWS00_01.matcher(name).matches()){
			return WS00_01;
		} else if (patternSS01.matcher(name).matches()){
			return SS01;
		} else if (patternWS01_02.matcher(name).matches()){
			return WS01_02;
		} else if (patternSS02.matcher(name).matches()) {
			return SS02;
		} else if (patternWS02_03.matcher(name).matches()) {
			return WS02_03;
		} else if (patternSS03.matcher(name).matches()) {
			return SS03;
		} else if (patternWS03_04.matcher(name).matches()) {
			return WS03_04;
		} else if (patternSS04.matcher(name).matches()) {
			return SS04;
		} else if (patternWS03_04.matcher(name).matches()) {
			return WS03_04;
		} else if (patternSS05.matcher(name).matches()) {
			return SS05;
		} else if (patternWS04_05.matcher(name).matches()) {
			return WS04_05;
		} else if (patternSS05.matcher(name).matches()) {
			return SS05;
		} else if (patternWS05_06.matcher(name).matches()) {
			return WS05_06;
		} else if (patternSS06.matcher(name).matches()) {
			return SS06;
		} else if (patternWS06_07.matcher(name).matches()) {
			return WS06_07;
		} else if (patternSS07.matcher(name).matches()) {
			return SS07;
		} else if (patternWS07_08.matcher(name).matches()) {
			return WS07_08;
		} 
		return PERMANENT;
	}

}
