package org.openuss.webdav;

import java.util.List;

import junit.framework.TestCase;

public class WebDAVPathImplTest extends TestCase {

	public void testParse() throws WebDAVException {
		WebDAVPathImpl davPath = WebDAVPathImpl.parse("/webdav/", "/webdav/input");
		assertEquals("/webdav/", davPath.getResolved());
		assertEquals("input", davPath.getToResolve());
		
		try {
			WebDAVPathImpl.parse("/webdav/", "input");
			assertTrue(false);
		} catch (WebDAVException exDAV){
			; // expected
		}
		
		String prefix2 = "/webdav";
		String clientInput2 = "/webdav/הצצה";
		davPath = WebDAVPathImpl.parse(prefix2, clientInput2);
		assertEquals("/webdav/", davPath.getResolved());
		
		davPath = WebDAVPathImpl.parse("/webdav/", "https://openuss.de/webdav/input");
		assertEquals("/webdav/", davPath.getResolved());
		assertEquals("input", davPath.getToResolve());

		davPath = WebDAVPathImpl.parse("/webdav/", "https://openuss.de/webdav/input");
		assertEquals("/webdav/", davPath.getResolved());
		assertEquals("input", davPath.getToResolve());
	}
	
	public void testGetRoot() {
		String rootStr = "https://example.org/o/p/e/n/u/SSS";
		
		WebDAVPathImpl root = WebDAVPathImpl.getRoot(rootStr);
		rootStr = WebDAVPathImpl.appendSep(rootStr); 
			
		assertTrue(root.isRoot());
		checkRoot(root, rootStr);
		
		checkRoot(root.asFinalPath(), rootStr);
		checkRoot(root.asResolved(), rootStr);
		checkRoot(root.concat(""), rootStr);
		checkRoot(root.concat("/"), rootStr);
		checkRoot(root.concat("x/y").asFinalPath(), rootStr);
		checkRoot(root.concat("xasdd\\fs").getParent().getParent(), rootStr);
		checkRoot(root.concat("x/y").getParent().getParent(), rootStr);
	}
	
	protected void checkRoot(WebDAVPath root, String rootStr) {
		assertTrue(root.isResolved());
		assertTrue(root.getFileName() == null);
		assertTrue(root.getFileExt() == null);
		assertTrue(root.getNextName() == null);
		assertTrue(root.getParent() == root);
		assertEquals(root.getNumberOfElemsToResolve(), 0);
		assertEquals(root.getCompleteString(), rootStr);
		assertEquals(root.getResolved(), rootStr);
		assertEquals(root.getToResolve(), null);
		assertTrue(root.getToResolveList() == null);
	}
	
	public void testGetFileName() throws WebDAVException {
		WebDAVPathImpl davPath  = WebDAVPathImpl.parse("/webdav/",  "/webdav/x/name.txt");
		assertTrue(null == davPath.getFileName());
		
		davPath  = WebDAVPathImpl.parse("/webdav/", "/webdav/name.txt");
		WebDAVPath davPath1 = davPath.asResolved();
		assertEquals("name.txt", davPath1.getFileName());

		WebDAVPathImpl davPath2  = WebDAVPathImpl.parse("/", "/test/collection");
		assertNull(davPath2.getFileName());
	}

	public void testGetFileExt() throws WebDAVException {
		WebDAVPathImpl davPath  = WebDAVPathImpl.parse("/webdav/", "/webdav/name.txt");
		WebDAVPath resolvedPath = davPath.asResolved();
		assertEquals("txt", resolvedPath.getFileExt());
			
		davPath  = WebDAVPathImpl.parse("/webdav/", "/webdav/name.txt/");
		resolvedPath = davPath.asResolved();
		assertEquals("txt", resolvedPath.getFileExt());
		
		davPath  = WebDAVPathImpl.parse("/webdav/", "/webdav/name.txt.exe/");
		resolvedPath = davPath.asResolved();
		assertEquals("exe", resolvedPath.getFileExt());
		
		String prefix2 = "/webdav/";
		String clientInput2 = "/webdav/collection/";
		
		davPath  = WebDAVPathImpl.parse(prefix2, clientInput2);
		resolvedPath = davPath.asResolved();
		assertNull(resolvedPath.getFileExt());
	}

	public void testConcat() throws WebDAVException {
		WebDAVPathImpl path = WebDAVPathImpl.parse("/webdav/", "/webdav/collection/");
		WebDAVPath path2 = path.concat("a");
		assertEquals("collection/a", path2.getToResolve());
	
		path2 = path.concat("");
		assertEquals("collection", path2.getToResolve());
	}

