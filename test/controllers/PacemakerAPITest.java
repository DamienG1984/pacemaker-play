package controllers;

import static org.junit.Assert.*;

import org.junit.Test;

import models.Activity;
import models.Fixtures;
import models.User;
import models.Friends;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;

import jdk.nashorn.internal.runtime.options.LoggingOption.LoggerInfo;
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

public class PacemakerAPITest extends WithApplication{

	@Test
    //This route test should call the createUser() method in PacemakerAPI
    public void POSTOnApiUsersRouteCreatesUser() {
		
      //ensure the database has initial data before exercising tests
      assertThat(User.findAll().size(), equalTo(4));	      
      
      //Invoke the createUser() method and ensure the HTTP status returned was OK
      assertThat(addUser().status(), equalTo(OK));
      
      //ensure the database has five users after exercising tests
      assertThat(User.findAll().size(), equalTo(5));
	}
	
	@Test
    //This route test should call the createActivity() method in PacemakerAPI
	//and add the activity for a specific user  
    public void POSTOnApiUsersActivityRouteCreatesActivity() {
		
	  //ensure the User table has preloaded data and activity table is empty
      assertThat(User.findAll().size(), equalTo(4));	      
      assertThat(Activity.findAll().size(), equalTo(0));
      
	  //First, invoke createUser() to create a user whom we will add activities to
      assertThat(addUser().status(), equalTo(OK));
      assertThat(User.findAll().size(), equalTo(5));
      
      //Now that the user is added successfully, we can exercise the activity test
      assertThat(addActivityToUser().status(), equalTo(OK));
      assertThat(Activity.findAll().size(), equalTo(1));
    }
	
	@Test
    //This route test should call the createActivity() method in PacemakerAPI
	//and add the activity for a specific user  
    public void POSTOnApiUsersFriendRouteCreatesFriend() {
		
      //ensure the User table has preloaded data and Friend table is empty
      assertThat(User.findAll().size(), equalTo(4));	      
      assertThat(Friends.findAll().size(), equalTo(0));
      
	  //First, invoke createUser() to create a user whom we will add friends to
      assertThat(addUser().status(), equalTo(OK));
      assertThat(User.findAll().size(), equalTo(5));
      
      //Now that the user is added successfully, we can exercise the friend test
      assertThat(addFriendToUser().status(), equalTo(OK));
      assertThat(Friends.findAll().size(), equalTo(1));
    }
    
	@Test
    public void DELETEOnApiUsersIdRouteShouldDeleteAllUserData() {
      //ensure the User and Activity tables are empty before exercising tests
      assertThat(User.findAll().size(), equalTo(4));	      
      assertThat(Activity.findAll().size(), equalTo(0));
      assertThat(Friends.findAll().size(), equalTo(0));
	      
	  //First, invoke createUser() to create a user whom we will add activities to
      assertThat(addUser().status(), equalTo(OK));
      assertThat(User.findAll().size(), equalTo(5));
      
      //Second, invoke createActivity to add an activity to the user above
      assertThat(addActivityToUser().status(), equalTo(OK));
      assertThat(Activity.findAll().size(), equalTo(1));
      
    //Second, invoke createFriend to add an activity to the user above
      assertThat(addFriendToUser().status(), equalTo(OK));
      assertThat(Friends.findAll().size(), equalTo(1));
		
	  //Now invoke deleteUser(Long id) and test both the user and the activity are deleted
      RequestBuilder deleteRequest = new RequestBuilder()
              .method(DELETE)
              .uri("/api/users/1");
      Result deleteResult = route(deleteRequest);
      assertThat(deleteResult.status(), equalTo(OK));
   
      assertThat(User.findAll().size(), equalTo(4));
      assertThat(Activity.findAll().size(), equalTo(0));
      assertThat(Friends.findAll().size(), equalTo(0));
    }
	
	@Test
	public void PUTonUpdateUser()
	{
		//ensure default users have been loaded
	    assertThat(User.findAll().size(), equalTo(4));
	    
	    // Retrieve a User to update
	    User user = User.findById(1l);

	    //Test that the fields in the returned activity were created correctly
	    assertNotNull(user);
		assertThat("homer", equalTo(user.firstname));
		assertThat("simpson", equalTo(user.lastname));
		assertThat("homer@simpson.com", equalTo(user.email));
		assertThat("secret", equalTo(user.password));
	    
	    //Now invoke update user(Long id) and test both the user
	      RequestBuilder updateRequest = new RequestBuilder()
	              .method(PUT)
	              .uri("/api/users/1")
	              .bodyJson(Json.parse(Fixtures.updateuserJson));
	      Result updateResult = route(updateRequest);
	      assertThat(updateResult.status(), equalTo(OK));
	      
	    // Retrieve the user we just updated; id should be 1
	    User nwuser = User.findById(1l);
	    
	    //System.out.println(nwuser.toString());
	    //Test that the fields in the returned user were updated correctly
	    assertNotNull(nwuser);
		assertThat("Jim", equalTo(nwuser.firstname));
		assertThat("Simpson", equalTo(nwuser.lastname));
		assertThat("homer@simpson.com", equalTo(nwuser.email));
		assertThat("secret", equalTo(nwuser.password));
	}
    
	@Test
    public void DELETEOnApiUsersRouteShouldExist() {
        RequestBuilder request = new RequestBuilder()
                .method(DELETE)
                .uri("/api/users");

        Result result = route(request);
        assertThat(result.status(), equalTo(OK));
    }

