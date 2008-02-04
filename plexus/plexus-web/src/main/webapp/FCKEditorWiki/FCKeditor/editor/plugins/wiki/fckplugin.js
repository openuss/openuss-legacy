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
	                         FCKConfig.BaseHref + '/openuss-plexus/views/secured/wiki/wikieditlinks.faces', 400, 500));
	                         
	// Create the "Find" toolbar button.
	var oWikiLinkItem = new FCKToolbarButton('OpenUSSWikiLink', FCKLang['OpenUSSWikiLinkTitle']);
	oWikiLinkItem.IconPath = FCKPlugins.Items['wiki'].Path + '/wikilink.gif';
	
	FCKToolbarItems.RegisterItem('OpenUSSWikiLink', oWikiLinkItem);
	
	FCK.ContextMenu.RegisterListener( {
	        AddItems : function( menu, tag, tagName )
	        {
	        	var bInsideLink = ( tagName == 'A' || FCKSelection.HasAncestorNode( 'A' ) ) ;
	        
	        	alert('test "' + tagName + '", "' + tag + '" ,"' + menu + '" : ' + bInsideLink);
	                // under what circumstances do we display this option
	                if ( bInsideLink )
	                {
	                        // the command needs the registered command name, the title for the context menu, and the icon path
	                        menu.AddItem( 'OpenUSSWikiLink', FCKLang['OpenUSSWikiLinkTitle'], oWikiLinkItem.IconPath ) ;
	                }
	        }}
	);
	
}