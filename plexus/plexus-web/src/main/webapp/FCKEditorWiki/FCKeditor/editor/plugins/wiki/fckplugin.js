/*
 * 
 * File Name: fckplugin.js
 * 	This plugin register the required Toolbar items to be able to insert 
 *  inner-wiki links
 * 
 * File Authors:
 * 		Christian Beer
 */

// only working in wiki!!
if (window.document.location.href.match(/Wiki/)) {

	openussCourseId = 1005; // FIXME parameterize courseId

	alert("t13: " + window.openussCourseId);

	// Register the related commands.
	FCKCommands.RegisterCommand('OpenUSSWikiLink', 
	    new FCKDialogCommand(FCKLang['OpenUSSWikiLinkTitle'], 
	                         FCKLang['OpenUSSWikiLinkTitle'], 
	                         basePath + '/views/secured/wiki/wikieditlinks.faces?course=' + openussCourseId, 400, 500));
	                         	                         
	// Create the "Link" toolbar button.
	var oWikiLinkItem = new FCKToolbarButton('OpenUSSWikiLink', FCKLang['OpenUSSWikiLinkTitle']);
	oWikiLinkItem.IconPath = FCKPlugins.Items['wiki'].Path + '/wikilink.gif';
	
	FCKToolbarItems.RegisterItem('OpenUSSWikiLink', oWikiLinkItem);
	
	FCK.ContextMenu.RegisterListener( {
	        AddItems : function( menu, tag, tagName )
	        {
	        	var bInsideLink = ( tagName == 'A' || FCKSelection.HasAncestorNode( 'A' ) ) ;
	        
        	    // under what circumstances do we display this option
                if ( bInsideLink )
                {
                        // the command needs the registered command name, the title for the context menu, and the icon path
                        menu.AddItem( 'OpenUSSWikiLink', FCKLang['OpenUSSWikiLinkContextMenuTitle'], oWikiLinkItem.IconPath ) ;
                }
	        }}
	);
	
}