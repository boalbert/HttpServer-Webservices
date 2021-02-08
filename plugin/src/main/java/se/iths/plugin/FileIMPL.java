package se.iths.plugin;

import se.iths.spi.IOhandler;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;


public class FileIMPL implements IOhandler {

    @Override
    public String urlHandler(String url, Socket socket) throws IOException {

        var output = new PrintWriter(socket.getOutputStream());

        File file = new File("/Users/jannismuller/Documents/Jannis/It-högskolan/Javautvecklare 2020/Kurser/Webservices & Integration/Laboration1/"+ File.separator + url);
        byte[] page = readFromFile(file);

        String contentType = Files.probeContentType(file.toPath());

        output.println("HTTP/1.1 200 OK");
        output.println("Content-Length:" + page.length);
        output.println("Content-Type:"+contentType);  //application/json
        output.println("");
        output.println(page);

        output.flush();

        var dataOut = new BufferedOutputStream(socket.getOutputStream());

        dataOut.write(page);
        dataOut.flush();

        socket.close();
        return null;
    }




    public static byte[] readFromFile(File file) {
        byte[] content = new byte[0];
        System.out.println("Does file exists: " + file.exists());
        if (file.exists() && file.canRead()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                content = new byte[(int) file.length()];
                int count = fileInputStream.read(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }




}
