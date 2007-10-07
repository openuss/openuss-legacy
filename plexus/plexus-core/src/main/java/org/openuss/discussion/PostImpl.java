// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import org.apache.commons.lang.StringUtils;
import org.openuss.framework.utilities.Text2HtmlConverter;

/**
 * @author ingo dueppe
 * @author sebastian roekens
 * @see org.openuss.discussion.Post
 */
public class PostImpl extends PostBase implements Post {

	private static final long serialVersionUID = 6101341233546650814L;

	/**
	 * @see org.openuss.discussion.Post#getSubmitterName()
	 */
	public String getSubmitterName() {
		if (this.getSubmitter() != null) {
			return this.getSubmitter().getDisplayName();
		} else {
			return null;
		}
	}

	/**
	 * @see org.openuss.discussion.Post#getEditorName()
	 */
	public String getEditorName() {
		if (getEditor() != null) {
			return getEditor().getDisplayName();
		} else {
			return "";
		}
	}

	/**
	 * @see org.openuss.discussion.Post#isEdited()
	 */
	public boolean isEdited() {
		return StringUtils.isNotBlank(getEditorName());
	}

	@Override
	public String getFormulaString() {
		if (getFormula() != null) {
			return getFormula().getFormula();
		} else {
			return null;
		}
	}

	@Override
	public void setFormulaString(String formula) {
		if (getFormula() != null) {
			getFormula().setFormula(formula);
		} else if (getFormula() == null) {
			Formula f = Formula.Factory.newInstance();
			f.setFormula(formula);
			setFormula(f);
		}
	}

	@Override
	public Long getSubmitterPicture() {
		if (getSubmitter().getImageId() != null) {
			return getSubmitter().getImageId();
		} else {
			return null;
		}
	}

	@Override
	public String getText() {
		String text = super.getText();
		if (!text.contains("<br/>") && !text.contains("<a")) {
			text = Text2HtmlConverter.toHtml(text);
		}
		return text;
	}

}