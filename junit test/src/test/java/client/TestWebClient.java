package client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.*;

public class TestWebClient {
    @Before
    public void setup(){
        URL.setURLStreamHandlerFactory(new StubStreamHandlerFactory());
    }

    @Test
    public void testGetContentOk() throws MalformedURLException {
        WebClient client = new WebClient();
        String result = client.getContent(new URL("http://localhost"));
        Assert.assertEquals("It works", result);
    }

    private class StubStreamHandlerFactory implements URLStreamHandlerFactory{

        @Override
        public URLStreamHandler createURLStreamHandler(String protocol) {
            return new StubHttpURLStreamHandler();
        }
    }

    private class StubHttpURLStreamHandler extends URLStreamHandler{

        @Override
        protected URLConnection openConnection(URL url) throws IOException {
            return new StubHttpURLConnection(url);
        }
    }
}
