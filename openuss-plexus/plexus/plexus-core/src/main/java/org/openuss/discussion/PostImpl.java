// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

/**
 * @see org.openuss.discussion.Post
 */
public class PostImpl
    extends org.openuss.discussion.PostBase
	implements org.openuss.discussion.Post
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = 6101341233546650814L;

    /**
     * @see org.openuss.discussion.Post#getSubmitterName()
     */
    public java.lang.String getSubmitterName()
    {
    	if (this.getSubmitter()!=null) return this.getSubmitter().getDisplayName();
    	return null;
    }

    /**
     * @see org.openuss.discussion.Post#getEditorName()
     */
    public java.lang.String getEditorName()
    {
    	if (getEditor()!=null) 	return	getEditor().getDisplayName();
    	return "";
    }

    /**
     * @see org.openuss.discussion.Post#isEdited()
     */
    public boolean isEdited()
    {
    	return (getEditorName()!=null);
    }

	@Override
	public String getFormulaString() {
		if (getFormula()!=null)	return getFormula().getFormula();
		return "";
	}

	@Override
	public void setFormulaString(String formula) {
       	if (getFormula()!=null){
       		getFormula().setFormula(formula);
       	} else if (getFormula()==null){
       		Formula f = Formula.Factory.newInstance();
       		f.setFormula(formula);
      		setFormula(f);
       	}
	}

}