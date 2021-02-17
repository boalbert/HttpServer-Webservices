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

	/**
	 * Returns either a list of all contacts (/contacts/), or
	 * a single contact (/contacts/id), or if contact is not found
	 * an empty byte[], which ResponseHandler interprets as 404.
	 */
	@Override
	public byte[] urlHandler(String requestPath, String requestBody, String requestMethod) {
		ContactDao contactDao = new ContactDao();

		if (requestPath.equals("/contacts/"))
			return returnListAsJson(contactDao.findAll()); // Return a list of all contacts
		if (contactDao.findById(extractContactId(requestPath)) == null) // Return byte[0], responsehandler will return 404
			return new byte[0];
		return returnObjectAsJson(contactDao.findById(extractContactId(requestPath))); // Return list of contacts if id is specified

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
