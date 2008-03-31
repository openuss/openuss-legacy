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

FCKConfig.LinkDlgHideTarget = true;
FCKConfig.LinkUpload = false;
FCKConfig.LinkDlgHideAdvanced = true;

basePath = FCKConfig.BasePath.substring(0, FCKConfig.BasePath.indexOf("/", 1));

// only working in wiki!!
if (window.document.location.href.match(/Wiki/)) {

	// get parameters from hidden fields in xhtml. these must be present!!
	openussCourseId = top.document.getElementById('openussCourseId').value;

	FCKConfig.ContextMenu = ['Generic','Anchor','Image','Flash','Select','Textarea','Checkbox','Radio','TextField', 'HiddenField','ImageButton','Button','BulletedList','NumberedList','Form'] ;

    // Wiki link plugin
    var sOtherPluginPath = FCKConfig.BasePath.substr(0, FCKConfig.BasePath.length - 7) + 'editor/plugins/' ;
    FCKConfig.Plugins.Add( 'wiki', 'de,en', sOtherPluginPath ) ;

    FCKConfig.ImageBrowserURL = basePath + '/views/secured/wiki/wikichooseimage.faces?course=' + openussCourseId;
    
    FCKConfig.EditorAreaCSS = '/theme-plexus/css/style.css';
    FCKConfig.BodyClass = 'wiki_content';
    FCKConfig.BodyId = 'wiki_editor';
    
    //----------------------------------------------------
	// ajaxAutoSave plugin 
	FCKConfig.Plugins.Add( 'ajaxAutoSave','de,en') ;
	
	// --- config settings for the ajaxAutoSave plugin ---
	// URL to post to
	FCKConfig.ajaxAutoSaveTargetUrl = basePath + '/fckfaces/FCKeditor/editor/savedraft' ;
	
	// Enable / Disable Plugin onBeforeUpdate Action 
	FCKConfig.ajaxAutoSaveBeforeUpdateEnabled = true;
	
	// RefreshTime
	FCKConfig.ajaxAutoSaveRefreshTime = 120 ; // 120 = 2 min
	
	// Sensitivity to key strokes
	FCKConfig.ajaxAutoSaveSensitivity = 2 ;
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
    ['ajaxAutoSave','FitWindow','Preview'],
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

