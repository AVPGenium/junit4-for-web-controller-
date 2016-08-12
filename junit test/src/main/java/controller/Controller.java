package controller;

import service.Request;
import service.RequestHandler;
import service.Response;

public interface Controller {
    Response processRequest(Request request);
    void addHandler(Request request, RequestHandler requestHandler);
}
