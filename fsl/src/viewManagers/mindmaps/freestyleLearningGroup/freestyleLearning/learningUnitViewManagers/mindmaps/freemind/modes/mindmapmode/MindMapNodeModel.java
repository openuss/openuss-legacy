/*FreeMindAdapter - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2001  Joerg Mueller <joergmueller@bigfoot.com>
 *See COPYING for Details
 *
 *Modified by Joerg Brenninkmeyer <joerg@brenninkmeyer.name> (2004)
 *for integration into Freestyle Learning <www.freestyle-learning.de>
 *which is also distributed under GNU GPL terms. The code is available
 *at <http://sourceforge.net/projects/openuss/>.
 * 
 *This program is free software; you can redistribute it and/or
 *modify it under the terms of the GNU General Public License
 *as published by the Free Software Foundation; either version 2
 *of the License, or (at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program; if not, write to the Free Software
 *Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/*$Id: MindMapNodeModel.java,v 1.3 2004/09/01 04:46:45 jbrenni Exp $*/

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.mindmapmode;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;

import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.FreeMindMain;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.Tools;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.main.XMLElement;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.EdgeAdapter;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.MindIcon;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.MindMapNode;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mindmaps.freemind.modes.NodeAdapter;

/**
 * This class represents a single Node of a Tree. It contains direct handles 
 * to its parent and children and to its view.
 */
public class MindMapNodeModel extends NodeAdapter {
	
    //
    //  Constructors
    //

    public MindMapNodeModel(FreeMindMain frame) {
	super(frame);
	children = new LinkedList();
	setEdge(new MindMapEdgeModel(this, getFrame()));
    }

	    
    public MindMapNodeModel( Object userObject, FreeMindMain frame ) {
	super(userObject,frame);
	children = new LinkedList();
	setEdge(new MindMapEdgeModel(this, getFrame()));
    }

    //Overwritten get Methods
    public String getStyle() {
	if (isFolded()) {
	    return MindMapNode.STYLE_BUBBLE;
	} else {
	    return super.getStyle();
	}
    }

    protected MindMapNode basicCopy() {
       return new MindMapNodeModel(userObject, getFrame()); }

    //
    // The mandatory load and save methods
    //

    public String saveHTML_escapeUnicodeAndSpecialCharacters(String text) {
       int len = text.length();
       StringBuffer result = new StringBuffer(len);
       int intValue;
       char myChar;
       boolean previousSpace = false;
       boolean spaceOccured = false;
       for (int i = 0; i < len; ++i) {
          myChar = text.charAt(i);
          intValue = (int) text.charAt(i);
          if (intValue > 128) {
             result.append("&#").append(intValue).append(';'); }
          else {
             spaceOccured = false;
             switch (myChar) {
             case '&':
                result.append("&amp;");
                break;
             case '<':
                result.append("&lt;");
                break;
             case '>':
                result.append("&gt;");
                break;
             case ' ':
                spaceOccured  = true;
                if (previousSpace) {
                   result.append("&nbsp;"); }
                else { 
                   result.append(" "); }
                break;                
             case '\n':
                result.append("\n<br>\n");
                break;
             default:
                result.append(myChar); }
             previousSpace = spaceOccured; }}
       return result.toString(); };

