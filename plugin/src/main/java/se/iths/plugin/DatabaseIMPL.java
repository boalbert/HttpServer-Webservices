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
        Contact contact = contactDao.findById(1);

        byte[] file = (

                "<html><body>" +
                "<h1>Contact Info</h1>" +
                "<p>Firstname: " + contact.getFirstName() + "</p>" +
                "<p>Lastname: " + contact.getLastName() + "</p>" +
                "</body></html>"

        ).getBytes();

        return file;
    }

    // Read create contact() -> /create?firstname=Johan&lastname=Nilsson

    // TODO Gör en metod som läser ut /contact/2 och hämtar endast '2' och kör findById på den
}
