package models;

import static org.junit.Assert.*;
import org.junit.Test;
import play.test.WithApplication;
import static org.hamcrest.CoreMatchers.*;
import static play.test.Helpers.*;
import passwordHashTest.BCrypt;

public class UserTest extends WithApplication{
//automatically ensures that a fake application is started
//and stopped for each test method.
	
  @Test
  public void createAndRetrieveUserByEmail() {
	
	// Create a new user and save it
	new User("Joe", "Soap", "joesoap@gmail.com", "secret").save();
	
	//Retrieve the user with e-mail address joesoap@gmail.com
	User joesoap = User.findByEmail("joesoap@gmail.com");
	
	// Test 
	assertNotNull(joesoap);
	assertThat("Joe", equalTo(joesoap.firstname));
	assertThat("Soap", equalTo(joesoap.lastname));
	assertThat("joesoap@gmail.com", equalTo(joesoap.email));
	BCrypt.checkpw("secret", joesoap.password);
  }
  
  @Test
  public void updateAndRetrieveUserById() {
	
	// Create a new user and save it
	User joesoap = new User("Joe", "Soap", "joesoap@gmail.com", "secret");
	joesoap.save();
	
	//Create new user to perform update with
	User upuser = new User ("John", "Snow", "joesoap@gmail.com", "secret");
	
	//Call update
	joesoap.update(upuser);
	joesoap.save();
	
	//Retrieve the user with e-mail address joesoap@gmail.com
		User joe = User.findById(5l);
	
	// Test 
	assertNotNull(joe);
	assertThat("John", equalTo(joe.firstname));
	assertThat("Snow", equalTo(joe.lastname));
	assertThat("joesoap@gmail.com", equalTo(joe.email));
	BCrypt.checkpw("secret", joe.password);
  }
  
  @Test
  public void deleteAllUsersResultsInEmptyUserTable() {

    //Assert that the user table contains preloaded data
    assertThat(User.findAll().size(), equalTo(4));

    // Add all the users listed in the Fixtures class to the user table
    for (User user : Fixtures.users){
      new User(user.firstname, 
               user.lastname, 
               user.email, 
               user.password)
              .save();
    }
    //Ensure all were added successfully
    //Manually seteting user length due to preloaded data
    assertThat(User.findAll().size(), equalTo(9));

    //Delete all the users that were just added
    User.deleteAll();

    //Assert that the user table is once again empty
    assertThat(User.findAll().size(), equalTo(0));
  }
   
}



