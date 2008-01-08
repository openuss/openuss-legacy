package org.openuss.framework.jsfcontrols.components;

import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.MethodRule;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.html.HtmlComponentHandler;

public class PopupFrameComponentHandler extends HtmlComponentHandler {
	protected final static Class<?>[] ACTION_METHOD_SIG = new Class[0];
	protected final static MethodRule actionOpenTagRule = new MethodRule("actionOpen", String.class, ACTION_METHOD_SIG);
	protected final static MethodRule actionCloseTagRule = new MethodRule("actionClose", String.class, ACTION_METHOD_SIG);

	public PopupFrameComponentHandler(ComponentConfig tagConfig) {
		super(tagConfig);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected MetaRuleset createMetaRuleset(Class type) {
		MetaRuleset m = super.createMetaRuleset(type);

		m.addRule(actionOpenTagRule);
		m.addRule(actionCloseTagRule);

		return m;
	}
}