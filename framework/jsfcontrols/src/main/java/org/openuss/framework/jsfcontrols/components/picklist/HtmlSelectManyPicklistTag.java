/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.openuss.framework.jsfcontrols.components.picklist;

import org.apache.log4j.Logger;

import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlSelectListboxTagBase;

/**
 * @author Bruno Aranda (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlSelectManyPicklistTag extends HtmlSelectListboxTagBase
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HtmlSelectManyPicklistTag.class);

    public String getComponentType()
    {
        return HtmlSelectManyPicklist.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlSelectManyPicklist.DEFAULT_RENDERER_TYPE;
    }
    
    public void setEnabledOnUserRole(String enabledOnUserrole) {
    	logger.error("EnabledOnUserRole is not supported by HtmlSelectManyPickList!");
    	throw new UnsupportedOperationException("EnabledOnUserRole is not supported by HtmlSelectManyPickList!");
    }
    
    public void setVisibleOnUserRole(String enabledOnUserrole) {
    	logger.error("VisibleOnUserRole is not supported by HtmlSelectManyPickList!");
    	throw new UnsupportedOperationException("VisibleOnUserRole is not supported by HtmlSelectManyPickList!");
    }
    
}
 