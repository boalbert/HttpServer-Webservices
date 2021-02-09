package se.iths.spi;

import java.io.IOException;
import java.net.Socket;

public interface IOhandler {

    public byte[] urlHandler(String requestPath);

}