    public int saveHTML(Writer fileout, String parentID, int lastChildNumber,
                        boolean isRoot, boolean treatAsParagraph, int depth) throws IOException {
        // return lastChildNumber 
        // Not very beautiful solution, but working at least and logical too.

        final String el = System.getProperty("line.separator");
        
        boolean basedOnHeadings = (getFrame().getProperty("html_export_folding").
                                   equals("html_export_based_on_headings"));

        boolean createFolding = isFolded();
        if (getFrame().getProperty("html_export_folding").equals("html_export_fold_all")) {
           createFolding = hasChildren(); }
        if (getFrame().getProperty("html_export_folding").equals("html_export_no_folding") ||
            basedOnHeadings || isRoot) {
           createFolding = false; }

        fileout.write(treatAsParagraph || basedOnHeadings ? "<p>" : "<li>");

        String localParentID = parentID;
	if (createFolding) {
           // lastChildNumber = new Integer lastChildNumber.intValue() + 1; Change value of an integer
           lastChildNumber++;
     
           localParentID = parentID+"_"+lastChildNumber;
           fileout.write
              ("<span id=\"show"+localParentID+"\" class=\"foldclosed\" onClick=\"show_folder('"+localParentID+
               "')\" style=\"POSITION: absolute\">+</span> "+
               "<span id=\"hide"+localParentID+"\" class=\"foldopened\" onClick=\"hide_folder('"+localParentID+
               "')\">-</Span>");

           fileout.write("\n"); }
        
        if (basedOnHeadings && hasChildren() && depth <= 5) {
           fileout.write("<h"+depth+">"); }

	if (getLink() != null) {
           String link = getLink();
           if (link.endsWith(".mm")) {
              link += ".html"; }
           fileout.write("<a href=\""+link+"\" target=\"_blank\"><span class=l>~</span>&nbsp;"); }

        String fontStyle="";
	
	if (color != null) {
           fontStyle+="color: "+Tools.colorToXml(getColor())+";"; }

        if (font!=null && font.getSize()!=0) {
           int defaultFontSize = Integer.parseInt(getFrame().getProperty("defaultfontsize"));
           int procentSize = (int)(getFont().getSize()*100/defaultFontSize);
           if (procentSize != 100) {
              fontStyle+="font-size: "+procentSize+"%;"; }}

        if (font != null) {
           String fontFamily = getFont().getFamily();
           fontStyle+="font-family: "+fontFamily+", sans-serif; "; }

        if (isItalic()) {
           fontStyle+="font-style: italic; "; }

        if (isBold()) {
           fontStyle+="font-weight: bold; "; }

        // ------------------------

        if (!fontStyle.equals("")) {
           fileout.write("<span style=\""+fontStyle+"\">"); }

        if (getFrame().getProperty("export_icons_in_html").equals("true")) {
           for (int i = 0; i < getIcons().size(); ++i) {
              fileout.write("<img src=\"" + ((MindIcon) getIcons().get(i)).getIconFileName() +
                            "\" alt=\""+((MindIcon) getIcons().get(i)).getDescription(getFrame())+"\">"); }}

        if (this.toString().matches(" *")) {
           fileout.write("&nbsp;"); }
        else if (this.toString().startsWith("<html>")) {
           String output = this.toString().substring(6); // do not write <html>
           if (output.endsWith("</html>")) {
              output = output.substring(0,output.length()-7); }
           fileout.write(output); }
        else {
           fileout.write(saveHTML_escapeUnicodeAndSpecialCharacters(toString())); }

        if (fontStyle != "") {
           fileout.write("</span>"); }

        fileout.write(el);

        if (getLink() != null) {
           fileout.write("</a>"+el); }

        if (basedOnHeadings && hasChildren() && depth <= 5) {
           fileout.write("</h"+depth+">"); }
        
        // Are the children to be treated as paragraphs?
        
        boolean treatChildrenAsParagraph = false;
        for (ListIterator e = childrenUnfolded(); e.hasNext(); ) {
           if (((MindMapNodeModel)e.next()).toString().length() > 100) { // TODO: replace heuristic constant
              treatChildrenAsParagraph = true;
              break; }}

        // Write the children

        //   Export based on headings

        if (getFrame().getProperty("html_export_folding").equals("html_export_based_on_headings")) {
           for (ListIterator e = childrenUnfolded(); e.hasNext(); ) {
              MindMapNodeModel child = (MindMapNodeModel)e.next();            
              lastChildNumber =
                 child.saveHTML(fileout,parentID,lastChildNumber,/*isRoot=*/false,
                                treatChildrenAsParagraph, depth + 1); }
           return lastChildNumber; }
       
        //   Export not based on headings

        if (hasChildren()) {
           if (getFrame().getProperty("html_export_folding").equals("html_export_based_on_headings")) {
              for (ListIterator e = childrenUnfolded(); e.hasNext(); ) {
                 MindMapNodeModel child = (MindMapNodeModel)e.next();            
                 lastChildNumber =
                    child.saveHTML(fileout,parentID,lastChildNumber,/*isRoot=*/false,
                                   treatChildrenAsParagraph, depth + 1); }}              
           else if (createFolding) {
              fileout.write("<ul id=\"fold"+localParentID+
                            "\" style=\"POSITION: relative; VISIBILITY: visible;\">");
              if (treatChildrenAsParagraph) {
                 fileout.write("<li>"); }
              int localLastChildNumber = 0;
              for (ListIterator e = childrenUnfolded(); e.hasNext(); ) {
                 MindMapNodeModel child = (MindMapNodeModel)e.next();            
                 localLastChildNumber =
                    child.saveHTML(fileout,localParentID,localLastChildNumber,/*isRoot=*/false, 
                                   treatChildrenAsParagraph, depth + 1); }}
           else {
              fileout.write("<ul>"); 
              if (treatChildrenAsParagraph) {
                 fileout.write("<li>"); }
              for (ListIterator e = childrenUnfolded(); e.hasNext(); ) {
                 MindMapNodeModel child = (MindMapNodeModel)e.next();            
                 lastChildNumber =
                    child.saveHTML(fileout,parentID,lastChildNumber,/*isRoot=*/false,
                                   treatChildrenAsParagraph, depth + 1); }}
           if (treatChildrenAsParagraph) {
              fileout.write("</li>"); }
           fileout.write(el);
           fileout.write("</ul>"); }

        // End up the node
        
        if (!treatAsParagraph) {
           fileout.write(el+"</li>"+el); }

        return lastChildNumber;
    }
    public void saveTXT(Writer fileout,int depth) throws IOException {
        for (int i=0; i < depth; ++i) {
           fileout.write("    "); }
        if (this.toString().matches(" *")) {
           fileout.write("o"); }
        else {
           if (getLink() != null) {
              String link = getLink();
              if (!link.equals(this.toString())) {
                 fileout.write(this.toString()+" "); }              
              fileout.write("<"+link+">"); }
           else {
              fileout.write(this.toString()); }}


        fileout.write("\n");
        //fileout.write(System.getProperty("line.separator"));
        //fileout.newLine();

        // ^ One would rather expect here one of the above commands
        // commented out. However, it does not work as expected on
        // Windows. My unchecked hypothesis is, that the String Java stores
        // in Clipboard carries information that it actually is \n
        // separated string. The current coding works fine with pasting on
        // Windows (and I expect, that on Unix too, because \n is a Unix
        // separator). This method is actually used only for pasting
        // purposes, it is never used for writing to file. As a result, the
        // writing to file is not tested.
        
        // Another hypothesis is, that something goes astray when creating
        // StringWriter.

        for (ListIterator e = childrenUnfolded(); e.hasNext(); ) {
           ((MindMapNodeModel)e.next()).saveTXT(fileout,depth + 1); }
    }
    public void collectColors(HashSet colors) {
       if (color != null) {
          colors.add(getColor()); }
       for (ListIterator e = childrenUnfolded(); e.hasNext(); ) {
          ((MindMapNodeModel)e.next()).collectColors(colors); }}

