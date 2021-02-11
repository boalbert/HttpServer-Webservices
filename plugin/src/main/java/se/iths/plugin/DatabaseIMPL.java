package se.iths.plugin;

import se.iths.plugin.model.Contact;
import se.iths.plugin.model.ContactDaoImpl;
import se.iths.spi.IOhandler;

public class DatabaseIMPL implements IOhandler {

	@Override
	public byte[] urlHandler(String requestPath, String requestBody, String requestMethod) {

		Contact contact = new Contact();
		ContactDaoImpl contactDao = new ContactDaoImpl();

		if (requestPath.contains("/findcontact")) {
			contact = contactDao.findById(extractContactId(requestPath));
		} else {
			String firstName = extractFirstName(requestPath);
			String lastName = extractLastName(requestPath);
			contact.setFirstName(firstName);
			contact.setLastName(lastName);
			contactDao.createContact(contact);
		}

		//TODO Ändra till .json-format
		byte[] file = (

				"<html><body>" +
						"<h1>Contact Info</h1>" +
						"<p>Firstname: " + contact.getFirstName() + "</p>" +
						"<p>Lastname: " + contact.getLastName() + "</p>" +
						"</body></html>"

		).getBytes();

		return file;
	}

	public static int extractContactId(String contactString) {

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

	public Contact createContactFromUrl(String requestPath) {

		Contact contact = new Contact();

		contact.setFirstName(extractFirstName(requestPath));
		contact.setLastName(extractLastName(requestPath));

		return contact;
	}
}
