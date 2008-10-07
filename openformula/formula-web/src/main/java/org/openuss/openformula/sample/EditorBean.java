package org.openuss.openformula.sample;

public class EditorBean {
	
	private String text;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String save() {
		System.out.println("Save Text: \n"+text);
		return null;
	}
}