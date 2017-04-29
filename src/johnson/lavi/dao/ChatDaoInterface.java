package johnson.lavi.dao;

import java.util.List;

import johnson.lavi.domain.Message;

public interface ChatDaoInterface {
	public List<Message> getMessages() throws Exception;
	public void saveMessage(Message message) throws Exception;
}
