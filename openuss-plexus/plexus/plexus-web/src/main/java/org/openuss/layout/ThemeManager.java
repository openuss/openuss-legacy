package org.openuss.layout;

/**
 * This class is to intend to provide theme management  
 * @author idueppe
 * @deprecated
 */
public class ThemeManager {
	
	private String designStyleSheet;
	private String layoutStyleSheet;
	private String typoStyleSheet;
	
	private String layout;
	
	/**
	 * The layout property defines the facelets composite template that sites should use.
	 * @TODO For now it is statically bound within the faces-config.xml. But it should be used
	 * to dynamically change the templating according to the current context of the user.
	 * For example different theme packages for institutes.  
	 */
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public String getLayoutStyleSheet() {
		return layoutStyleSheet;
	}
	public void setLayoutStyleSheet(String layoutStyleSheet) {
		this.layoutStyleSheet = layoutStyleSheet;
	}
	public String getTypoStyleSheet() {
		return typoStyleSheet;
	}
	public void setTypoStyleSheet(String typoStyleSheet) {
		this.typoStyleSheet = typoStyleSheet;
	}
	public String getDesignStyleSheet() {
		return designStyleSheet;
	}
	public void setDesignStyleSheet(String designStyleSheet) {
		this.designStyleSheet = designStyleSheet;
	}

}
