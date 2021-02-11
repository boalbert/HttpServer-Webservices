package se.iths.plugin;

import se.iths.plugin.model.Contact;
import se.iths.plugin.model.ContactDaoImpl;
import se.iths.spi.IOhandler;

public class PostDatabaseIMPL extends DatabaseIMPL implements IOhandler {
	@Override
	public byte[] urlHandler(String requestPath, String requestBody, String requestMethod) {

		ContactDaoImpl contactDao = new ContactDaoImpl();

		String firstName = extractFirstName(requestBody);
		String lastName = extractLastName(requestBody);

		Contact contact = new Contact(firstName,lastName);

		contactDao.createContact(contact);

		//TODO Ändra till .json-format
		byte[] file = (

				"<html><body>" +
						"<h1>Inserted via POST</h1>" +
						"<p>Firstname: " + contact.getFirstName() + "</p>" +
						"<p>Lastname: " + contact.getLastName() + "</p>" +
						"</body></html>"

		).getBytes();

		return file;
	}
}
