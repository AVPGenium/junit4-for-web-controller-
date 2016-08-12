package controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.ErrorResponse;
import service.Request;
import service.RequestHandler;
import service.Response;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestDefaultController {
    private DefaultController controller;
    private Request request;
    private RequestHandler handler;

    @Before
    public void setup(){
        controller = new DefaultController();
        request = new TestRequest();
        handler = new TestHandler();
        controller.addHandler(request, handler);
    }

    @Test
    public void testAddHandler(){
        RequestHandler handler2 = controller.getHandler(request);
        Assert.assertSame(handler2, handler);
    }

    @Test
    public void testProcessRequest(){
        Response response = controller.processRequest(request);
        Assert.assertNotNull("Must not return a null response", response);
        //Assert.assertEquals(TestResponse.class, response.getClass()); //before
        Assert.assertEquals(new TestResponse(), response); // after
    }

    @Test(expected = Exception.class)
    public void testProcessRequestAnswersErrorResponse(){
        TestRequest request = new TestRequest("testError");
        TestExceptionHandler handler = new TestExceptionHandler();
        controller.addHandler(request, handler);
        Response response = controller.processRequest(request);
        Assert.assertNotNull("Must not return a null response", response);
        Assert.assertEquals(ErrorResponse.class, response.getClass());
    }

    @Test
    public void testGetHandlerNotDefined(){
        TestRequest request = new TestRequest("testNotDefined");
        try{
            controller.getHandler(request);
            fail("An exception should be raised if the requested " +
                    "handler has not been registered");
        } catch (RuntimeException expected){
            assertTrue(true);
        }
    }

    @Test
    public void testAddRequestDuplicateName(){
        TestRequest request = new TestRequest();
        TestHandler handler = new TestHandler();
        try{
            controller.addHandler(request, handler);
            fail("An exception should be raised if the default " +
                    "TestRequest has already been registered");
        } catch (RuntimeException expected){
            assertTrue(true);
        }
    }

    private class TestRequest implements Request{
        private static final String DEFAULT_NAME = "Test";
        private String name;

        public TestRequest(String name){
            this.name = name;
        }

        public TestRequest() {
            this.name = DEFAULT_NAME;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private class TestHandler implements RequestHandler{

        @Override
        public Response process(Request request) throws Exception {
            return new TestResponse();
        }
    }

    private class TestExceptionHandler implements RequestHandler{

        @Override
        public Response process(Request request) throws Exception {
            throw  new Exception("error processing request");
        }
    }

    private class TestResponse implements Response{
        private static final String NAME = "Test";

        public String getName(){
            return NAME;
        }

        @Override
        public int hashCode() {
            return NAME.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            boolean result = false;
            if(obj instanceof TestResponse){
                result = ((TestResponse) obj).getName().equals(getName());
            }
            return result;
        }
    }
}
