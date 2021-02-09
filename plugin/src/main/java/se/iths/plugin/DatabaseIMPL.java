package se.iths.plugin;

import se.iths.plugin.model.Contact;
import se.iths.plugin.model.ContactDaoImpl;
import se.iths.spi.IOhandler;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DatabaseIMPL implements IOhandler {

    @Override
    public String urlHandler(String url, Socket socket) throws IOException {

        ContactDaoImpl contactDao = new ContactDaoImpl();
        Contact contact = contactDao.findById(1);

        var output = new BufferedOutputStream(socket.getOutputStream());

        byte[] file = ("<html><body><p>" + contact.getFirstName() + contact.getLastName() + "</p></body></html>").getBytes();
        // byte[] file = ("<html><body><p>Hello</p></body></html>").getBytes();

        output.write(("HTTP/1.1 200 OK" + "\r\n").getBytes());
        output.write(("Content-Type: text/html" + "\r\n").getBytes());
        output.write(("Content-Length: " + file.length).getBytes());
        output.write(("\r\n\r\n").getBytes());
        output.write(file);
        output.write(("\r\n").getBytes());
        output.flush();
        output.close();
        socket.close();

        return null;
    }
}
