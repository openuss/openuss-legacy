package org.openuss.framework.web.xss;

import junit.framework.Assert;
import junit.framework.TestCase;

public class HtmlInputFilterTest extends TestCase {
	protected HtmlInputFilter vFilter;

	protected void setUp() {
		vFilter = new HtmlInputFilter(true);
	}

	protected void tearDown() {
		vFilter = null;
	}

	private void test(String input, String result) {
		Assert.assertEquals(result, vFilter.filter(input));
	}

	public void test_basics() {
		test("", "");
		test("hello", "hello");
	}

	public void test_balancing_tags() {
		test("<b>hello", "<b>hello</b>");
		test("<b>hello", "<b>hello</b>");
		test("hello<b>", "hello");
		test("hello</b>", "hello");
		test("hello<b/>", "hello");
		test("<b><b><b>hello", "<b><b><b>hello</b></b></b>");
		test("</b><b>", "");
	}

	public void test_end_slashes() {
		test("<img>", "<img />");
		test("<img/>", "<img />");
		test("<b/></b>", "");
	}

	public void test_balancing_angle_brackets() {
		if (HtmlInputFilter.ALWAYS_MAKE_TAGS) {
			test("<img src=\"foo\"", "<img src=\"foo\" />");
			test("i>", "");
			test("<img src=\"foo\"/", "<img src=\"foo\" />");
			test(">", "");
			test("foo<b", "foo");
			test("b>foo", "<b>foo</b>");
			test("><b", "");
			test("b><", "");
			test("><b>", "");
		} else {
			test("<img src=\"foo\"", "&lt;img src=\"foo\"");
			test("b>", "b&gt;");
			test("<img src=\"foo\"/", "&lt;img src=\"foo\"/");
			test(">", "&gt;");
			test("foo<b", "foo&lt;b");
			test("b>foo", "b&gt;foo");
			test("><b", "&gt;&lt;b");
			test("b><", "b&gt;&lt;");
			test("><b>", "&gt;");
		}
	}

	public void test_attributes() {
		test("<img src=foo>", "<img src=\"foo\" />");
		test("<img asrc=foo>", "<img />");
		test("<img src=test test>", "<img src=\"test\" />");
		test("<a href=\"test\">link</a>", "<a href=\"test\">link</a>" );
	}

	public void test_disallow_script_tags() {
		test("<script>", "");
		if (HtmlInputFilter.ALWAYS_MAKE_TAGS) {
			test("<script", "");
		} else {
			test("<script", "&lt;script");
		}
		test("<script/>", "");
		test("</script>", "");
		test("<script woo=yay>", "");
		test("<script woo=\"yay\">", "");
		test("<script woo=\"yay>", "");
		test("<script woo=\"yay<b>", "");
		test("<script<script>>", "");
		test("<<script>script<script>>", "script");
		test("<<script><script>>", "");
		test("<<script>script>>", "");
		test("<<script<script>>", "");
	}

	public void test_protocols() {
		test("<a href=\"http://foo\">bar</a>", "<a href=\"http://foo\">bar</a>");
		// we don't allow ftp. t("<a href=\"ftp://foo\">bar</a>", "<a
		// href=\"ftp://foo\">bar</a>");
		test("<a href=\"mailto:foo\">bar</a>", "<a href=\"mailto:foo\">bar</a>");
		test("<a href=\"javascript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
		test("<a href=\"java script:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
		test("<a href=\"java\tscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
		test("<a href=\"java\nscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
		test("<a href=\"java" + String.valueOf((char)1) + "script:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
		test("<a href=\"jscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
		test("<a href=\"vbscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
		test("<a href=\"view-source:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
	}

	public void test_self_closing_tags() {
		test("<img src=\"a\">", "<img src=\"a\" />");
		test("<img src=\"a\">foo</img>", "<img src=\"a\" />foo");
		test("</img>", "");
	}

	public void test_comments() {
		if (HtmlInputFilter.STRIP_COMMENTS) {
			test("<!-- a<b --->", "");
		} else {
			test("<!-- a<b --->", "<!-- a&lt;b --->");
		}
	}

}
