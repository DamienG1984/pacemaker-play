package controllers;

import models.Activity;
import models.User;
import play.data.Form;
import play.data.FormFactory; 
import play.mvc.*;
import views.html.*;
import play.*;

public class Dashboard extends Controller
{
	
  public Result index()
  {
    String email = session().get("email");
    User user = User.findByEmail(email);
    return ok(dashboard_main.render(user.activities));
  }
  
  public Result uploadActivityForm()
  {
    return ok(dashboard_uploadactivity.render());
  }  

  public Result submitActivity()
  {
	Form<Activity> boundForm = Form.form(Activity.class).bindFromRequest();    
    Activity activity = boundForm.get();
    
    if(boundForm.hasErrors()) 
    {
      return badRequest();
    }

    String email = session().get("email");
    User user = User.findByEmail(email);

    user.activities.add(activity);
    user.save();
    return redirect (routes.Dashboard.index());
  }
  
  public Result editActivityDetails(Long aid)
  {
	  String email = session().get("email");
	    User user = User.findByEmail(email);
	    Activity activity = Activity.findById(aid);    
	    return ok(dashboard_editActivity.render(activity)); 
  }

 
  public Result updateActivityDetails()
  {
	  Form<Activity> boundForm = Form.form(Activity.class).bindFromRequest();    
	    Activity activity = boundForm.get();
	    
	    
	    //get Session User details in order to update activity
	    String email = session().get("email");
	    User user = User.findByEmail(email);
	    
	    //get Session User activity using Id from Form
	    Activity activity1 = Activity.findById(activity.id); 
	    
	  if(boundForm.hasErrors()) 
	    {
	      return badRequest();
	    }
	  else
	  {
	    activity1.update(activity);
	    activity1.save();
	    return  redirect (routes.Dashboard.index());
	    }
  }
  
  public Result deleteActivity(Long aid)
  {
	 // Form<Activity> boundForm = Form.form(Activity.class).bindFromRequest();    
	 //   Activity activity = boundForm.get();
	    
	    //get Session User details in order to delete activity
	    String email = session().get("email");
	    User user = User.findByEmail(email);
	    
	    //get Session User activity using Id from Form
	    Activity activity1 = Activity.findById(aid); 
	    
//	    if(boundForm.hasErrors()) 
//	    {
//	      return badRequest();
//	    }

	    activity1.delete();
	    return  redirect (routes.Dashboard.index());
  }
}
