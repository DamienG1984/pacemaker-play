package controllers;

import models.*;
import play.data.Form;
import play.data.FormFactory; 
import play.mvc.*;
import views.html.*;
import play.*;
import java.util.ArrayList;
import java.util.List;


public class FriendsDashboard extends Controller 
{
	
	public List<User> friends = new ArrayList<User>();
	
	public Result viewFriends()
	  {
	    String email = session().get("email");
	    User user = User.findByEmail(email);
	    return ok(dashboard_viewFriends.render(user.friends));
	  }
	
	public Result findFriends()
	{
		List<User> friend = User.findAll();
	    return ok(dashboard_findFriends.render(friend));
	}
	
	public Result addFriend (Long Id)
	{
		String email = session().get("email");
	    User muser = User.findByEmail(email);
	    
		User user = User.findById(Id);
		Friends friend = new Friends();
		friend.firstname 	= user.firstname;
		friend.lastname		= user.lastname;
		friend.email		= user.email;
		
		muser.friends.add(friend);
	    muser.save();
		
	    return ok(dashboard_viewFriends.render(muser.friends));
	}
	
	  public Result deleteFriend(Long aid)
	  {
		    
		    //get Session User details in order to delete friend
		    String email = session().get("email");
		    User user = User.findByEmail(email);
		    
		    //get Session Users friend using Id
		    Friends friend = Friends.findById(aid); 
		    
		    friend.delete();
		    return ok(dashboard_viewFriends.render(user.friends));
	  }
	  
	  public Result viewFriendActivity(String email)
	  {
		    
		    //Find friend activities by
		    User user = User.findByEmail(email);
		    return ok(dashboard_viewFriendAct.render(user.activities));
	  }
}
