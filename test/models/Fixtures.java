package models;

public class Fixtures {

	  public static String userJson = "{\n"
              +  "\"email\"    : \"jim@simpson.com\" ,\n"
              +  "\"firstName\": \"Jim\"             ,\n"
              +  "\"lastName\" : \"Simpson\"         ,\n"
              +  "\"password\" : \"secret\"           \n"
         + "}";

      public static User users[] = { 
          new User ("homer",  "simpson", "homer@simpson.com",  "secret"),
          new User ("lisa",   "simpson", "lisa@simpson.com",   "secret"),
          new User ("maggie", "simpson", "maggie@simpson.com", "secret"),
          new User ("bart",   "simpson", "bart@simpson.com",   "secret"),
          new User ("marge",  "simpson", "marge@simpson.com",  "secret"),
        };
      
      public static Friends friends[] = { 
              new Friends ("homer",  "simpson", "homer@simpson.com"),
              new Friends ("lisa",   "simpson", "lisa@simpson.com"),
              new Friends ("maggie", "simpson", "maggie@simpson.com"),
              new Friends ("bart",   "simpson", "bart@simpson.com"),
              new Friends ("marge",  "simpson", "marge@simpson.com"),
            };

      public static String activityJson  = "{\n"
              +  "\"atype\"      : \"run\"                 ,\n"
              +  "\"location\"  : \"Dunmore\"             ,\n"
              +  "\"distance\"  : 3                        \n"
         + "}";
      
      public static String updateactivityJson  = "{\n"
              +  "\"atype\"      : \"Jog\"                 ,\n"
              +  "\"location\"  : \"New Ross\"             ,\n"
              +  "\"distance\"  : 7                       \n"
         + "}";
      
      public static String friendJson = "{\n"
              +  "\"firstName\"    : \"Jim\" ,\n"
              +  "\"lastName\": \"Simpson\"             ,\n"
              +  "\"email\" : \"jim@simpson.com\"         \n"
         + "}";
      
      public static String loginJson = "{\n"
              +  "\"email\" : \"bart@simpson.com\"         ,\n"
              +   "\"password\": \"secret\"             \n"
         + "}";
      
      public static String subuserJson = "{\n"
              +  "\"email\"    : \"homer@simpson.com\" ,\n"
              +  "\"firstName\": \"Jim\"             ,\n"
              +  "\"lastName\" : \"Simpson\"         ,\n"
              +  "\"password\" : \"secret\"           \n"
         + "}";
      
      public static String subactivityJson  = "{\n"
              +  "\"Id\"      : 1                 ,\n"
              +  "\"atype\"      : \"Walk\"                 ,\n"
              +  "\"location\"  : \"Dunmore East\"             ,\n"
              +  "\"distance\"  : 3.0                        \n"
         + "}";
      
      public static String updateuserJson = "{\n"
              +  "\"email\"    : \"homer@simpson.com\" ,\n"
              +  "\"firstname\": \"Jim\"             ,\n"
              +  "\"lastname\" : \"Simpson\"         ,\n"
              +  "\"password\" : \"secret\"           \n"
         + "}";

}
