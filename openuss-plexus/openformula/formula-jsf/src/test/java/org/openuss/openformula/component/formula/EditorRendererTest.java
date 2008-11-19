package org.openuss.openformula.component.formula;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;

/**
 * Class to test the behaviour of the SayHello renderer
 */
public class EditorRendererTest extends AbstractJsfTestCase {

	private MockResponseWriter writer;
	private Editor editor;

	public EditorRendererTest(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		super.setUp();
		
		editor = new Editor();
		editor.setValue("<xmath></xmath>");
		editor.setId("editorId");
		editor.setWidth("200");
		editor.setHeight("200");

		writer = new MockResponseWriter(new StringWriter(), null, null);
		facesContext.setResponseWriter(writer);
		facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
		facesContext.getRenderKit().addRenderer(editor.getFamily(), editor.getRendererType(), new EditorRenderer());
	}

	public void tearDown() throws Exception {
		super.tearDown();
		editor = null;
		writer = null;
	}

	public void testEncodeEnd() throws IOException {
		editor.encodeEnd(facesContext);
		facesContext.renderResponse();    

		String output = writer.getWriter().toString();  
		assertTrue(output.contains("editorId"));
		   
		assertFalse(output.contains("${clientId}"));
	}

}