    private String saveRFT_escapeUnicodeAndSpecialCharacters(String text) {
       int len = text.length();
       StringBuffer result = new StringBuffer(len);
       int intValue;
       char myChar;
       for (int i = 0; i < len; ++i) {
          myChar = text.charAt(i);
          intValue = (int) text.charAt(i);
          if (intValue > 128) {
             result.append("\\u").append(intValue).append("?"); }
          else {
             switch (myChar) {
             case '\\':
                result.append("\\\\");
                break;
             case '{':
                result.append("\\{");
                break;
             case '}':
                result.append("\\}");
                break;                
			case '\n':
				result.append(" \\line ");
				break;                
             default:
                result.append(myChar); }}}
       return result.toString(); }

    public void saveRTF(Writer fileout, int depth, HashMap colorTable) throws IOException {
        String pre="{"+"\\li"+depth*350;
        String level;
        if (depth <= 8){
           level = "\\outlinelevel" + depth;
        }
        else
        { 
           level = "";
        }
        String fontsize="";
	if (color != null) {
           pre += "\\cf"+((Integer)colorTable.get(getColor())).intValue(); }

        if (isItalic()) {
           pre += "\\i "; }
        if (isBold()) {
           pre += "\\b "; }
        if (font != null && font.getSize() != 0) {
           fontsize="\\fs"+Math.round(1.5*getFont().getSize());
           pre += fontsize; }

        pre += "{}"; // make sure setting of properties is separated from the text itself

        fileout.write("\\li"+depth*350+level+"{}");
        if (this.toString().matches(" *")) {
           fileout.write("o"); }
        else {
           String text = saveRFT_escapeUnicodeAndSpecialCharacters(this.toString());
           if (getLink() != null) {
              String link = saveRFT_escapeUnicodeAndSpecialCharacters(getLink());
              if (link.equals(this.toString())) {
                 fileout.write(pre+"<{\\ul\\cf1 "+link+"}>"+"}"); }
              else {
                 fileout.write("{"+fontsize+pre+text+"} ");
                 fileout.write("<{\\ul\\cf1 "+link+"}}>"); }}
           else {
              fileout.write(pre+text+"}"); }}
        
        fileout.write("\\par");
        fileout.write("\n");

        for (ListIterator e = childrenUnfolded(); e.hasNext(); ) {
           ((MindMapNodeModel)e.next()).saveRTF(fileout,depth + 1,colorTable); }
    }

