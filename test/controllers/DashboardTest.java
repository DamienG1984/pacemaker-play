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

public class DashboardTest extends WithApplication
{
	@Test
	 public void GetOndashboardindex()
	 {
		RequestBuilder dashboardRequest = new RequestBuilder()
				 .session("email","homer@simpson.com")
	             .method(GET)
	             .uri("/dashboard");
			
			Result dashboardresult = route(dashboardRequest);
	     assertThat(dashboardresult.status(), equalTo(OK));
	 }
	
	@Test
	public void POSTOndashboardActivity()
	 {
		RequestBuilder creatActivityRequest = new RequestBuilder()
				 .session("email","homer@simpson.com")
	             .method(POST)
	             .uri("/submitactivity")
	             .bodyJson(Json.parse(Fixtures.activityJson));
			
			Result creatActivityresult = route(creatActivityRequest);
	     assertThat(creatActivityresult.status(), equalTo(303));
	 }
	
	@Test
	 public void GetOndashboardEditActivity()
	 {
		//invoke createActivity to add an activity to default user
	    assertThat(addActivityToUser().status(), equalTo(OK));
	    assertThat(Activity.findAll().size(), equalTo(1));
	    
		RequestBuilder editActivityRequest = new RequestBuilder()
				 .session("email","homer@simpson.com")
	             .method(GET)
	             .uri("/dashboard/editActivity/1");
			
			Result editActivityresult = route(editActivityRequest);
	     assertThat(editActivityresult.status(), equalTo(OK));
	 }
	
	@Test
	 public void PUTOndashboardUpdateActivity()
	 {
		//invoke createActivity to add an activity to default user
	    assertThat(addActivityToUser().status(), equalTo(OK));
	    assertThat(Activity.findAll().size(), equalTo(1));
		
		RequestBuilder updateRequest = new RequestBuilder()
				 .session("email","homer@simpson.com")
	             .method(PUT)
	             .uri("/dashboard/editactivity/submit")
	             .bodyJson(Json.parse(Fixtures.subactivityJson));
			
			Result updateresult = route(updateRequest);
	     assertThat(updateresult.status(), equalTo(303));
	 }
	
	@Test
	 public void DELETEOndashboardUpdateActivity()
	 {
		//invoke createActivity to add an activity to default user
	    assertThat(addActivityToUser().status(), equalTo(OK));
	    assertThat(Activity.findAll().size(), equalTo(1));
	    
		RequestBuilder deleteRequest = new RequestBuilder()
				 .session("email","homer@simpson.com")
	             .method(DELETE)
	             .uri("/dashboard/delete/1");
			
			Result deleteresult = route(deleteRequest);
	     assertThat(deleteresult.status(), equalTo(303));
	     
	      assertThat(Activity.findAll().size(), equalTo(0));
	 }
		
	// Adding Auto add activity
		private Result addActivityToUser()
		  {
		      RequestBuilder activityRequest = new RequestBuilder()
		              .method(POST)
		              .uri("/api/users/1/activities")
		              .bodyJson(Json.parse(Fixtures.activityJson));
		      return route(activityRequest);        
		  }

}
