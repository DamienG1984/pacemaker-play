package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.List;

import com.google.common.base.Objects;

import passwordHash.BCrypt;

import javax.persistence.*;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;

@Entity
@Table(name="my_friends")
public class Friends extends Model{
	
	@Id
	@GeneratedValue
	public Long   id;
	
	public String firstname;
	public String lastname;
	public String email;
	public static Find<String, Friends> find = new Find<String, Friends>(){};
	
	public Friends()
	{
	}
	
	public Friends(String firstname, String lastname, String email)
	  {
	    this.firstname = firstname;
	    this.lastname  = lastname;
	    this.email     = email;
	  }
	
	@Override
	  public String toString()
	  {
	    return toStringHelper(this).addValue(id)
	            .add("Firstname", firstname)
	            .add("Lastname", lastname)
	            .add("Email", email)
	            .toString();
	  }
		  
	  @Override
	  public boolean equals(final Object obj)
	  {
	    if (obj instanceof Friends)
	    {
	      final Friends other = (Friends) obj;
	      return Objects.equal(firstname,      other.firstname) 
	          && Objects.equal(lastname,  other.lastname)
	          && Objects.equal(email,  other.email) ; 
	    }
	    else
	    {
	      return false;
	    }
	  }
		  
	  @Override  
	  public int hashCode()  
	  {  
	     return Objects.hashCode(this.id, this.firstname, this.lastname, this.email);  
	  } 
		
	  public static Friends findById(Long id)
	  {
	    return find.where().eq("id", id).findUnique();
	  }
	  
	  public static List<Friends> findAll()
	  {
	    return find.all();
	  }

	  public static void deleteAll()
	  {
	    for (Friends friend: Friends.findAll())
	    {
	      friend.delete();
	    }
	  }

}
