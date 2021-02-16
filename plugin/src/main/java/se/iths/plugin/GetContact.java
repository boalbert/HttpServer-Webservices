package se.iths.plugin;

import se.iths.plugin.dao.ContactDao;
import se.iths.plugin.model.Contact;
import se.iths.routing.Route;
import se.iths.spi.IoHandler;

@Route(url = "/getcontact")
public class GetContact implements IoHandler {

	@Override
	public byte[] urlHandler(String requestPath, String requestBody, String requestMethod) {

		Contact contact;
		ContactDao contactDao = new ContactDao();

		if (requestPath.contains("/getjson")) {
			contact = contactDao.findById(extractContactId(requestPath));
			return returnJson(contact);
		} else {
			contact = contactDao.findById(extractContactId(requestPath));
			return returnHtml(contact);
		}
	}

	private byte[] returnJson(Contact contact) {
		return ("{firstname: '" + contact.getFirstName() + "', lastname: '" + contact.getLastName() + "'}").getBytes();
	}

	private byte[] returnHtml(Contact contact) {

		return (

				"<html><body>" +
						"<h1>Contact Info</h1>" +
						"<p>Firstname: " + contact.getFirstName() + "</p>" +
						"<p>Lastname: " + contact.getLastName() + "</p>" +
						"</body></html>"

		).getBytes();
	}

	private static int extractContactId(String contactString) {

		int indexAt = contactString.indexOf("/", 2) + 1;
		int contactId = Integer.parseInt(contactString.substring(indexAt));

		return contactId;
	}

	public static String extractFirstName(String nameString) {

		int beforeFirstName = nameString.indexOf("=") + 1;

		int afterFirstName = nameString.indexOf("&");

		String firstName = nameString.substring(beforeFirstName, afterFirstName);

		return firstName;
	}

	public static String extractLastName(String nameString) {

		int afterFirstName = nameString.indexOf("&");

		int beforeLastName = nameString.indexOf("=", afterFirstName) + 1;

		String lastName = nameString.substring(beforeLastName);

		return lastName;
	}
}
