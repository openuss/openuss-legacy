var rewriteTitle = true;
var userId = -1;
var roomId = -1;
var roomName = "";
var lastMessage = -1;
var refreshRunning = false;
var server_name = "";
var interval;

/*
 * enters text by Enter-event
 */ 
function send(e){
	var whichCode;
	whichCode = (window.Event) ? e.which : e.keyCode;
	if ( whichCode == "13" ) {
   		sendMessage(document.formular.message.value); 
		document.formular.message.value = '';
		whichCode = null;   	 
 	}
}

/*
 * scrolls to the end of the chat 
 */
function scrollToBottom (element) {
    element.scrollTop = element.scrollHeight;
}

/*
 * changes chat topic
 */
function changeTopic(topic) {
	var req = newXMLHttpRequest();
	
	req.onreadystatechange = getReadyStateHandler(req, function() {});
	
	req.open("POST", server_name, true);
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	var now = new Date(); // needed to prevent hashing, especially in IE
	req.send("action=topic&roomId="+roomId+"&userId="+userId+"&topic="+enc(topic)+"&time="+now.getTime());
}

/*
 * sends chat message
 */
function sendMessage(message) {
	while (refreshRunning) {
		// wait
	}
	if (userId != 0 && !refreshRunning && message != "") {
		refreshRunning = true;
		var req = newXMLHttpRequest();

		req.onreadystatechange = getReadyStateHandler(req, handleSend);
		
		req.open("POST", server_name, true);
		req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		var now = new Date(); // needed to prevent hashing, especially in IE
		req.send("action=send&roomId="+roomId+"&userId="+userId+"&message="+enc(message)+"&lastMessage="+lastMessage+"&time="+now.getTime());
		refreshRunning = false;
	}
}

/*
 * register new user with chat servlet
 */
function register(s_name, docId) {

	server_name = s_name;
	roomId = docId;

	var req = newXMLHttpRequest();
 
	// use synchronous http-request (third parameter equals 'false'):
	// avoids 'dead users' in chat window when quickly closing window after opening
	req.open("POST", server_name, false);
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	var now = new Date(); // needed to prevent hashing, especially in IE
	req.send("action=enter&roomId="+roomId+"&time="+now.getTime());
 
	var userXML = req.responseXML;
	userId=userXML.getElementsByTagName("user")[0].firstChild.nodeValue;
	lastMessage = userXML.getElementsByTagName("last")[0].firstChild.nodeValue - 1;
}

/*
 * delete user when leaving
 */
function leave() {
	if (userId != 0) {
		var req = newXMLHttpRequest();
		 
		// use synchronous http-request (third parameter equals 'false'):
		// will avoid strange behaviour (request not finished; error message)
		// caused by the browser destroying the request-object when closing window
		// before request has been send or before handler function is called
		req.open("POST", server_name, false);
		req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		var now = new Date(); // needed to prevent hashing, especially in IE
		req.send("action=leave&roomId="+roomId+"&userId="+userId+"&time="+now.getTime());  
	}
}

/*
 * start refresh
 */
function refresh() {
	
	// User ID must have been set before first refresh, might not be the case due to asynchronous requests
	if (userId != 0 && !refreshRunning) {
		refreshRunning = true;
		var req = newXMLHttpRequest();
		
		req.onreadystatechange = getReadyStateHandler(req, handleRefresh);
		
		req.open("POST", server_name, true);
		req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		var now = new Date(); // needed to prevent hashing, especially in IE
		req.send("action=refresh&roomId="+roomId+"&userId="+userId+"&lastMessage="+lastMessage+"&time="+now.getTime());
		refreshRunning = false;
	}
}

/*
 * rebuilds view with refreshed data
 */
function handleRefresh(refreshXML) {
	if (lastMessage == refreshXML.getElementsByTagName("last")[0].firstChild.nodeValue) return;
	if (rewriteTitle) {
		document.title = document.title + ' - ' + refreshXML.getElementsByTagName("documentName")[0].firstChild.nodeValue; 
		rewriteTitle = false;
	}	

	/*
	if (refreshXML.getElementsByTagName("topic")[0].firstChild)
		document.formular.topic.value = dec(refreshXML.getElementsByTagName("topic")[0].firstChild.nodeValue); 
 	*/
	userArray = refreshXML.getElementsByTagName("users")[0].getElementsByTagName("user");
	newValue = "";
	for (var i = 0; i < userArray.length; i++) {
		newValue += dec(userArray[i].firstChild.nodeValue) + "\n";
	}
	if (document.formular.usersonline.value != newValue) document.formular.usersonline.value = newValue;

	msgArray = refreshXML.getElementsByTagName("messages")[0].getElementsByTagName("message");
	for (var i = 0; i < msgArray.length; i++) {
		if (refreshXML.getElementsByTagName("sender")[0].firstChild)
			document.formular.messagefield.value += dec(msgArray[i].getElementsByTagName("sender")[0].firstChild.nodeValue + "(" + msgArray[i].getElementsByTagName("creationtime")[0].firstChild.nodeValue + ")" + ": ");
		if (refreshXML.getElementsByTagName("content")[0].firstChild)
			document.formular.messagefield.value += dec(msgArray[i].getElementsByTagName("content")[0].firstChild.nodeValue) + "\n";
		else document.formular.messagefield.value += "\n";  
	}
 
	lastMessage = refreshXML.getElementsByTagName("last")[0].firstChild.nodeValue;
	scrollToBottom(document.formular.messagefield);
}

// used when sending to quickly refresh
function handleSend(refreshXML) {
	if (lastMessage == refreshXML.getElementsByTagName("last")[0].firstChild.nodeValue) return;

	msgArray = refreshXML.getElementsByTagName("messages")[0].getElementsByTagName("message");
	for (var i = 0; i < msgArray.length; i++) {
		if (refreshXML.getElementsByTagName("sender")[0].firstChild)
			document.formular.messagefield.value += dec(msgArray[i].getElementsByTagName("sender")[0].firstChild.nodeValue + "(" + msgArray[i].getElementsByTagName("creationtime")[0].firstChild.nodeValue + ")" + ": ");
		if (refreshXML.getElementsByTagName("content")[0].firstChild)
			document.formular.messagefield.value += dec(msgArray[i].getElementsByTagName("content")[0].firstChild.nodeValue) + "\n";
		else
			document.formular.messagefield.value += "\n";
	}

	lastMessage = refreshXML.getElementsByTagName("last")[0].firstChild.nodeValue;
	scrollToBottom(document.formular.messagefield);
	refreshRunning = false;
}

function enc(text) {
	return encodeURIComponent(escape(text));
}

function dec(text) {
	return unescape(text);
}

function initChat(servername, objectId) {
	register(servername, objectId);
	refresh();
	interval=setInterval(refresh, 1500);
	focusMessageField();
}

function destroyChat() {
	window.clearInterval(interval);
	leave();
}

function doSend() {
//	sendMessage(escape(document.formular.message.value));
	sendMessage(document.formular.message.value);
	document.formular.message.value = '';
	scrollToBottom(document.formular.messagefield);
	focusMessageField();
}

// focuses on the message input field
function focusMessageField() {
	document.getElementsByName('message')[0].focus();
}
