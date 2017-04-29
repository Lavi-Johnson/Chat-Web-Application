package johnson.lavi.dao;

import johnson.lavi.domain.Message;
import johnson.lavi.domain.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class ChatDao implements ChatDaoInterface{
	private File chatLogFile;
	
	public ChatDao(){
	}
	
	public ChatDao(File chatLogFile){
		setChatLogFile(chatLogFile);
	}

	@Override
	public List<Message> getMessages(){
		List<Message> messages = new ArrayList<Message>();
		
		//Read messages from the chat log file.
		String chatLogContents = "";
		String line;
		try{
			File chatLogFile = getChatLogFile();
			FileReader chatLogReader = new FileReader(chatLogFile);
			BufferedReader bufferedChatLogReader = new BufferedReader(chatLogReader);
			try{
				while((line = bufferedChatLogReader.readLine()) != null){
					chatLogContents += line;
				}
			}catch(IOException ioException){
				System.out.println("The file \"" + chatLogFile.getAbsolutePath() + "\" could not be read from.");
				ioException.printStackTrace();
			}finally{
				bufferedChatLogReader.close();
			}
		}catch(FileNotFoundException fileNotFoundException){
			fileNotFoundException.printStackTrace();
		}catch(IOException ioException){
			System.out.println("The file \"" + chatLogFile.getAbsolutePath() + "\" could not be closed.");
		}
		
		//Transform the chat log contents into a list of Message objects.
		JSONDeserializer<ArrayList<Message>> deserializer = new JSONDeserializer<>();
		messages = deserializer.deserialize(chatLogContents);
//		chatLogContentsList.stream()
//			.forEach(savedMessage -> {
//				Message message = new Message();
//				message.setText((String)savedMessage.get("text"));
//				User user = new User((String)savedMessage.get("user"));
//				String hexColor = (String)savedMessage.get("hexColor");
//				if(hexColor != null && hexColor.trim() != ""){
//					user.setHexColor(hexColor);
//				}
//				message.setUser(user);
//				messages.add(message);
//			});
		return messages;
	}

	@Override
	public void saveMessage(Message message) {
		File chatLogFile = getChatLogFile();
		FileWriter fileWriter;
		if(chatLogFile == null){
			System.out.println("A chat log file has not been provided to the service. Canceling the save.");
		}else{
			JSONSerializer serializer = new JSONSerializer();
			List<Message> messages = getMessages();
			messages.add(message);
			String updatedChatLogContentsJson = serializer.serialize(messages);
			try{
				fileWriter = new FileWriter(chatLogFile);
				try{
					fileWriter.write(updatedChatLogContentsJson);
				}catch(IOException innerIoException){
					System.out.println("EXCEPTION: Unable to write to the chat log file.");
					innerIoException.printStackTrace();
				}finally{
					fileWriter.flush();
					fileWriter.close();
				}
			}catch(IOException ioException){
				System.out.println("EXCEPTION: Unable to open a stream to the chat log file.");
				ioException.printStackTrace();
			}
		}
	}
	
	//==== Getters and Setters ====//

	public File getChatLogFile() {
		return chatLogFile;
	}

	public void setChatLogFile(File chatLogFile) {
		this.chatLogFile = chatLogFile;
	}
}
