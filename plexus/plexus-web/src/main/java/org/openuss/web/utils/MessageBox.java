package org.openuss.web.utils;

import java.io.Serializable;

import org.openuss.framework.web.jsf.controller.BaseBean;

/**
 * A messagebox represents all additional needed information to display a message box site.
 * 
 * For instance the title of the page and the return page after confirmation.
 *  
 * @author Ingo Dueppe
 */
public class MessageBox extends BaseBean implements Serializable{
	
	private static final String MESSAGEBOX = "messagebox";

	private static final long serialVersionUID = 6788903491909715195L;
	
	public static final String SESSION_KEY = MESSAGEBOX; 

	private String title;
	
	private String confirmLabel; 
	private String confirmOutcome;

	/**
	 * Convience message to create a default message box with a given message.
	 * @param message
	 * @return outcome "MESSAGEBOX"
	 */
	public static String showDefaultMessage(String message, String title) {
		new MessageBox(message, title);
		return MESSAGEBOX;
	}
	
	/**
	 * Convience method to create a new Messagebox instance 
	 * @param message text
	 * @return MessageBox instance
	 */
	public static MessageBox createMessage(String message, String title) {
		MessageBox box = new MessageBox(message, title);
		return box;
	}
	
	/**
	 * Creates a MessageBox bean and register it in the session scope.
	 * The default label of the confirm button is <code>getText("home")</code>.
	 * The default outcome of the confirm button is <code>home</home> 
	 */
	public MessageBox() {
		setSessionBean(MessageBox.SESSION_KEY, this);
		confirmOutcome = "home";
		confirmLabel = i18n("home");
	}
	
	/**
	 * Creates a MessageBox bean and register it in the session scope.
	 * The message will be added to the facesmessage facility.
	 * The default label of the confirm button is <code>getText("home")</code>.
	 * The default outcome of the confirm button is <code>home</home> 
	 * @param message
	 */
	public MessageBox(String message, String title) {
		this();
		this.title = title;
		addMessage(message);
	}
	
	/**
	 * Get the outcome of the confirm button.
	 * @return
	 */
	public String getConfirmOutcome() {
		return confirmOutcome;
	}
	
	/**
	 * Defines the outcome if the confirm button is hit.
	 * @param confirmOutcome
	 */
	public void setConfirmOutcome(String confirmOutcome) {
		this.confirmOutcome = confirmOutcome;
	}
	
	/**
	 * Get the title of the message box
	 * @return String
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Set the title of the message box.
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Label of the confirm button
	 * @return
	 */
	public String getConfirmLabel() {
		return confirmLabel;
	}
	
	/**
	 * Set the label of the confirm button
	 * @param confirmLabel
	 */
	public void setConfirmLabel(String confirmLabel) {
		this.confirmLabel = confirmLabel;
	}
	
	/**
	 * Is called by the messagebox view on confirmation.
	 * Removed the bean from session scope.
	 * @return configured outcome
	 */
	public String confirm() {
		setSessionBean(SESSION_KEY, null);
		return confirmOutcome;
	}
	
	
}
