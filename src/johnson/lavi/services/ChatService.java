package johnson.lavi.services;

import johnson.lavi.dao.ChatDao;
import johnson.lavi.dao.ChatDaoInterface;
import johnson.lavi.dao.ChatSqlDao;
import johnson.lavi.domain.Message;
import johnson.lavi.domain.User;
import flexjson.JSONSerializer;
import flexjson.JSONDeserializer;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class ChatService {
	private String chatLogDirectoryName;
	private String chatLogFileName;
	private File chatLogFile;
	private ChatDaoInterface chatDao;
	
	public ChatService(){
	}
	
	public ChatService(String chatLogDirectoryName, String chatLogFileName){
		this.chatLogDirectoryName = chatLogDirectoryName;
		this.chatLogFileName = chatLogFileName;
		this.chatLogFile = new File(chatLogDirectoryName + chatLogFileName);
	}
	
	public List getMessages() throws Exception {
		ArrayList<Message> messages;
		ChatDaoInterface chatDao = getChatDao();
		messages = (ArrayList<Message>)chatDao.getMessages();
		return messages;
	}
	
	public void saveMessage(Message message) throws Exception {
		ChatDaoInterface chatDao = getChatDao();
		chatDao.saveMessage(message);
	}
	
	//== Getters and Setters ==//
	
	public ChatDaoInterface getChatDao() {
		return this.chatDao;
	}
	
	public String getChatLogDirectoryName() {
		return chatLogDirectoryName;
	}
	
	public File getChatLogFile() {
		return this.chatLogFile;
	}

	public String getChatLogFileName() {
		return chatLogFileName;
	}
	
	public void setChatDao(ChatDaoInterface chatDao) {
		this.chatDao = chatDao;
	}

	public void setChatLogDirectoryName(String chatLogDirectoryName) {
		this.chatLogDirectoryName = chatLogDirectoryName;
		File updatedChatLogFile = new File(chatLogDirectoryName + this.chatLogFileName);
		setChatLogFile(updatedChatLogFile);
	}
	
	public void setChatLogFile(File chatLogFile) {
		this.chatLogFile = chatLogFile;
		ChatDaoInterface chatDao = getChatDao();
		if(chatDao == null){
			chatDao = new ChatDao();
			setChatDao(chatDao);
		}
		((ChatDao)chatDao).setChatLogFile(getChatLogFile());
	}
	
	public void setChatLogFileName(String chatLogDirectoryName, String chatLogFileName) {
		this.chatLogDirectoryName = chatLogDirectoryName;
		this.chatLogFileName = chatLogFileName;
		File updatedChatLogFile = new File(chatLogDirectoryName + chatLogFileName);
		setChatLogFile(updatedChatLogFile);
	}

	public void setChatLogFileName(String chatLogFileName) {
		this.chatLogFileName = chatLogFileName;
		File updatedChatLogFile = new File(this.chatLogDirectoryName + chatLogFileName);
		setChatLogFile(updatedChatLogFile);
	}
}
