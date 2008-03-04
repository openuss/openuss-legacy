var chatWindows;
var WIDTH = 600;
var HEIGHT = 650;
var RETRY_TIME = 100;
var REFRESH_TIME = 10000;
var timestamp = -1;
var sending = false;
var serverName = "";
var refreshTimeout = null;

/**
 * Openchat
 **/
function openChat(id) {
	if (chatWindows == null) {
		chatWindows = new Array();
	}
	if (id == -1)
		return;
    var name = 'chat' + id;
    var obj;
    var openWindow = false;
    for (var i = 0; i < chatWindows.length; i++) {
    	if (chatWindows[i][name] == name) {
    		obj = chatWindows[i];
    		if (obj[window].closed || !obj[window].name) {
    			openWindow = true;
    		}
    		continue;
    	}
    }
    if (obj == null) {
    	var obj = new Object();
    	obj[name] = name;
    	chatWindows.push(obj);
    	openWindow = true;
    }
    if (openWindow) {
        x = (screen.availWidth-WIDTH-10)/2;
        y = (screen.availHeight-HEIGHT-30)/2;
        obj[window] = window.open('chat.faces?group=' + id, name, "menubar=no,status=no,scrollbars=no,dependent=no,resizable=no,width=" + WIDTH + ",height=" + HEIGHT + ",left=" + x + ",top=" + y);
	    obj[window].innerWidth = WIDTH;
    	obj[window].innerHeight = HEIGHT;
	}
    obj[window].focus();
    for (var i = 0; i < chatWindows.length; i++) {
	    chatWindows[i][window].chatWindows = chatWindows;
    }
}

function scrollToBottom(element) {
    element.scrollTop = element.scrollHeight;
}

function enc(text) {
	return encodeURIComponent(escape(text));
}

function dec(text) {
	return unescape(text);
}

function updateChatArea(refreshXML) {
	var messages = refreshXML.getElementsByTagName("messages");
	if (messages.length == 0)
		return;
	var msgArray = messages[0].getElementsByTagName("message");
	
	for (var i = 0; i < msgArray.length; i++) {
		var sender = msgArray[i].getElementsByTagName("sender")[0];
		var message = '';
		message += '<div>';
		message += dec(sender.getElementsByTagName('name')[0].firstChild.nodeValue);
		message += " (" + msgArray[i].getElementsByTagName("time")[0].firstChild.nodeValue + ")" + ": ";
		message += '</div>\n';
		message += '<div class="Message"';
		var colors = sender.getElementsByTagName('color');
		if (colors.length > 0)
			message += ' style="color:' + colors[0].firstChild.nodeValue + '"';
		message += '>' + dec(msgArray[i].getElementsByTagName("content")[0].firstChild.nodeValue)+ '</div>\n';
		document.getElementById('chatArea').innerHTML += message;
	}
	scrollToBottom(document.getElementById('chatArea'));
}

function updateUserList(refreshXML) {
	var users = refreshXML.getElementsByTagName("users");
	if (users.length == 0)
		return;
	userArray = users[0].getElementsByTagName("user");
	var userList = '';
	for (var i = 0; i < userArray.length; i++) {
		userList += '<div><a href="javascript:showUser(';
		userList += userArray[i].getElementsByTagName("identifier")[0].firstChild.nodeValue + ');"';
		userList += ' title="' + userArray[i].getElementsByTagName("username")[0].firstChild.nodeValue + '"';
		var colors = userArray[i].getElementsByTagName("color");
		if (colors.length > 0)
			userList += ' style="color:' + colors[0].firstChild.nodeValue + '"';
		userList += '>' + dec(userArray[i].getElementsByTagName("name")[0].firstChild.nodeValue) + '</a></div>\n';
	}
	document.getElementById('userList').innerHTML = userList;
	scrollToBottom(document.getElementById('chatArea'));
}

function updateRoomList(refreshXML) {
	var rooms = refreshXML.getElementsByTagName("rooms");
	if (rooms.length == 0)
		return;
	roomArray = rooms[0].getElementsByTagName("room");
	selectRoomElement = document.getElementById("currentRoomList");
	for (var i = selectRoomElement.length - 1; i >= 0; i--) {
		selectRoomElement.options[i] = null;
	}
	for (var i = 0; i < roomArray.length; i++) {
		var selected = (roomArray[i].getElementsByTagName("selected").length > 0)
		selectRoomElement.options[i] =
			new Option(roomArray[i].getElementsByTagName("name")[0].firstChild.nodeValue + " (" + roomArray[i].getElementsByTagName("numOfUsers")[0].firstChild.nodeValue + ")", roomArray[i].getElementsByTagName("identifier")[0].firstChild.nodeValue, selected, selected);
	}
}

