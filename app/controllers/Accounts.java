package controllers;

import javax.inject.Inject;

import models.User;
import passwordHash.BCrypt;
import play.*;
import play.mvc.*;

import views.html.*;

import play.data.FormFactory; 
import play.data.Form;
import java.util.*;


public class Accounts extends Controller
{  
 
  private static Form<User> userForm;
  private static Form<User> loginForm;

  @Inject
  public Accounts(FormFactory formFactory) {
     this.userForm  = formFactory.form(User.class);
     this.loginForm = formFactory.form(User.class);
  }
 
  public Result index()
  {
    return ok(welcome_main.render());
  }

  public Result signup()
  {
    return ok(accounts_signup.render());
  }
  
  public Result login()
  {
    return ok(accounts_login.render());
  }
  
  public Result logout()
  {
    session().clear();
    return ok(welcome_main.render());
  }

  public Result register()
  {
	Form<User> boundForm = userForm.bindFromRequest(); 
	
	// Create usr string object to hold email address from form
	String usr = boundForm.get().email;
	//search to see email address entered already exists
	User fusr = User.findByEmail(usr);

	if (fusr != null)
		//if(loginForm.hasErrors()) 
    {
		Logger.info("User Already Exists");
      return badRequest(accounts_login.render());
    }
    else
    {
        User user = boundForm.get();
        //hash password
        user.password = BCrypt.hashpw(user.password, BCrypt.gensalt());
	    //Logger.info ("User = " + user.toString());
	    user.save();
	    return ok(welcome_main.render());
    }
  }

  public Result authenticate() 
  {
	Form<User> boundForm = loginForm.bindFromRequest();
	
	User user = loginForm.bindFromRequest().get();
	
	User luser = User.findByEmail(boundForm.get().email);
	
	Logger.info(user.password);
	
	//check if pre loaded user passwords match or hash passwords match
		if (luser.email.equals(boundForm.get().email) && luser.password.equals(boundForm.get().password) ||
			(luser.email.equals(boundForm.get().email) && BCrypt.checkpw(boundForm.get().password, luser.password)))
		{
			session("email", boundForm.get().email);
		    return redirect(routes.Dashboard.index());
		}
		else 
		{	 
			Logger.info("Invalid Login Deatils, Please try again");
		   return badRequest(accounts_login.render());
		}
  }
  
  public Result viewUserDetails()
  {
	  String email = session().get("email");
	    User user = User.findByEmail(email);
	    return ok(dashboard_viewUser.render(user));
  }
  
  public Result editUserDetails()
  {
	  String email = session().get("email");
	    User user = User.findByEmail(email);
	    return ok(dashboard_editUser.render(user));
  }
  
  public Result submitUserDetails()
  {
	  Form<User> boundForm = Form.form(User.class).bindFromRequest();    
	    User user = boundForm.get();
	    
	    if(boundForm.hasErrors()) 
	    {
	      return badRequest();
	    }
	    
	    String email = session().get("email");
	    User user1 = User.findByEmail(email);
	   
	    //check if password was updated, if not then save user
	    if (user1.password.equals(user.password))
	    {
	    	user1.update(user);
		    user1.save();
		    return ok (dashboard_viewUser.render(user1));
	    }
	    else
	    {
	    //if password was updated, hash new password
	    //user1.password = BCrypt.hashpw(user1.password, BCrypt.gensalt());
	    user1.update(user);
	    user1.save();
	    return ok (dashboard_viewUser.render(user1));
	    }

  }
  
}
