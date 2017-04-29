<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("rootPath", request.getContextPath());
	pageContext.setAttribute("appTitle", "Emerald Paradox Chat");
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${appTitle}</title>
		<link rel="icon" type="image/icon" href="${rootPath}/Images/favicon.ico"/>
		
		<!-- CSS Imports -->
		<!-- + Third-party + -->
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href= "${rootPath}/CSS/chat.css"/>
		
		<!-- JavaScript Imports -->
		<!-- ++ APIs ++ -->
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
		<!-- ++ My Logic ++ -->
		<script type="text/javascript" src="${rootPath}/JavaScript/chat.js"></script>
		<script type="text/javascript" src="${rootPath}/JavaScript/Ng-Controllers/ChatController.js"></script>
		<script type="text/javascript" src="${rootPath}/JavaScript/Ng-Services/ChatService.js"></script>
		<script type="text/javascript" src="${rootPath}/JavaScript/Ng-Constants/CHAT_CONSTANTS.js"></script>
	</head>
	<body ng-app="EmeraldParadoxChat" ng-controller="ChatController">
		<h1 class="MainHeader">${appTitle}</h1>
		<br/>
		<!-- Chat box -->
		<div class="container-fluid">
		<div id="chat" class="row">
			<div class="ChatDisplay">
				<div class="ChatEntry" ng-repeat="message in messages">
					<span class="UserName">{{message.user.userName}}</span>
					<span class="UserNameChatTextSeparator">:</span>
					<span class="ChatText" style="color: {{message.user.hexColor}}">{{message.text}}</span>
				</div>
			</div>
			<form class="ChatInputForm" name="chatForm" autocomplete="off">
				<input id="chatInput" class="TextInput" type="text" name="chatInput" ng-keypress="chatInputKeyPressHandler($event)"/>
				<input id="chatSubmitButton" class="Button" type="button" name="chatButton" value="Chat" ng-click="processChatInput()"/>
				<input id="textColorPicker" class="ColorPicker" type="color" name="textColorPicker" ng-model="loggedInUser.hexColor"/>
				<input id="userNameInput" class="TextInput" type="text" name="userNameInput" ng-model="loggedInUser.userName"/>
			</form>
		</div>
		</div>
	</body>
</html>