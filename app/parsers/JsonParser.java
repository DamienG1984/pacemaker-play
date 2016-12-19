package parsers;
 
import models.Activity;
import models.User;
import models.Friends;

import java.util.ArrayList;
import java.util.List;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class JsonParser
{
  private static JSONSerializer userSerializer     = new JSONSerializer();
  private static JSONSerializer activitySerializer = new JSONSerializer();
  private static JSONSerializer friendSerializer = new JSONSerializer();  

  public static User renderUser(String json)
  {
    return new JSONDeserializer<User>().deserialize(json, User.class); 
  }
  
  public static String renderUser(Object obj)
  {
    return userSerializer.serialize(obj);
  }
  
  public static List<User> renderUsers(String json)
  {
    return new JSONDeserializer<ArrayList<User>>().use("values", User.class).deserialize(json);
  }   
  
  // Activities
  public static Activity renderActivity(String json)
  {
    return new JSONDeserializer<Activity>().deserialize(json, Activity.class);
  }
  
  public static String renderActivity(Object obj)
  {
    return activitySerializer.serialize(obj);
  }

  public static  List<Activity> renderActivities (String json)
  {
    return new JSONDeserializer<ArrayList<Activity>>().use("values", Activity.class).deserialize(json);
  } 
  
  
 // Friends
  public static Friends renderFriends(String json)
  {
    return new JSONDeserializer<Friends>().deserialize(json, Friends.class);
  }
  
  public static String renderFriends(Object obj)
  {
    return friendSerializer.serialize(obj);
  }

  public static  List<Friends> renderFriend (String json)
  {
    return new JSONDeserializer<ArrayList<Friends>>().use("values", Friends.class).deserialize(json);
  } 

  
}