function updateObject(refreshXML) {
	var objects = refreshXML.getElementsByTagName("object");
	if (objects.length == 0)
		return;
	objectName = objects[0].getElementsByTagName("name")[0].firstChild.nodeValue;
	document.title += (" - " + objectName);
}

function initChat() {
	send("method=initChat&identifier=" + identifier, true);
	window.setInterval("checkOpener()", 300);
}

function checkOpener() {
/*
	if (window.opener && !window.opener.closed && !window.opener.chatWindows) {
		window.opener.chatWindows = chatWindows;
	}
*/
}

function refresh() {
	send("method=getXML&identifier="+identifier, false);
}

function sendMessage(message) {
	if (message != "") {
		send("method=sendMessage&identifier="+identifier+"&message=" + enc(message), true);
		resetMessageField();
	}
	focusMessageField();
}

function setColor(color) {
	send("method=setColor&identifier="+identifier+"&color="+color, true);
}

function leaveChat() {
	send("method=leaveChat&identifier="+identifier, true, true);
}

function send(paramStr, retry, oneway) {
	if (sending && retry) {
		window.setTimeout('send("' + paramStr + '", true, ' + oneway + ');', RETRY_TIME);
		sending = true;
	} else if (!sending) {
		if (refreshTimeout != null) {
			window.clearTimeout(refreshTimeout);
			refreshTimeout = null;
		}
		var req = newXMLHttpRequest();
		if (oneway !== true) {
			req.onreadystatechange = getReadyStateHandler(req, handleSend);
		}
		req.open("POST", serverName, true);
		req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		req.send(paramStr + "&timestamp=" + timestamp);
	}
}

function handleSend(refreshXML) {
	timestamp = refreshXML.getElementsByTagName("timestamp")[0].firstChild.nodeValue;
	updateRoomList(refreshXML);
	updateChatArea(refreshXML);
	updateUserList(refreshXML);
	updateObject(refreshXML);
	sending = false;
	refreshTimeout = window.setTimeout("refresh()", REFRESH_TIME);
}


function showUser(identifier) {
	// need to override
	url = 'showObject.do?identifier=' + identifier;
	if (window.opener) {
		window.opener.location.href = url;
		window.opener.focus();
	} else {
		window.open(url);
	}
}

/**
 * Rewrite
 **/
function resetMessageField() {
	document.formular.message.value = ' ';
	if (tinyMCE.instances["mce_editor_0"]) {
		tinyMCE.updateContent("message");
		if (tinyMCE.isGecko) {
			tinyMCE.instances["mce_editor_0"].contentDocument.designMode = "off";
			tinyMCE.instances["mce_editor_0"].contentDocument.designMode = "on";
		}
	}
}


/**
 * Focus
 **/
function focusMessageField() {
	if (tinyMCE.instances["mce_editor_0"]) {
		tinyMCE.instances["mce_editor_0"].contentWindow.focus();
	} else {
		document.getElementById("message").focus();
	}
}

function setEventListener(count) {
	if (!tinyMCE.instances["mce_editor_0"]) {
		if (count < 10) {
			window.setTimeout("setEventListener(" + (++count) + ");", 300);
		} else {
			document.getElementById("message").addEventListener("keydown", submitOnReturn, false);
		}
		return;
	}
	var doc = tinyMCE.instances["mce_editor_0"].contentDocument;
	if (tinyMCE.isMSIE) {
		doc.detachEvent("onkeypress", TinyMCE_Engine.prototype._eventPatch);
		doc.detachEvent("onkeydown", TinyMCE_Engine.prototype._eventPatch);
		doc.detachEvent("onkeyup", TinyMCE_Engine.prototype._eventPatch);
	} else {
		doc.removeEventListener("keypress", tinyMCE.handleEvent, false);
		doc.removeEventListener("keydown", tinyMCE.handleEvent, false);
		doc.removeEventListener("keyup", tinyMCE.handleEvent, false);
	}
	tinyMCE.addEvent(tinyMCE.instances["mce_editor_0"].contentDocument, "keydown", submitOnReturn);
}

function submitOnReturn(e) {
	if (e.keyCode == 13 && !e.shiftKey) {
		tinyMCE.cancelEvent(e);
		document.formular.submit();
	}
}

