package se.iths.plugin;

import se.iths.plugin.dao.ContactDao;
import se.iths.plugin.model.Contact;
import se.iths.plugin.util.JsonConverter;
import se.iths.routing.Route;
import se.iths.spi.IoHandler;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Route(url = "/contacts")
public class GetContact implements IoHandler {

	@Override
	public byte[] urlHandler(String requestPath, String requestBody, String requestMethod) {
		ContactDao contactDao = new ContactDao();

		if (requestPath.equals("/contacts/")) {
			return returnListAsJson(contactDao.findAll());
		} else {
			return returnListAsJson(contactDao.findContactById(extractContactId(requestPath)));
		}
	}

	public byte[] returnListAsJson(List<Contact> list) {
		return new JsonConverter().convertListToJson(list);
	}

	private int extractContactId(String contactString) {

		int indexAt = contactString.indexOf("/", 2) + 1;

		return Integer.parseInt(contactString.substring(indexAt));
	}

	public byte[] returnObjectAsJson(Contact contact) {
		return new JsonConverter().convertObjectToJson(contact);
	}

	public String extractFirstName(String nameString) {

		int beforeFirstName = nameString.indexOf("=") + 1;

		int afterFirstName = nameString.indexOf("&");

		String firstName = nameString.substring(beforeFirstName, afterFirstName);

		return URLDecoder.decode(firstName, StandardCharsets.ISO_8859_1);
	}

	public String extractLastName(String nameString) {

		int afterFirstName = nameString.indexOf("&");

		int beforeLastName = nameString.indexOf("=", afterFirstName) + 1;

		String lastName = nameString.substring(beforeLastName);

		return URLDecoder.decode(lastName, StandardCharsets.ISO_8859_1);
	}
}
