package johnson.lavi.dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import johnson.lavi.domain.Message;

public class ChatSqlDao implements ChatDaoInterface {
	private String rdbms = "mysql";
	private String hostname = "localhost";
	private String port = "3306";
	private String database = "EmeraldParadoxChat";
	private String jdbcUrl = "jdbc:" + rdbms + "://" + hostname + ":" + port + "/" + database;
	private String user = "root";
	private String password = "";
	
	public ChatSqlDao() {
		
	}
	
	@Override
	public List<Message> getMessages() throws Exception {
		
		List<Message> messages = new ArrayList<>();
		// Get a connection to the database.
		Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
		// Prepare the SQL statement.
		
		// Send off the SQL statement.
		// Transform the results into messages.
		return messages;
	}

	@Override
	public void saveMessage(Message message) {
		// Prepare the SQL statement.
		// Send off the SQL statement.
	}
}
