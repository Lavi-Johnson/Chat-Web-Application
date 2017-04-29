app.controller('ChatController', ['$scope', '$interval', 'ChatService', 'CHAT_CONSTANTS', function($scope, $interval, ChatService, CHAT_CONSTANTS) {
	$scope.messages = [];
	
	//Set up the logged in user.
	$scope.loggedInUser = {
			userName: 'Guest' + (new Date()).getTime(),
			hexColor: '#000000'
	};
	
	//Initialize event handlers
	$scope.processChatInput = processChatInput;
	$scope.chatInputKeyPressHandler = chatInputKeyPressHandler;
	
	//Start the message polling interval.
	$interval(ChatService.getMessages, CHAT_CONSTANTS.GET_MESSAGES_INTERVAL, 0, null, processChatMessages);
	
	//========================//
	
	//==== Event handlers ====//
	
	function processChatInput(){
		var user = getLoggedInUser();
		var text = getMessageInput();
		var message = {
				'message': {
					'user': user,
					'text': text
				}
		}

		ChatService.saveMessage(message);
	}
	
	function chatInputKeyPressHandler($event){
		if($event.keyCode == 13){ //13 is the code for the "Enter" key.
			$scope.processChatInput();
			$event.stopPropagation();
			return false;
		}
		return true;
	}
	
	//==== Chat logic ====//
	
	function processChatMessages(response){
		$scope.messages = response.data;
	}
	
	function alertSaveMessageError(){
		alertError('Could not send your message. Please try again.');
	}
	
	function alertError(errorMessage){
		alert(errorMessage);
	}
	
	//==== Helper functions ====//
	
	function getLoggedInUser(){
		
		//TODO: Get logged in user from session information making a back end call.
		
		return $scope.loggedInUser || $scope.guestUser;
	}
	
	function getMessageInput(){
		var input = document.getElementById('chatInput');
		var inputValue = input.value;
		input.value = '';
		return inputValue;
	}
	
	function printValues(values){
		if(values){
			for(var i = 0, length = values.length; i < length; i++){
				console.log(values[i]);
			}
		}
	}
}]);

