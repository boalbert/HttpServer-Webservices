package se.iths.plugin;

import se.iths.plugin.model.Contact;
import se.iths.plugin.dao.ContactDao;
import se.iths.routing.Route;
import se.iths.spi.IoHandler;

@Route(url = "/postcontact")
public class PostContact extends GetContact implements IoHandler {

	@Override
	public byte[] urlHandler(String requestPath, String requestBody, String requestMethod) {

		ContactDao contactDao = new ContactDao();

		String firstName = extractFirstName(requestBody);
		String lastName = extractLastName(requestBody);

		Contact contact = new Contact(firstName,lastName);

		contactDao.createContact(contact);

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