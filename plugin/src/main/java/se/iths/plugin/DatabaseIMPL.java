package se.iths.plugin;

import se.iths.plugin.model.Contact;
import se.iths.plugin.model.ContactDaoImpl;
import se.iths.spi.IOhandler;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DatabaseIMPL implements IOhandler {

    @Override
    public byte[] urlHandler(String requestPath) {


        ContactDaoImpl contactDao = new ContactDaoImpl();
        Contact contact = contactDao.findById(extractContactId(requestPath));

        byte[] file = (

                "<html><body>" +
                "<h1>Contact Info</h1>" +
                "<p>Firstname: " + contact.getFirstName() + "</p>" +
                "<p>Lastname: " + contact.getLastName() + "</p>" +
                "</body></html>"

        ).getBytes();

        return file;
    }

    public Contact createContactFromUrl(String requestPath) {

        Contact contact = new Contact();

        contact.setFirstName(extractFirstName(requestPath));
        contact.setLastName(extractLastName(requestPath));

        return contact;
    }

    public static int extractContactId(String contactString) {

        int indexAt = contactString.indexOf("/", 2) + 1;
        int contactId = Integer.parseInt(contactString.substring(indexAt));

        return contactId;
    }

    public static String extractFirstName(String nameString) {

        int beforeFirstName = nameString.indexOf("=") + 1;

        int afterFirstName = nameString.indexOf("&");

        String firstName = nameString.substring(beforeFirstName,afterFirstName);

        return firstName;
    }

    public static String extractLastName(String nameString) {

        int afterFirstName = nameString.indexOf("&");

        int beforeLastName = nameString.indexOf("=", afterFirstName) + 1;

        String lastName = nameString.substring(beforeLastName);

        return lastName;
    }
}
