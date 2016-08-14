package client;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.http.*;
import org.mortbay.http.handler.AbstractHttpHandler;
import org.mortbay.http.handler.NotFoundHandler;
import org.mortbay.http.handler.ResourceHandler;
import org.mortbay.util.ByteArrayISO8859Writer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class TestWebClientSkeleton {
    private static HttpServer server;

    @BeforeClass
    public static void setup() throws Exception {
        server = new HttpServer();
        SocketListener listener = new SocketListener();
        listener.setPort(8080);
        server.addListener(listener);

        HttpContext contextOk = new HttpContext();
        contextOk.setContextPath("/testGetContentOk");
        contextOk.addHandler(new TestGetContentOkHandler());
        server.addContext(contextOk);

        HttpContext contextNotFound = new HttpContext();
        contextNotFound.setContextPath("/testGetContentNotFound");
        contextNotFound.addHandler(new NotFoundHandler());
        server.addContext(contextNotFound);

        server.start();
    }

    @Test
    public void testGetContentOk() throws Exception{
        WebClient client = new WebClient();
        String result = client.getContent(new URL("http://localhost:8080/testGetContentOk"));
        Assert.assertEquals("It works", result);
    }

    @Test
    public void testGetContentNotFound() throws MalformedURLException {
        WebClient client = new WebClient();
        String result = client.getContent(new URL("http://localhost:8080/testGetContentNotFound"));
        Assert.assertNull(result);
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        server.stop();
    }

    private static class TestGetContentOkHandler extends AbstractHttpHandler{

        @Override
        public void handle(String pathContext, String pathParams, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
            OutputStream out = httpResponse.getOutputStream();
            ByteArrayISO8859Writer writer = new ByteArrayISO8859Writer();
            writer.write("It works");
            writer.flush();
            httpResponse.setIntField(HttpFields.__ContentLength, writer.size());
            writer.writeTo(out);
            out.flush();
            httpRequest.setHandled(true);
        }
    }

    private class TestGetContentServerErrorHandlerextends extends AbstractHttpHandler{

        @Override
        public void handle(String pathContext, String pathParams, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
            httpResponse.sendError(HttpResponse.__503_Service_Unavailable);
        }
    }
}
