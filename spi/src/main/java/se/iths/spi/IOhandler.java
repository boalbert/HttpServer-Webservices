package se.iths.spi;

import java.io.IOException;
import java.net.Socket;

public interface IOhandler {

    String urlHandler(String url, Socket socket) throws IOException;

}