    //NanoXML save method
    public void save(Writer writer, MindMapMapModel model) throws IOException {
	XMLElement node = new XMLElement();
	node.setName("node");

	node.setAttribute("text",this.toString());

	//	((MindMapEdgeModel)getEdge()).save(doc,node);

	XMLElement edge = ((MindMapEdgeModel)getEdge()).save();
	if (edge != null) {
           node.addChild(edge); }

    if(getCloud() != null) {
        XMLElement cloud = ((MindMapCloudModel)getCloud()).save();
        node.addChild(cloud); 
    }

//     // fc, 31.12.2003: For SVG export
//     if(getViewer() != null) {
//         node.setAttribute("SCREEN_POSITION_X", Integer.toString(getViewer().getX()));
//         node.setAttribute("SCREEN_POSITION_Y", Integer.toString(getViewer().getY()));
//         node.setAttribute("SCREEN_HEIGHT", Integer.toString(getViewer().getHeight()));
//         node.setAttribute("SCREEN_WIDTH", Integer.toString(getViewer().getWidth()));
//     }

    Vector linkVector = model.getLinkRegistry().getAllLinksFromMe(this); /* Puh... */
    for(int i = 0; i < linkVector.size(); ++i) {
        if(linkVector.get(i) instanceof MindMapArrowLinkModel) {
            XMLElement arrowLinkElement = ((MindMapArrowLinkModel) linkVector.get(i)).save();
            node.addChild(arrowLinkElement);
        }
    }
        
	if (isFolded()) {
           node.setAttribute("folded","true"); }
	
    // fc, 17.12.2003: Remove the left/right bug.
    //                       VVV  save if and only if parent is root.
	if ((isLeft()!= null) && !(isRoot()) && (getParentNode().isRoot())) {
        node.setAttribute("POSITION",(isLeft().getValue())?"left":"right"); 
    }
	
    String label = model.getLinkRegistry().getLabel(this); /* Puh... */
	if (label!=null) {
           node.setAttribute("id",label); }
	
	if (color != null) {
           node.setAttribute("color", Tools.colorToXml(getColor())); }

	if (style != null) {
           node.setAttribute("style", super.getStyle()); }
	    //  ^ Here cannot be just getStyle() without super. This is because
	    //  getStyle's style depends on folded / unfolded. For example, when
	    //  real style is fork and node is folded, getStyle returns
	    //  MindMapNode.STYLE_BUBBLE, which is not what we want to save.

	//link
	if (getLink() != null) {
           node.setAttribute("link", getLink()); }

	//font
	if (font!=null) {
	    XMLElement fontElement = new XMLElement();
	    fontElement.setName("font");

	    if (font != null) {
               fontElement.setAttribute("name",font.getFamily()); }
	    if (font.getSize() != 0) {
               fontElement.setAttribute("size",Integer.toString(font.getSize())); }
	    if (isBold()) {
               fontElement.setAttribute("bold","true"); }
	    if (isItalic()) {
               fontElement.setAttribute("italic","true"); }
	    if (isUnderlined()) {
               fontElement.setAttribute("underline","true"); }
	    node.addChild(fontElement); }
    for(int i = 0; i < getIcons().size(); ++i) {
	    XMLElement iconElement = new XMLElement();
	    iconElement.setName("icon");
        iconElement.setAttribute("builtin", ((MindIcon) getIcons().get(i)).getName());
        node.addChild(iconElement);
    }
        

        if (childrenUnfolded().hasNext()) {
           node.writeWithoutClosingTag(writer);
           //recursive
           for (ListIterator e = childrenUnfolded(); e.hasNext(); ) {
              MindMapNodeModel child = (MindMapNodeModel)e.next();
              child.save(writer, model); }
           node.writeClosingTag(writer); }
        else {
           node.write(writer); }}
    
    // --- additional FSL support ---
  
    public int getWidthWithParentInformation() {
        return ((EdgeAdapter)this.getEdge()).getWidthWithParentInformation();
    }

}
