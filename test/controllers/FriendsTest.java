package controllers;

import static org.junit.Assert.*;

import org.junit.Test;

import models.Activity;
import models.Fixtures;
import models.User;
import models.Friends;
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


public class FriendsTest extends WithApplication
{
	@Test
	 public void GetOnFriendsDashboard()
	 {
		RequestBuilder dashboardRequest = new RequestBuilder()
				 .session("email","homer@simpson.com")
	             .method(GET)
	             .uri("/dashboard/friends");
			
			Result dashboardresult = route(dashboardRequest);
	     assertThat(dashboardresult.status(), equalTo(OK));
	 }

	@Test
	 public void GetOnFindFriends()
	 {
		RequestBuilder findfriendsRequest = new RequestBuilder()
				 .session("email","homer@simpson.com")
	             .method(GET)
	             .uri("/dashboard/findFriends");
			
			Result findfriendsresult = route(findfriendsRequest);
	     assertThat(findfriendsresult.status(), equalTo(OK));
	 }
	
	@Test
	 public void PostOnaddFriends()
	 {
		RequestBuilder addfriendsRequest = new RequestBuilder()
				 .session("email","homer@simpson.com")
	             .method(POST)
	             .uri("/dashboard/addFriend/2");
			
			Result addfriendsresult = route(addfriendsRequest);
	     assertThat(addfriendsresult.status(), equalTo(OK));
	 }
	
	@Test
	 public void DeleteOnFriends()
	 {
		
		assertThat(addFriendToUser().status(), equalTo(OK));
	    assertThat(Friends.findAll().size(), equalTo(1));
	    
	    
		RequestBuilder deletefriendsRequest = new RequestBuilder()
				 .session("email","homer@simpson.com")
	             .method(DELETE)
	             .uri("/dashboard/delete/friend/1");
			
		 Result deletefriendsresult = route(deletefriendsRequest);
	     assertThat(deletefriendsresult.status(), equalTo(OK));
		  
	     //Check friend was deleted
		 assertThat(Friends.findAll().size(), equalTo(0));
	 }
	
	@Test
	 public void viewFriendActivities()
	 {
		//Add User
	    assertThat(addUser().status(), equalTo(OK));
	    assertThat(User.findAll().size(), equalTo(5));
	    
		//Add Activity to Friend First
	    assertThat(addActivityToUser().status(), equalTo(OK));
	    assertThat(Activity.findAll().size(), equalTo(1));
	    
	    //Add Friend
		assertThat(addFriendToUser().status(), equalTo(OK));
	    assertThat(Friends.findAll().size(), equalTo(1));
	    
		RequestBuilder viewfriendsRequest = new RequestBuilder()
				 //.session("email","homer@simpson.com")
	             .method(GET)
	             .uri("/dashboard/firends/activies/jim@simpson.com");
			
		 Result viewfriendsresult = route(viewfriendsRequest);
	     assertThat(viewfriendsresult.status(), equalTo(OK));
	 }
	
	//Add Friend
	private Result addFriendToUser()
	  {
	      RequestBuilder friendsRequest = new RequestBuilder()
	              .method(POST)
	              .uri("/api/users/1/addFriend")
	              .bodyJson(Json.parse(Fixtures.friendJson));
	      return route(friendsRequest);        
	  }
	
	// Adding Auto add user
		private Result addUser()
	    {
	        RequestBuilder userRequest = new RequestBuilder()
	                .method(POST)
	                .uri("/api/users")
	                .bodyJson(Json.parse(Fixtures.userJson));
	       return route(userRequest);
	    }

	// Adding Auto add activity
	private Result addActivityToUser()
	  {
	      RequestBuilder activityRequest = new RequestBuilder()
	              .method(POST)
	              .uri("/api/users/5/activities")
	              .bodyJson(Json.parse(Fixtures.activityJson));
	      return route(activityRequest);        
	  }
	
}
