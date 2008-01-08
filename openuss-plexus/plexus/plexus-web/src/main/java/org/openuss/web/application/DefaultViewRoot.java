package org.openuss.web.application;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.shale.view.Constants;
import org.apache.shale.view.ExceptionHandler;

/**
/**
 * <p>Extending the UIViewRoot to provide specialized exception
 * handling needed for the Shale view controller to enforce the contracts
 * of the callbacks.</p>
 * 
 * @author Ingo Dueppe
 *
 */
public class DefaultViewRoot extends UIViewRoot {

    /**
     * <p>Override to catch exceptions raised by the action listeners
     * in the invoke application phase.</p>
     *
     * @param context faces context
     */
    public void processApplication(FacesContext context) {
        try {
            super.processApplication(context);
        } catch (Exception e) {
            handleException(context, e);
        }
    }

    /**
     * <p>Override to catch and handle exceptions with immediate commands
     * and value change listeners in the apply request values phase.</p>
     *
     * @param context faces context
     */
    public void processDecodes(FacesContext context) {
        try {
            super.processDecodes(context);
        } catch (Exception e) {
            handleException(context, e);
        }
    }

    /**
     * <p>Override to catch and handle exceptions raised in the 
     * update model phase.</p>
     *
     * @param context faces context
     */
    public void processUpdates(FacesContext context) {
        try {
            super.processUpdates(context);
        } catch (Exception e) {
            handleException(context, e);
        }
    }

    /**
     * <p>Override to catch and handle exceptions in value change
     * listeners that might have occured in the process validations
     * phase.</p>
     *
     * @param context faces context
     */
    public void processValidators(FacesContext context) {
        try {
            super.processValidators(context);
        } catch (Exception e) {
            handleException(context, e);
        }
    }


    /**
     * <p>Handle the specified exception according to the strategy
     * defined by our current {@link ExceptionHandler}.</p>
     *
     * @param context FacesContext for the current request
     * @param exception Exception to be handled
     */
    private void handleException(FacesContext context, Exception exception) {

        if (context == null) {
            exception.printStackTrace(System.out);
            return;
        }
        ExceptionHandler handler = (ExceptionHandler)
          context.getApplication().getVariableResolver().resolveVariable
                (context, Constants.EXCEPTION_HANDLER);
        handler.handleException(exception);

    }
	
	
}
