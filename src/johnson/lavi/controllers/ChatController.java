package johnson.lavi.controllers;

import johnson.lavi.services.ChatService;
import johnson.lavi.domain.Message;
import johnson.lavi.domain.User;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class ChatController extends HttpServlet{
//	private static Log4JLogger logger = LogFactory.getLog(ChatController.class);
	private ApplicationContext appContext = new ClassPathXmlApplicationContext("beans.xml");
	private ChatService chatService;
	
	public void init() throws ServletException{
		//Set up the chat service.
		ChatService chatService = appContext.getBean("ChatService", ChatService.class);
		
		////Obtain the file from the web application's root path and supply it to the chat service.
		ServletContext servletContext = getServletContext();
		URL url = null;
		try {
			url = servletContext.getResource(chatService.getChatLogDirectoryName() + chatService.getChatLogFileName());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		File chatLogFile = null;
		try {
			chatLogFile = new File((url.toURI()).getPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}catch(NullPointerException nullPointerException){
			nullPointerException.printStackTrace();
		}
		
		////Clear any existing messages.
		try {
			FileWriter fileWriter = new FileWriter(chatLogFile);
			try{
				fileWriter.write("[]");
			}catch(IOException innerIOException){
				innerIOException.printStackTrace();
			}finally{
				fileWriter.flush();
				fileWriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		////Supply the chat service with the chat log file and save it for future requests.
		chatService.setChatLogFile(chatLogFile);
		setChatService(chatService);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Message> messageList;
		JSONSerializer serializer = new JSONSerializer();
		try {
			messageList = getChatService().getMessages();
		} catch(Exception exception) {
			messageList = new ArrayList<>();
			Message errorMessage = createErrorMessage();
			messageList.add(errorMessage);
		}
		
		//Return the response with the messages inside.
		String messageListJson = serializer.exclude("class").serialize(messageList);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.write(messageListJson);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Extract the message from the POST request.
		String serializedMessage = (String)request.getParameter("message");
		
		System.out.println("Message being saved: " + serializedMessage);
		
		JSONDeserializer<HashMap<String, Object>> deserializer = new JSONDeserializer<>();
		Map<String, Object> dataMap = deserializer.deserialize(serializedMessage);
		Map<String, Object> messageConfiguration = (HashMap<String, Object>)dataMap.get("message");
		
		//Update the chat log file with the new message.
		
		////Get the user's information for the message.
		User user = new User();
		Map<String, Object> userMap = (HashMap<String, Object>)messageConfiguration.get("user");
		user.setUserName((String)userMap.get("userName"));
		user.setHexColor((String)userMap.get("hexColor"));
		
		////Create the message with the information obtained.
		Message message = new Message();
		message.setUser(user);
		message.setText((String)messageConfiguration.get("text"));
		
		////Save the message to the log file.
		String resultStatusMessage;
		try {
			ChatService chatService = getChatService();
			chatService.saveMessage(message);
			resultStatusMessage = "{\"success\":\"true\"}";
		} catch(Exception exception) {
			resultStatusMessage = "{\"success\":\"false\", \"reason\":\"" + exception.getMessage() + "\"}";
		}
		
		// Return a success message.
		response.setContentType("application/json");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(resultStatusMessage);
		} catch(IOException ioException) {
			System.out.println("EXCEPTION: Could not write a response.");
			throw ioException;
		}
	}
	
	//==== Getters and Setters ====//
	
	public ChatService getChatService(){
		return this.chatService;
	}
	
	public void setChatService(ChatService chatService){
		this.chatService = chatService;
	}
	
	//==== Helper Methods ====//
	
	private Message createErrorMessage() {
		User emeraldParadoxChatUser = new User();
		emeraldParadoxChatUser.setUserName("EMERALD PARADOX CHAT");
		emeraldParadoxChatUser.setHexColor("#000000");
		Message errorMessage = new Message();
		errorMessage.setText("There was an error getting the messages. Another attempt will be made soon...");
		errorMessage.setUser(emeraldParadoxChatUser);
		return errorMessage;
	}
}
