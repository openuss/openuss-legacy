package org.openuss.webdav;

import org.openuss.webdav.WebDAVPath.CommonPathRes;

import junit.framework.TestCase;

public class WebDAVPathImplTest extends TestCase {

	public void testWebDAVPathImpl() {
		String path = "/webdav/";
		String toResolve = "rest";
		WebDAVPathImpl davPath  = new WebDAVPathImpl(path, toResolve);
		assertEquals("/webdav/", davPath.getPrefix());
		assertEquals("rest", davPath.getToResolve());
		
		String path1 = "/webdav";
		String toResolve1 = "/rest";
		WebDAVPathImpl davPath1  = new WebDAVPathImpl(path1, toResolve1);
		assertEquals("/webdav/", davPath1.getPrefix());
		assertEquals("rest", davPath1.getToResolve());
		
		String path2 = "//d";
		String toResolve2 = "re/st";
		WebDAVPathImpl davPath2  = new WebDAVPathImpl(path2, toResolve2);
		assertEquals("//d", davPath2.getPrefix());
		assertEquals("re/st", davPath2.getToResolve());
		
		String path3 = "/ה/";
		String toResolve3 = "";
		WebDAVPathImpl davPath3  = new WebDAVPathImpl(path3, toResolve3);
		assertEquals("/ה/", davPath3.getPrefix());
		assertNull(davPath3.getToResolve());
	}

