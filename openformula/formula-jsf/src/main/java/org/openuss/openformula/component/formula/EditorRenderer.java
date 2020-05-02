package org.openuss.openformula.component.formula;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The renderer for my component
 */
public class EditorRenderer extends Renderer {

    private static final Logger logger = Logger.getLogger(EditorRenderer.class.getName());

    private VelocityContext velocityContext;

    public EditorRenderer() {
        super();
        Properties properties = new Properties();
        properties.put(RuntimeConstants.RESOURCE_LOADER, "classpath");
        properties.put("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        try {
            Velocity.init(properties);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        velocityContext = new VelocityContext();
    }

    public void decode(FacesContext facesContext, UIComponent uiComponent) {
        super.decode(facesContext, uiComponent);
        Map paramMap = facesContext.getExternalContext().getRequestParameterMap();
        String clientId = uiComponent.getClientId(facesContext);
        String value = (String) paramMap.get(clientId);
        Editor editor = (Editor) uiComponent;
        editor.setValue(value);
        editor.setValid(true);
    }

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        super.encodeBegin(facesContext, uiComponent);
        // no need to use encodeBegin.
    }

    public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        super.encodeChildren(facesContext, uiComponent);
        // this component does not have children
    }

    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        try {
            Template template = Velocity.getTemplate("templates/editor.vsl");
            StringWriter sw = new StringWriter();
            velocityContext.put("component", uiComponent);
            velocityContext.put("clientId", uiComponent.getId());
            template.merge(velocityContext, sw);
            ResponseWriter writer = facesContext.getResponseWriter();
            logger.log(Level.FINER, sw.toString());
            writer.write(sw.toString());
        } catch (ResourceNotFoundException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ParseErrorException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
