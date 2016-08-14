package server;


import org.mortbay.http.HttpContext;
import org.mortbay.http.HttpServer;
import org.mortbay.http.SocketListener;
import org.mortbay.http.handler.ResourceHandler;

public class JettyExample {
    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();
        SocketListener listener = new SocketListener();
        listener.setPort(8080);
        server.addListener(listener);
        HttpContext context = new HttpContext();
        context.setContextPath("/");
        context.setResourceBase("./");
        context.addHandler(new ResourceHandler());
        server.addContext(context);
        server.start();
    }
}
