package se.iths.plugin;

import se.iths.plugin.dao.ContactDao;
import se.iths.plugin.model.Contact;
import se.iths.routing.Route;
import se.iths.spi.IoHandler;

@Route(url = "/insertcontactviaget")
public class GetContactInsert extends GetContact implements IoHandler {

	@Override
	public byte[] urlHandler(String requestPath, String requestBody, String requestMethod) {

		ContactDao contactDao = new ContactDao();

		String firstName = extractFirstName(requestPath);
		String lastName = extractLastName(requestPath);

		Contact contact = new Contact(firstName, lastName);
		contactDao.createContact(contact);

		return returnHtml(contact);
	}

	private byte[] returnHtml(Contact contact) {

		byte[] file = (
				"<html><body>" +
				"<h1>Contact Info</h1>" +
				"<p>Firstname: " + contact.getFirstName() + "</p>" +
				"<p>Lastname: " + contact.getLastName() + "</p>" +
				"</body></html>"
		).getBytes();

		return file;
	}
}
