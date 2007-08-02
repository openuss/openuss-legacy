package org.openuss.web.component.genericflexlist;

import java.io.IOException;
import javax.faces.component.UIOutput;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.util.Iterator;



public class UIGenericFlexList extends UIOutput {
	
	public void encodeBegin(FacesContext context) throws IOException
	{
	
	}
	
	public void encodeEnd(FacesContext context) throws IOException
	{
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("div", this);
		writer.writeAttribute("class", "generic_flexlist", null);
		
			String title = (String)getAttributes().get("title");
			if(title != null)
			{
				writer.startElement("div", this);
					writer.writeAttribute("class", "generic_flexlist_header", null);
					writer.write(title);
				writer.endElement("div");
			}
			
			UIComponent visibleFacet = this.getFacet("visible");
			if(visibleFacet != null) {
				writer.startElement("div", this);
					renderChild(context, visibleFacet);
					visibleFacet.encodeBegin(context);
					visibleFacet.encodeEnd(context);
				writer.endElement("div");
			}
			
			UIComponent hiddenFacet = this.getFacet("hidden");
			if(hiddenFacet != null) {
				writer.startElement("div", this);
					writer.writeAttribute("id", "hidden_items" + this.getId(), null);
					writer.writeAttribute("style", "display:none;", null);
					hiddenFacet.encodeBegin(context);
					hiddenFacet.encodeEnd(context);
				writer.endElement("div");
			}
			
			if(hiddenFacet != null) {
				writer.startElement("div", this);
					writer.writeAttribute("class", "generic_flexlist_footer", null);
					
					writer.startElement("a", this);
						writer.writeAttribute("href", "javascript:void(0)", null);
						writer.writeAttribute("style", "color: #5493D4;", null);
						
						String javascript = "" +
							"new Effect.toggle('show_hidden_items" + this.getId() + "', 'blind', {duration:0.001}); " +
							"new Effect.toggle('hide_hidden_items" + this.getId() + "', 'blind', {duration:0.001}); " +
							"new Effect.toggle('hidden_items" + this.getId() + "', 'blind', {duration:0.001});";
						writer.writeAttribute("onclick", javascript, null);
						
						writer.startElement("span", this);
							writer.writeAttribute("id", "show_hidden_items" + this.getId(), null);
							
							String showButtonTitle = (String)getAttributes().get("showButtonTitle");
							if(showButtonTitle != null)
								writer.write( showButtonTitle );
							else
								writer.write( "More Items...");
							
						writer.endElement("span");
						
						writer.startElement("span", this);
							writer.writeAttribute("id", "hide_hidden_items" + this.getId(), null);
							writer.writeAttribute("style", "display:none;", null);
							
							String hideButtonTitle = (String)getAttributes().get("hideButtonTitle");
							if(hideButtonTitle != null)
								writer.write( hideButtonTitle );
							else
								writer.write("Fewer Items...");
							
						writer.endElement("span");						
					writer.endElement("a");
					
				writer.endElement("div");
			}
			
		writer.endElement("div");
	}
	
	private static void renderChildren(FacesContext facesContext, UIComponent component)
	throws IOException
	{
		if (component.getChildCount() > 0)
		{
			for (Iterator it = component.getChildren().iterator(); it.hasNext(); )
			{
				UIComponent child = (UIComponent)it.next();
				renderChild(facesContext, child);
			}
		}
	}


	private static void renderChild(FacesContext facesContext, UIComponent child)
	throws IOException
	{
		if (!child.isRendered())
		{
			return;
		}


		child.encodeBegin(facesContext);
		if (child.getRendersChildren())
		{
			child.encodeChildren(facesContext);
		}
		else
		{
			renderChildren(facesContext, child);
		}
		child.encodeEnd(facesContext);
	}
}
