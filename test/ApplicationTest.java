import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.DELETE;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.PUT;
import static play.test.Helpers.route;

import org.junit.Test;

import parsers.JsonParser;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Http.RequestBuilder;
import play.test.WithApplication;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * Simple (JUnit) tests that calls all none parameterized routes to check they load OK.
 *
 */
public class ApplicationTest extends WithApplication {

   	 
        @Test
        public void testThatWelcomePageWorks() {
            RequestBuilder response = new RequestBuilder()
  	              .method(GET)
  	              .uri("/");
  	      Result response1 = route(response);
  	      assertThat(response1.status(), equalTo(OK));
  	      //assertEquals("text/html", result.contentType().get()); 
          //assertEquals("utf-8", result.charset().get());
        }
        
        @Test
        public void testLoginPageWorks() {
            RequestBuilder response = new RequestBuilder()
  	              .method(GET)
  	              .uri("/login");
  	      Result response1 = route(response);
  	      assertThat(response1.status(), equalTo(OK));
        }
        
        @Test
        public void testSignUpPageWorks() {
            RequestBuilder response = new RequestBuilder()
  	              .method(GET)
  	              .uri("/signup");
  	      Result response1 = route(response);
  	      assertThat(response1.status(), equalTo(OK));
        }
        
        @Test
        public void testLogOutPageWorks() {
            RequestBuilder response = new RequestBuilder()
  	              .method(GET)
  	              .uri("/logout");
  	      Result response1 = route(response);
  	      assertThat(response1.status(), equalTo(OK));
        }
        
        @Test
        public void testUploadPageWorks() {
            RequestBuilder response = new RequestBuilder()
  	              .method(GET)
  	              .uri("/upload");
  	      Result response1 = route(response);
  	      assertThat(response1.status(), equalTo(OK));
        }
        
        @Test
        public void testFindFriendsWorks() {
            RequestBuilder response = new RequestBuilder()
  	              .method(GET)
  	              .uri("/dashboard/findFriends");
  	      Result response1 = route(response);
  	      assertThat(response1.status(), equalTo(OK));
        }
        	
}
