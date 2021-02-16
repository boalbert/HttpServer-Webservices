package se.iths.plugin;

import se.iths.plugin.dao.ContactDao;
import se.iths.plugin.dao.JsonConverter;
import se.iths.plugin.model.Contact;
import se.iths.routing.Route;
import se.iths.spi.IoHandler;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Route(url = "/getcontact")
public class GetContact implements IoHandler {

	@Override
	public byte[] urlHandler(String requestPath, String requestBody, String requestMethod) {

		Contact contact;
		ContactDao contactDao = new ContactDao();

		contact = contactDao.findById(extractContactId(requestPath));
		return returnJson(contact);

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

	private int extractContactId(String contactString) {

		int indexAt = contactString.indexOf("/", 2) + 1;

		return Integer.parseInt(contactString.substring(indexAt));
	}

	public byte[] returnJson(Contact contact) {
		return new JsonConverter().convertToJson(contact);
	}
}