	public void testToClientString() {
		WebDAVPath path = WebDAVPathImpl.getRoot("webdav");
		path = path.concat("a");
		String clientString = path.toClientString();
		String expected = "webdav/";
		assertEquals(expected, clientString);
		
		WebDAVPath resolvedPath = path.asResolved();
		expected = "webdav/a";
		clientString = resolvedPath.toClientString();
		assertEquals(expected, clientString);
		
		WebDAVPath root = WebDAVPathImpl.getRoot("webdav");
		WebDAVPath path2 = root.concat("הצü");
		path2 = path2.asResolved();
		String notExpected = "webdav/הצü";
		clientString = path2.toClientString();
		assertNotSame(notExpected, clientString);
	}

	public void testGetNextName() throws WebDAVException {
		WebDAVPathImpl davPath  = WebDAVPathImpl.parse("/webdav/", "/webdav/collection/name.txt");
		String next = davPath.getNextName();
		assertEquals("collection", next);
		
		WebDAVPath pathResolved = davPath.asResolved();
		next = pathResolved.getNextName();
		assertNull(next);
	}

	public void testNext() throws WebDAVException {
		WebDAVPathImpl davPath  = WebDAVPathImpl.parse("/webdav", "/webdav/collection/name.txt");
		WebDAVPath nextPath = davPath.next();
		WebDAVPathImpl correctNextPath = WebDAVPathImpl.parse("/webdav/collection", "/webdav/collection/name.txt");
		assertEquals(correctNextPath.getCompleteString(), nextPath.getCompleteString());
		assertEquals(correctNextPath.getToResolve(), nextPath.getToResolve());
		
		assert(nextPath.next() == nextPath);
	}

	public void testGetCompleteString() {
		WebDAVPath root = WebDAVPathImpl.getRoot("webdav");
		WebDAVPath davPath = root.concat("a");
		davPath = davPath.concat("b");
		String expected = "webdav/a/b";
		String clientString = davPath.getCompleteString();
		assertEquals(expected, clientString);
		assertEquals(expected, davPath.getResolved() + davPath.getToResolve());
		
		WebDAVPath path = root.concat("הצü");
		path = path.asResolved();
		expected = "webdav/הצü";
		clientString = path.getCompleteString();
		assertEquals(expected, clientString);
	}

	public void testAsFinalPath() {
		WebDAVPath root = WebDAVPathImpl.getRoot("webdav");
		WebDAVPath davPath = root.concat("a");
		davPath = davPath.concat("b");
		davPath = davPath.asFinalPath();
		String expectedPrefix = "webdav/";
		String prefix = davPath.getResolved();
		assertEquals(expectedPrefix, prefix);
		assertNull(davPath.getToResolve());
	}

	public void testGetNumberOfElemsToResolve() {
		WebDAVPath root = WebDAVPathImpl.getRoot("webdav");
		WebDAVPath davPath = root.concat("a");
		davPath = davPath.concat("b");
		assertEquals(2, davPath.getNumberOfElemsToResolve());
	}

	public void testGetParent() {
		WebDAVPath root = WebDAVPathImpl.getRoot("webdav/");
		assertTrue(root.equals(root.getParent()));
		
		WebDAVPath davPath = root.concat("a");
		assertEquals("webdav/", davPath.getParent().getCompleteString());
		assertEquals("webdav/", davPath.getParent().getResolved());
		WebDAVPath davPath2 = davPath.concat("b");
		assertEquals("webdav/a/b", davPath2.getCompleteString());
		
		assertEquals(davPath2.getParent(), davPath);
		assertEquals("webdav/", davPath2.getResolved());
		
		davPath = root.concat("b/x/y");
		assertEquals("webdav/b/x", davPath.getParent().getCompleteString());
	}

	public void testStripExtension() {
		String withoutExtension = WebDAVPathImpl.stripExtension("datei.exe");
		assertEquals("datei", withoutExtension);
		
		withoutExtension = WebDAVPathImpl.stripExtension("datei");
		assertEquals("datei", withoutExtension);
		
		withoutExtension = WebDAVPathImpl.stripExtension(".exe");
		assertEquals("", withoutExtension);
		
		withoutExtension = WebDAVPathImpl.stripExtension("datei.exe.bat");
		assertEquals("datei.exe", withoutExtension);
	}

	public void testPlode() {
		checkPlode("a");
		checkPlode("a/ה/ü\\üa/df");
		checkPlode("");
		assertEquals(WebDAVPathImpl.explode("").size(), 0);
		assertEquals(WebDAVPathImpl.explode("/").size(), 0);
	}
	
	protected void checkPlode(String s) {
		List<String> lst = WebDAVPathImpl.explode(s);
		String s2 = WebDAVPathImpl.implode(lst);
		
		assertEquals(s, s2);
		assertEquals(s2, WebDAVPathImpl.implode(lst));
	}

}
