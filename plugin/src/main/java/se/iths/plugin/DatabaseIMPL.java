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

        byte[] file = ("<html><body><p>" + contact.getFirstName() + contact.getLastName() + "</p></body></html>").getBytes();

        return file;
    }
}
