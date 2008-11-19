package org.openuss.discussion;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

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
			Formula f = new FormulaImpl();
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
	public Date getLastModification() {
		Date last = super.getLastModification();
		if (last == null) {
			last = super.getCreated();
		} 
		return last;
	}

}