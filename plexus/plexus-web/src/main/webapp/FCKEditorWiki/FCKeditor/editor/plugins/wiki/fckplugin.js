/*
 * 
 * File Name: fckplugin.js
 * 	This plugin register the required Toolbar items to be able to insert 
 *  inner-wiki links
 * 
 * File Authors:
 * 		Christian Beer
 */

// Register the related commands.
FCKCommands.RegisterCommand('OpenUSSWikiLink', 
    new FCKDialogCommand(FCKLang['OpenUSSWikiLinkTitle'], 
                         FCKLang['OpenUSSWikiLinkTitle'], 
                         FCKConfig.BaseHref + '/openuss-plexus/views/secured/wiki/wikieditlinks.faces', 400, 500));
                         
// Create the "Find" toolbar button.
var oWikiLinkItem = new FCKToolbarButton('OpenUSSWikiLink', FCKLang['OpenUSSWikiLinkTitle']);
oWikiLinkItem.IconPath = FCKConfig.PluginsPath + 'wiki/wikilink.gif';

FCKToolbarItems.RegisterItem('OpenUSSWikiLink', oWikiLinkItem);

// oLink: The actual selected link in the editor.
            var oLink = FCK.Selection.MoveToAncestorNode( 'A' ) ;
            if ( oLink )
                FCK.Selection.SelectNode( oLink ) ;
                
            alert("FUCK U");
            
            window.onload = function()
            {
                // Translate the dialog box texts.
                oEditor.FCKLanguageManager.TranslatePage(document) ;
            
                // Load the selected link information (if any).
                LoadSelection() ;
            
                GetE('page').focus();
                window.parent.SetAutoSize( true ) ;
            
                // Activate the "OK" button.
                window.parent.SetOkButton(true) ;
            }
                        
        
            //#### The OK button was hit.
            function Ok()
            {
                var sUri, sInnerHtml ;
            
                sUri = GetE('page').value ;
                if ( sUri.length == 0 )
                {
                    alert( FCKLang.DlnLnkMsgNoUrl ) ;
                    return false ;
                }
            
                //check that the links have a proper protocol or assume http
                var sProtocol = oRegex.UriProtocol.exec( sUri ) ;
                if ( !sProtocol )
                {
                    sUri = "http://" + sUri ;
                }
            
                // No link selected, so try to create one.
                if ( !oLink )
                    oLink = oEditor.FCK.CreateLink( sUri ) ;
                
                if ( oLink )
                    sInnerHtml = oLink.innerHTML ;      // Save the innerHTML (IE changes it if it is like a URL).
                else
                {
                    // If no selection, use the uri as the link text (by dom, 2006-05-26)
                    sInnerHtml = sUri;
            
                    var oLinkPathRegEx = new RegExp("//?([^?\"']+)([?].*)?$");
                    var asLinkPath = oLinkPathRegEx.exec( sUri );
                    if (asLinkPath != null)
                        sInnerHtml = asLinkPath[1];  // use matched path
            
            
                    // built new anchor and add link text
                    oLink = oEditor.FCK.CreateElement( 'a' ) ;
                }
                
                oEditor.FCKUndo.SaveUndoStep() ;
            
                oLink.href = sUri ;
                SetAttribute( oLink, '_fcksavedurl', sUri ) ;
            
                oLink.innerHTML = sInnerHtml ;      // Set (or restore) the innerHTML
            
            //      SetAttribute( oLink, 'target', null ) ;
            //  SetAttribute( oLink, 'title'    , GetE('txtAttTitle').value ) ;
            
                // Select the link.
                oEditor.FCKSelection.SelectNode(oLink);
                
                return true ;
            }