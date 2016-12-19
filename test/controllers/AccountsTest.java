package controllers;

import static org.junit.Assert.*;

import org.junit.Test;

import models.Activity;
import models.Fixtures;
import models.User;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Http.RequestBuilder;

import static play.test.Helpers.DELETE;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.PUT;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static org.hamcrest.CoreMatchers.equalTo;
import play.mvc.Result;
import play.test.WithApplication;


public class AccountsTest extends WithApplication {
	
	
	@Test
	 public void POSTONAccountsRegister()
	 {
		RequestBuilder registerRequest = new RequestBuilder()
                .method(POST)
                .uri("/register")
			    .bodyJson(Json.parse(Fixtures.userJson));
		
		Result result = route(registerRequest);
        assertThat(result.status(), equalTo(OK));
	 }
	
	@Test
	 public void POSTONAccountsAuthenticate()
	 {
		RequestBuilder authenticateRequest = new RequestBuilder()
               .method(POST)
               .uri("/authenticate")
			    .bodyJson(Json.parse(Fixtures.loginJson));
		
		//using 303 as server cannot issue a 200 when returning a person info
		Result authenticateresult = route(authenticateRequest);
       assertThat(authenticateresult.status(), equalTo(303));
	 }
	
	@Test
	 public void GetONAccountsviewUserDetails()
	 {
		
		RequestBuilder viewuserRequest = new RequestBuilder()
			 .session("email","homer@simpson.com")
             .method(GET)
             .uri("/dashboard/details");
		
		Result viewuserresult = route(viewuserRequest);
     assertThat(viewuserresult.status(), equalTo(OK));
	 } 
	
	@Test
	 public void GetONAccountseditUserDetails()
	 {
		
		RequestBuilder edituserRequest = new RequestBuilder()
			 .session("email","homer@simpson.com")
            .method(GET)
            .uri("/dashboard/edituser");
		
		Result edituserresult = route(edituserRequest);
    assertThat(edituserresult.status(), equalTo(OK));
	 } 
	
	@Test
	 public void PUTONAccountsUpdateUser()
	 {
		
		RequestBuilder updateRequest = new RequestBuilder()
			  .session("email","homer@simpson.com")
              .method(PUT)
              .uri("/dashboard/edituser/submit")
			  .bodyJson(Json.parse(Fixtures.subuserJson));
		
		Result updateresult = route(updateRequest);
      assertThat(updateresult.status(), equalTo(OK));
	 } 
}
