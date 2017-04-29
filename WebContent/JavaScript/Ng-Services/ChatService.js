app.factory('ChatService', ['$http', 'CHAT_CONSTANTS', function($http, CHAT_CONSTANTS){
	return {
		getMessages: function(callback){
			$http.get(CHAT_CONSTANTS.GET_MESSAGES_URL)
				.then(callback);
		},
		saveMessage: function(message, callback){
			$http({
				method: 'POST',
				url: CHAT_CONSTANTS.SAVE_MESSAGE_URL,
				    headers: {
				    	'Content-Type': 'application/x-www-form-urlencoded'
				    },
				    transformRequest: function(obj) {
				        var str = [];
				        for(var p in obj)
				        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
				        return str.join("&");
				    },
				    data: {
				    	message: JSON.stringify(message)
				    }
				})
				.then(callback || function(response){
					console.log(response.data);
				});
		}
	};
}]);