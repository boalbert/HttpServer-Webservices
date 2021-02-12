package se.iths.httpHandler;

import se.iths.model.HttpRequest;
import se.iths.spi.IoHandler;

import java.util.HashMap;
import java.util.Map;

public class PluginHandler {


    private Map<String, IoHandler> routes = new HashMap<>();
    private HttpRequest httpRequest;

    public PluginHandler(Map<String, IoHandler> routes, HttpRequest httpRequest) {
        this.routes = routes;
        this.httpRequest = httpRequest;
    }


    public byte[] URLHandler(HttpRequest httpRequest) {

        String url = httpRequest.getRequestPath();
        String requestedBody = httpRequest.getRequestBody();
        String requestedMethod = httpRequest.getRequestMethod();

        IoHandler handler = routes.get(url);
        byte[] file;

        if (handler != null) {
            file = handler.urlHandler(url, requestedBody, requestedMethod);
        } else if (url.contains("create")) {
            handler = routes.get("/create");
            file = handler.urlHandler(url, requestedBody, requestedMethod);
        } else {
            handler = routes.get("/404.html"); //todo send status code via httpRespones
            url = "/404.html";

            file = handler.urlHandler(url, requestedBody, requestedMethod);
        }

        return file;
    }
}