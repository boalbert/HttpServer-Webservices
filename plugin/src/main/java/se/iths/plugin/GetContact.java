package se.iths.plugin;

import se.iths.plugin.dao.ContactDao;
import se.iths.plugin.dao.JsonConverter;
import se.iths.plugin.model.Contact;
import se.iths.routing.Route;
import se.iths.spi.IoHandler;

@Route(url = "/getcontact")
public class GetContact implements IoHandler {

	@Override
	public byte[] urlHandler(String requestPath, String requestBody, String requestMethod) {

		Contact contact;
		ContactDao contactDao = new ContactDao();

		contact = contactDao.findById(extractContactId(requestPath));
		return returnJson(contact);

	}

	private int extractContactId(String contactString) {

		int indexAt = contactString.indexOf("/", 2) + 1;

		return Integer.parseInt(contactString.substring(indexAt));
	}

	private byte[] returnJson(Contact contact) {
		return new JsonConverter().convertToJson(contact);
	}

	public String extractFirstName(String nameString) {

		int beforeFirstName = nameString.indexOf("=") + 1;

		int afterFirstName = nameString.indexOf("&");

		return nameString.substring(beforeFirstName, afterFirstName);
	}

	public String extractLastName(String nameString) {

		int afterFirstName = nameString.indexOf("&");

		int beforeLastName = nameString.indexOf("=", afterFirstName) + 1;

		return nameString.substring(beforeLastName);
	}
}
