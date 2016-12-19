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

public class ActivityTest extends WithApplication{
//automatically ensures that a fake application is started
//and stopped for each test method.
		
	@Test
	public void createAndRetrieveActivityForUser() {
		// Create a new user and save it in the database
	    new User("Jim", "Simpson", "jim@simpson.com", "secret").save();
	    User joesoap = User.findByEmail("jim@simpson.com");
	    joesoap.activities.add(new Activity("Run", "Tramore", 12.33));
		joesoap.save();
		
	    // Retrieve the activity we just added; id should be 1
	    Activity activity = Activity.findById(1l);
	      
	    //Test that the fields in the returned activity was set up correctly
	    assertNotNull(activity);
		assertThat("Run", equalTo(activity.atype));
		assertThat("Tramore", equalTo(activity.location));
		assertThat(12.33, equalTo(activity.distance));
	}
	
	@Test
	public void UpdateActivityforUser()
	{
		// Create a new user and save it in the database
	    new User("Jim", "Simpson", "jim@simpson.com", "secret").save();
	    User joesoap = User.findByEmail("jim@simpson.com");
	    
	    //Create Activity
	    joesoap.activities.add(new Activity("run", "Dunmore", 3.0));
		joesoap.save();
	    
	    // Retrieve the activity we just added; id should be 1
	    Activity activity = Activity.findById(1l);

	    //Test that the fields in the returned activity were created correctly
	    assertNotNull(activity);
		assertThat("run", equalTo(activity.atype));
		assertThat("Dunmore", equalTo(activity.location));
		assertThat(3.0, equalTo(activity.distance));
	      
	    // Retrieve the activity we just added; id should be 1
	    Activity uactivity = new Activity("Jog", "New Ross", 7.0);
	    
	    activity.update(uactivity);
	      
	    //Test that the fields in the returned activity were updated correctly
		assertNotNull(activity);
		assertThat("Jog", equalTo(activity.atype));
		assertThat("New Ross", equalTo(activity.location));
		assertThat(7.0, equalTo(activity.distance));
	}
	
	@Test
	public void DeleteActivity()
	{
		// Create a new user and save it in the database
	    new User("Jim", "Simpson", "jim@simpson.com", "secret").save();
	    User joesoap = User.findByEmail("jim@simpson.com");
	    joesoap.activities.add(new Activity("Cycle", "Tramore", 22.33));
		joesoap.save();
		
		//Check Activity was created
		assertThat(Activity.findAll().size(), equalTo(1));
		
		Activity.deleteAll();

		//Check Activity was removed
	    assertThat(Activity.findAll().size(), equalTo(0));
	}
	
}