	public void testParse() {
		String prefix = "/webdav/";
		String clientInput = "/webdav/input";
		try {
			WebDAVPathImpl davPath = WebDAVPathImpl.parse(prefix, clientInput);
			assertEquals("/webdav/", davPath.getPrefix());
			assertEquals("input", davPath.getToResolve());
		} catch (WebDAVException e) {
			assertFalse(true);
		}
		
		String prefix1 = "/webdav/";
		String clientInput1 = "input";
		try {
			WebDAVPathImpl.parse(prefix1, clientInput1);
			assertTrue(false);
		} catch (WebDAVException exDAV){
			assertTrue(true); //exspected
		}
		
		String prefix2 = "/webdav";
		String clientInput2 = "/webdav/הצצה";
		try {
			WebDAVPathImpl davPath2 = WebDAVPathImpl.parse(prefix2, clientInput2);
			assertEquals("/webdav/", davPath2.getPrefix());
			assertNotSame("הצצה", davPath2.getToResolve()); //because of decoding
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
		String prefix3 = "/webdav/";
		String clientInput3 = "https://openuss.de/webdav/input";
		try {
			WebDAVPathImpl davPath3 = WebDAVPathImpl.parse(prefix3, clientInput3);
			assertEquals("/webdav/", davPath3.getPrefix());
			assertEquals("input", davPath3.getToResolve());
		} catch (WebDAVException e) {
			assertFalse(true);
		}
		
	}



	public void testGetFileName() {
		String prefix = "/webdav/";
		String clientInput = "/webdav/name.txt";
		try {
			WebDAVPathImpl davPath  = WebDAVPathImpl.parse(prefix, clientInput);
			assertEquals("webdav", davPath.getFileName());
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
		String prefix1 = "/webdav/";
		String clientInput1 = "/webdav/name.txt";
		try {
			WebDAVPathImpl davPath  = WebDAVPathImpl.parse(prefix1, clientInput1);
			WebDAVPath davPath1 = davPath.asResolved();
			assertEquals("name.txt", davPath1.getFileName());
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
		String clientInput2 = "/test/collection/";
		String prefix2 = "/";
		try {
			WebDAVPathImpl davPath2  = WebDAVPathImpl.parse(prefix2, clientInput2);
			assertNull(davPath2.getFileName());
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
	}

	public void testGetFileExt() {
		String prefix = "/webdav/";
		String clientInput = "/webdav/name.txt/";
		try {
			WebDAVPathImpl davPath  = WebDAVPathImpl.parse(prefix, clientInput);
			WebDAVPath davPathResolved = davPath.asResolved();
			assertEquals("txt", davPathResolved.getFileExt());
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
		
		String prefix1 = "/webdav/";
		String clientInput1 = "/webdav/name.txt.exe/";
		try {
			WebDAVPathImpl davPath  = WebDAVPathImpl.parse(prefix1, clientInput1);
			WebDAVPath resolvedPath = davPath.asResolved();
			assertEquals("exe", resolvedPath.getFileExt());
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
		String prefix2 = "/webdav/";
		String clientInput2 = "/webdav/collection/";
		try {
			WebDAVPathImpl davPath  = WebDAVPathImpl.parse(prefix2, clientInput2);
			WebDAVPath resolvedPath = davPath.asResolved();
			assertNull(resolvedPath.getFileExt());
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
	}

	public void testConcat() {
		try {
			String prefix = "/webdav/";
			String clientInput = "/webdav/collection/";
			WebDAVPathImpl path = WebDAVPathImpl.parse(prefix, clientInput);
			WebDAVPath path2 = path.concat("a");
			String correctToResolve = "collection/a";
			assertEquals(correctToResolve, path2.getToResolve());
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
		try {
			String prefix = "/webdav/";
			String clientInput = "/webdav/collection/";
			WebDAVPathImpl path = WebDAVPathImpl.parse(prefix, clientInput);
			WebDAVPath path2 = path.concat("");
			String correctToResolve = "collection/";
			assertEquals(correctToResolve, path2.getToResolve());
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
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

	public void testGetNextName() {
		String prefix = "/webdav/";
		String clientInput = "/webdav/collection/name.txt";
		try {
			WebDAVPathImpl davPath  = WebDAVPathImpl.parse(prefix, clientInput);
			String next = davPath.getNextName();
			assertEquals("collection", next);
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
		String prefix1 = "/webdav/";
		String clientInput1 = "/webdav/collection/name.txt";
		try {
			WebDAVPathImpl davPath  = WebDAVPathImpl.parse(prefix1, clientInput1);
			WebDAVPath pathResolved = davPath.asResolved();
			String next = pathResolved.getNextName();
			assertNull(next);
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
	}

	public void testNext() {
		String prefix = "/webdav";
		String clientInput = "/webdav/collection/name.txt";
		try {
			WebDAVPathImpl davPath  = WebDAVPathImpl.parse(prefix, clientInput);
			WebDAVPath nextPath = davPath.next();
			WebDAVPathImpl correctNextPath = WebDAVPathImpl.parse("/webdav/collection/", "/webdav/collection/name.txt");
			assertTrue(correctNextPath.equals(nextPath));
			
			WebDAVPath nextNextPath = nextPath.next();
			WebDAVPathImpl correctNextNextPath = WebDAVPathImpl.parse("/webdav/collection/", "/webdav/collection/name.txt");
			assertTrue(correctNextNextPath.equals(nextNextPath));
			
			WebDAVPath nextNextNextPath = nextNextPath.next();
			assertNull(nextNextNextPath);
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
		
	}

	public void testCommon() {
		try {
			String prefix = "/a/b/c";
			String clientInput = "/a/b/c/name.txt";
			WebDAVPathImpl davPath  = WebDAVPathImpl.parse(prefix, clientInput);
			
			String prefix1 = "/a/b/d";
			String clientInput1 = "/a/b/d/name.txt";
			WebDAVPathImpl davPath1  = WebDAVPathImpl.parse(prefix1, clientInput1);
			
			CommonPathRes commonPath = davPath.common(davPath1);
			
			WebDAVPath davPathCommon = commonPath.newThis;
			WebDAVPath davPathCommon1 = commonPath.newOther;
			WebDAVPath correctDavPathCommon = WebDAVPathImpl.parse("/a/b/", "/a/b/c/name.txt");
			WebDAVPath correctDavPathCommon1 = WebDAVPathImpl.parse("/a/b/", "/a/b/d/name.txt");
			
			assertTrue(davPathCommon.equals(correctDavPathCommon));
			assertTrue(davPathCommon1.equals(correctDavPathCommon1));
			assertFalse(davPathCommon.equals(davPathCommon1));
		} catch (WebDAVException e) {
			assertTrue(false);
		}
		
		try {
			String prefix = "/webdav/";
			String clientInput = "/webdav/a/b/c";
			WebDAVPathImpl path = WebDAVPathImpl.parse(prefix, clientInput);
			
			String prefix1 = "/test/";
			String clientInput1 = "/test/a/b/d";
			WebDAVPathImpl path1  = WebDAVPathImpl.parse(prefix1, clientInput1);
			
			CommonPathRes commonPath = path.common(path1);
			
			WebDAVPath davPathCommon = commonPath.newThis;
			WebDAVPath davPathCommon1 = commonPath.newOther;
			WebDAVPath correctDavPathCommon = WebDAVPathImpl.parse("/", clientInput);
			WebDAVPath correctDavPathCommon1 = WebDAVPathImpl.parse("/", clientInput1);
			
			assertTrue(davPathCommon.equals(correctDavPathCommon));
			assertTrue(davPathCommon1.equals(correctDavPathCommon1));
			assertFalse(davPathCommon.equals(davPathCommon1));
		} catch (WebDAVException e) {
			assertTrue(false);
		}
	}

	public void testGetCompleteString() {
		WebDAVPath root = WebDAVPathImpl.getRoot("webdav");
		WebDAVPath davPath = root.concat("a");
		davPath = davPath.concat("b");
		String expected = "webdav/a/b";
		String clientString = davPath.getCompleteString();
		assertEquals(expected, clientString);
		
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
		String prefix = davPath.getPrefix();
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
		WebDAVPath root = WebDAVPathImpl.getRoot("webdav");
		assertTrue(root.equals(root.getParent()));
		
		WebDAVPath davPath = root.concat("a");
		davPath = davPath.concat("b");
		String expected = "webdav/a";
		assertEquals(expected, davPath.getParent().getCompleteString());
	}

	public void testStripExtension() {
		String withoutExtension = WebDAVPathImpl.stripExtension("datei.exe");
		String expected = "datei";
		assertEquals(expected, withoutExtension);
		
		withoutExtension = WebDAVPathImpl.stripExtension("datei");
		expected = "datei";
		assertEquals(expected, withoutExtension);
		
		withoutExtension = WebDAVPathImpl.stripExtension(".exe");
		expected = "";
		assertEquals(expected, withoutExtension);
		
		withoutExtension = WebDAVPathImpl.stripExtension("datei.exe.bat");
		expected = "datei.exe";
		assertEquals(expected, withoutExtension);
	}

}
