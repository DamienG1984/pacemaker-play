package models;

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

public class FriendsTest extends WithApplication{
//automatically ensures that a fake application is started
//and stopped for each test method.
		
	@Test
	public void createAndRetrieveFriendsForUser() {
		
		// Create a new user and save it in the database
	    new User("Jim", "Simpson", "jim@simpson.com", "secret").save();
	    User joesoap = User.findByEmail("jim@simpson.com");
	    joesoap.friends.add(new Friends("Bob", "Murphy", "bob@murphy.com"));
		joesoap.save();    
		
	    // Retrieve the friend we just added; id should be 1
	    Friends friend = Friends.findById(1l);
	      
	    //Test that the fields in the returned friend was set up correctly
	    assertNotNull(friend);
		assertThat("Bob", equalTo(friend.firstname));
		assertThat("Murphy", equalTo(friend.lastname));
		assertThat("bob@murphy.com", equalTo(friend.email));
	}
	
	@Test
	public void DeleteFriends()
	{
		// Create a new user and save it in the database
	    new User("Jim", "Simpson", "jim@simpson.com", "secret").save();
	    User joesoap = User.findByEmail("jim@simpson.com");
	    joesoap.friends.add(new Friends("Bob", "Murphy", "bob@murphy.com"));
		joesoap.save();    
		
		//assert that Friend have been added.
	    assertThat(Friends.findAll().size(), equalTo(1));
	    
	    // Delete Created Friend
	    Friends.deleteAll();
	    
	    //assert that Friends have been removed.
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
	
	private Result addFriendToUser()
    {
		RequestBuilder friendResult = new RequestBuilder()
	              .method(POST)
	              .uri("/api/users/1/addFriend")
	              .bodyJson(Json.parse(Fixtures.friendJson));
	      return route(friendResult);
    }

}