	@Test
    public void GETOnApiUsersRouteShouldExist() {
        Result result = route(fakeRequest(GET, "/api/users"));
        assertThat(result.status(), equalTo(OK));
    }
	
	@Test
    public void GETOnApiUsersIDRouteShouldExist() {
        RequestBuilder request = new RequestBuilder()
                .method(GET)
                .uri("/api/users/1");

        Result result = route(request);
        assertThat(result.status(), equalTo(OK));
    }
    
    @Test
    public void GETOnDummyRouteShouldNotBeFound() {
        Result result = route(fakeRequest(GET, "/api/blah"));
        assertThat(result.status(), equalTo(NOT_FOUND));
    }
    
    ///ACTIVITY TESTS
    
    @Test
    public void GetOnActivityId()
    {
    	//Invoke add activity and test if correct
        assertThat(addActivityToUser().status(), equalTo(OK));
        assertThat(Activity.findAll().size(), equalTo(1));
        
        RequestBuilder activityrequest = new RequestBuilder()
                .method(GET)
                .uri("/api/users/1/activities/1");

        Result activityresult = route(activityrequest);
        assertThat(activityresult.status(), equalTo(OK));
    }
    
    @Test
	public void DeleteActivity()
	{
		//invoke createActivity to add an activity to the user above
	    assertThat(addActivityToUser().status(), equalTo(OK));
	    assertThat(Activity.findAll().size(), equalTo(1));
	    
	  //Now invoke deleteActivity(Long id) and test both the user and the activity are deleted
	      RequestBuilder deleteRequest = new RequestBuilder()
	              .method(DELETE)
	              .uri("/api/users/1/activities/1");
	      Result deleteResult = route(deleteRequest);
	      assertThat(deleteResult.status(), equalTo(OK));

	      assertThat(Activity.findAll().size(), equalTo(0));
	}
    
    @Test
	public void UpdateActivity()
	{
		//invoke createActivity to add an activity to the user above
	    assertThat(addActivityToUser().status(), equalTo(OK));
	    assertThat(Activity.findAll().size(), equalTo(1));
	    
	    // Retrieve the activity we just added; id should be 1
	    Activity activity = Activity.findById(1l);

	    //Test that the fields in the returned activity were created correctly
	    assertNotNull(activity);
		assertThat("run", equalTo(activity.atype));
		assertThat("Dunmore", equalTo(activity.location));
		assertThat(3.0, equalTo(activity.distance));
	    
	    //Now invoke deleteActivity(Long id) and test both the user and the activity are deleted
	      RequestBuilder updateRequest = new RequestBuilder()
	              .method(PUT)
	              .uri("/api/users/1/activities/1")
	              .bodyJson(Json.parse(Fixtures.updateactivityJson));
	      Result updateResult = route(updateRequest);
	      
	    // Retrieve the activity we just added; id should be 1
	    Activity uactivity = Activity.findById(1l);
	      
	    //Test that the fields in the returned activity were updated correctly
		assertNotNull(uactivity);
		assertThat("Jog", equalTo(uactivity.atype));
		assertThat("New Ross", equalTo(uactivity.location));
		assertThat(7.0, equalTo(uactivity.distance));
	}
    
   //// FRIENDS TESTS
    
    @Test
	public void viewFriends()
	{
		//invoke createActivity to add an activity to the user above
	    assertThat(addFriendToUser().status(), equalTo(OK));
	    assertThat(Friends.findAll().size(), equalTo(1));
	    
	  //Now invoke deleteActivity(Long id) and test both the user and the activity are deleted
	      RequestBuilder viewRequest = new RequestBuilder()
	              .method(GET)
	              .uri("/api/users/1/viewFriends");
	      Result viewResult = route(viewRequest);
	      assertThat(viewResult.status(), equalTo(OK));

	      assertThat(Friends.findAll().size(), equalTo(1));
	}
    
    @Test
	public void DeleteFriends()
	{
		//invoke createActivity to add an activity to the user above
	    assertThat(addFriendToUser().status(), equalTo(OK));
	    assertThat(Friends.findAll().size(), equalTo(1));
	    
	  //Now invoke deleteActivity(Long id) and test both the user and the activity are deleted
	      RequestBuilder deleteRequest = new RequestBuilder()
	              .method(DELETE)
	              .uri("/api/users/1/friend/1");
	      Result deleteResult = route(deleteRequest);
	      assertThat(deleteResult.status(), equalTo(OK));

	      assertThat(Friends.findAll().size(), equalTo(0));
	}
    
    
    private Result addUser()
    {
        RequestBuilder userRequest = new RequestBuilder()
                .method(POST)
                .uri("/api/users")
                .bodyJson(Json.parse(Fixtures.userJson));
       return route(userRequest);
    }

    private Result addActivityToUser()
    {
        RequestBuilder activityRequest = new RequestBuilder()
                .method(POST)
                .uri("/api/users/1/activities")
                .bodyJson(Json.parse(Fixtures.activityJson));
        return route(activityRequest);    	
    }
    
    private Result addFriendToUser()
    {
		RequestBuilder friendResult = new RequestBuilder()
	              .method(POST)
	              .uri("/api/users/1/addFriend")
	              .bodyJson(Json.parse(Fixtures.friendJson));
	      return route(friendResult);
    }
    
}


