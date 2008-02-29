/*
 * FCKeditor - The text editor for internet
 * Copyright (C) 2003-2006 Frederico Caldeira Knabben
 * 
 * Licensed under the terms of the GNU Lesser General Public License:
 * 		http://www.opensource.org/licenses/lgpl-license.php
 * 
 * For further information visit:
 * 		http://www.fckeditor.net/
 * 
 * "Support Open Source software. What about a donation today?"
 * 
 * File Name: fckconfig.js
 * 	Editor configuration settings.
 * 	See the documentation for more info.
 * 
 * File Authors:
 * 		Frederico Caldeira Knabben (fredck@fckeditor.net)
 */

FCKConfig.SkinPath = FCKConfig.BasePath + 'skins/openuss/' ;

FCKConfig.FlashUpload = false;
FCKConfig.ImageUpload = false;
FCKConfig.LinkBrowser = false;

// only working in wiki!!
if (window.document.location.href.match(/Wiki/)) {

    FCKConfig.ContextMenu = ['Generic','Anchor','Image','Flash','Select','Textarea','Checkbox','Radio','TextField', 'HiddenField','ImageButton','Button','BulletedList','NumberedList','Form'] ;

    // Wiki link plugin
    var sOtherPluginPath = FCKConfig.BasePath.substr(0, FCKConfig.BasePath.length - 7) + 'editor/plugins/' ;
    FCKConfig.Plugins.Add( 'wiki', 'de,en', sOtherPluginPath ) ;

    FCKConfig.ImageBrowserURL = '/openuss-plexus/views/secured/wiki/wikichooseimage.faces' ;
    
    FCKConfig.EditorAreaCSS = '/theme-plexus/css/style.css';
    FCKConfig.BodyClass = 'wiki_content';
}

FCKConfig.ToolbarSets["OpenUSS"] = [
	['Preview'],
	['Cut','Copy','Paste','PasteText','PasteWord','-','Print'],
	['Undo','Redo','-','SelectAll','RemoveFormat'],
	'/',
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['OrderedList','UnorderedList','-','Outdent','Indent'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['Link','Unlink'],
	['Smiley'],
	'/',
	['Style','FontFormat','FontName','FontSize'],
	['TextColor','BGColor'],
	['FitWindow','-','About']
] ;

FCKConfig.ToolbarSets["News"] = [
	['FitWindow','Preview'],
	['Cut','Copy','Paste','PasteText','PasteWord','-','Print'],
	['Undo','Redo','-','SelectAll','RemoveFormat'],
	'/',
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['OrderedList','UnorderedList','-','Outdent','Indent'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['Link','Unlink'],
	['TextColor','BGColor'],
	['Smiley']
] ;

FCKConfig.ToolbarSets["Wiki"] = [
    ['FitWindow','Preview'],
	['Cut','Copy','Paste','PasteText','PasteWord','-','Print'],
	['Undo','Redo','-','SelectAll','RemoveFormat'],
    ['Image','-','OpenUSSWikiLink','Unlink','-', 'Smiley'],
    '/',
	['FontFormat','-','Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['OrderedList','UnorderedList','-','Outdent','Indent'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['TextColor','BGColor']	
] ;

FCKConfig.ToolbarSets["Description"] = [
	['FitWindow','Preview'],
	['Cut','Copy','Paste','PasteText','PasteWord','-','Print'],
	['Undo','Redo','-','SelectAll','RemoveFormat'],
	'/',
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['OrderedList','UnorderedList','-','Outdent','Indent'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['Link','Unlink'],
	['TextColor','BGColor'],
	['Smiley']
] ;

FCKConfig.ToolbarSets["Discussion"] = [
	['FitWindow','Preview'],
	['Cut','Copy','Paste','PasteText','PasteWord','-','Print'],
	['Undo','Redo','-','SelectAll','RemoveFormat'],
	'/',
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['OrderedList','UnorderedList','-','Outdent','Indent'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['Link','Unlink'],
	['TextColor','BGColor'],
	['Smiley']
] ;

FCKConfig.ToolbarSets["Chat"] = [
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript','-','Smiley'],
	['Link','Unlink'],
	['TextColor','BGColor'],	
] ;

FCKConfig.ToolbarSets["Portrait"] = [
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['Undo','Redo','-'],
	['OrderedList','UnorderedList','-','Outdent','Indent'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['FitWindow'],
] ;

FCKConfig.ToolbarSets["Email"] = [
	['FitWindow','Preview'],
	['Cut','Copy','Paste','PasteText','PasteWord','-','Print'],
	['Undo','Redo','-','SelectAll','RemoveFormat'],
	'/',
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['OrderedList','UnorderedList','-','Outdent','Indent'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['Link','Unlink'],
	['TextColor','BGColor']
	
] ;
