var rootContextPath = '/EmeraldParadoxChat/';
app.constant('CHAT_CONSTANTS', {
	GET_MESSAGES_INTERVAL: 100,
	GET_MESSAGES_URL: rootContextPath + 'GetMessages',
	SAVE_MESSAGE_URL: rootContextPath + 'SendMessage'
});