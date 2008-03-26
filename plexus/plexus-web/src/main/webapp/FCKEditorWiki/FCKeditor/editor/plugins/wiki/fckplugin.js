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

	// Register the related commands.
	FCKCommands.RegisterCommand('OpenUSSWikiLink', 
	    new FCKDialogCommand(FCKLang['OpenUSSWikiLinkTitle'], 
	                         FCKLang['OpenUSSWikiLinkTitle'], 
	                         basePath + '/views/secured/wiki/wikieditlinks.faces', 400, 500));
	                         
    // Register the related commands.
    FCKCommands.RegisterCommand('OpenUSSWikiImage', 
        new FCKDialogCommand(FCKLang['OpenUSSWikiImageTitle'], 
                             FCKLang['OpenUSSWikiImageTitle'], 
                             basePath + '/views/secured/wiki/wikieditimages.faces', 400, 500));
	                         
	// Create the "Link" toolbar button.
	var oWikiLinkItem = new FCKToolbarButton('OpenUSSWikiLink', FCKLang['OpenUSSWikiLinkTitle']);
	oWikiLinkItem.IconPath = FCKPlugins.Items['wiki'].Path + '/wikilink.gif';
	
	// Create the "Image" toolbar button.
    var oWikiImageItem = new FCKToolbarButton('OpenUSSWikiImage', FCKLang['OpenUSSWikiImageTitle']);
    oWikiImageItem.IconPath = FCKPlugins.Items['wiki'].Path + '/wikiimage.gif';
	
	FCKToolbarItems.RegisterItem('OpenUSSWikiLink', oWikiLinkItem);
	FCKToolbarItems.RegisterItem('OpenUSSWikiImage', oWikiImageItem);
	
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