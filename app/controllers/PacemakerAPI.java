package controllers;

import static parsers.JsonParser.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class PacemakerAPI extends Controller
{  
  //user methods
	  
  public Result users()
  {
    List<User> users = User.findAll();
    return ok(renderUser(users));
  }

  public Result user(Long id)
  {
    User user = User.findById(id);  
    return user==null? notFound() : ok(renderUser(user)); 
  }
  
  public Result createUser()
  {
    User user = renderUser(request().body().asJson().toString());
    user.save();
    return ok(renderUser(user));
  }
  
  public Result deleteUser(Long id)
  {
    Result result = notFound();
    User user = User.findById(id);
    if (user != null)
    {
      user.delete();
      result = ok();
    }
    return result;
  }
  
  public Result deleteAllUsers()
  {
    User.deleteAll();
    return ok();
  }
  
  public Result updateUser(Long id)
  {
    Result result = notFound();
    User user = User.findById(id);
    if (user != null)
    {
      User updatedUser = renderUser(request().body().asJson().toString());
      user.update(updatedUser);
      user.save();
      result = ok(renderUser(user));
    }
    return result;
  }
 
  //activity methods
  
  public Result activities (Long userId)
  {  
    User user = User.findById(userId);
    return ok(renderActivity(user.activities));
  }
   
  public Result createActivity (Long userId)
  { 
    User     user     = User.findById(userId);
    Activity activity = renderActivity(request().body().asJson().toString());  
    
    user.activities.add(activity);
    user.save();
    
    return ok(renderActivity(activity));
  }
  
  public Result activity (Long userId, Long activityId)
  {  
    User     user     = User.findById(userId);
    Activity activity = Activity.findById(activityId);
    
    if (activity == null)
    {
      return notFound();
    }
    else
    {
      return user.activities.contains(activity)? ok(renderActivity(activity)): badRequest();
    }
  }  
  
  public Result deleteActivity (Long userId, Long activityId)
  {  
    User     user     = User.findById(userId);
    Activity activity = Activity.findById(activityId);
 
    if (activity == null)
    {
      return notFound();
    }
    else
    {
      if (user.activities.contains(activity))
      {
        user.activities.remove(activity);
        activity.delete();
        user.save();
        return ok();
      }
      else
      {
        return badRequest();
      }

    }
  }  
  
  public Result updateActivity (Long userId, Long activityId)
  {
    User     user     = User.findById(userId);
    Activity activity = Activity.findById(activityId);
    
    if (activity == null)
    {
      return notFound();
    }
    else
    {
      if (user.activities.contains(activity))
      {
    	  Activity updatedActivity = renderActivity(request().body().asJson().toString());
          //activity.distance = updatedActivity.distance;
          //activity.location = updatedActivity.location;
          //activity.atype     = updatedActivity.atype;
          
         // result = ok(renderUser(user));
          
          activity.update(updatedActivity);
          activity.save();
          //user.activities.update(activity);
          //user.save();
          return ok(renderActivity(updatedActivity));
      }
      else
      {
        return badRequest();
      }
    }
  }   
  
// friend methods
  
  public Result friends (Long userId)
  {  
    User user = User.findById(userId);
    return ok(renderFriends(user.friends));
  }
   
  public Result createFriend (Long userId)
  { 
    User     user     = User.findById(userId);
    Friends friend 	  = renderFriends(request().body().asJson().toString());  
    
    user.friends.add(friend);
    user.save();
    
    return ok(renderFriends(friend));
  }
  
  public Result friend (Long userId, Long friendId)
  {  
    User     user     = User.findById(userId);
    Friends friend = Friends.findById(friendId);
    
    if (friend == null)
    {
      return notFound();
    }
    else
    {
      return user.friends.contains(friend)? ok(renderFriends(friend)): badRequest();
    }
  }  
  
  public Result deleteFriend (Long userId, Long friendId)
  {  
    User     user     = User.findById(userId);
    Friends  friend = Friends.findById(friendId);
 
    if (friend == null)
    {
      return notFound();
    }
    else
    {
      if (user.friends.contains(friend))
      {
        user.friends.remove(friend);
        friend.delete();
        user.save();
        return ok();
      }
      else
      {
        return badRequest();
      }

    }
  }  
  
}
