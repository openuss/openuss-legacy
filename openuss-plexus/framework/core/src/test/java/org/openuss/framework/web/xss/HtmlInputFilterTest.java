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
	
	public void testEditor() {
		String tmp = "<font color=\"#ff0000\">aaaaaaaaa</font><br /><span style=" +
				"\"background-color: rgb(255, 204, 0);\">asasa</span><br />" +
				"<span style=\"background-color: rgb(255, 153, 204);\">sasa" +
				"s </span>a <strong><em>pokdsa </em></strong>k dap <font co" +
				"lor=\"#ff0000\">pokds </font><strong>aposkdpoasdk&nbsp; </" +
				"strong><img src=\"/openuss-plexus/fckfaces/FCKeditor/edito" +
				"r/images/smiley/msn/shades_smile.gif\" alt=\"\" /><br /><i" +
				"mg src=\"/openuss-plexus/fckfaces/FCKeditor/editor/images/" +
				"smiley/msn/angel_smile.gif\" alt=\"\" /><br /><br style=\"" +
				"background-color: rgb(255, 255, 153);\" /><ol style=\"back" +
				"ground-color: rgb(255, 255, 153);\"> <li>adsadasdasdsaasad" +
				"sad</li></ol><br /><ul><li>asdsad<ul><li>sadsadasd</li><li" +
				">asdasd</li></ul></li><li>asdsad</li></ul><ol><li>asdsad</" +
				"li><li>asdsad<ol><li>asdasad</li><li>sad</li><li>sadsad</l" +
				"i><li>asdsad</li></ol></li><li>asdasd</li></ol>centrum<br " +
				"/>right<br />justify qwewqe wqewqe<br />qwewqe<br /><br />" +
				"<a style=\"color:#00f;\" color=\"rgb(120,120,0)\" href=\"h" +
				"ttp://link\">link</a><br /><br /><br />qwe<br />";
		
		test(tmp,tmp);
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
