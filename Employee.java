import java.util.*;
/**
 * The Employee class is used to represent an employee. It keeps track of a 
 * username and a wish list of destinations.
 * 
 * @author Xuetong Du
 */


public class Employee {
	
	private String username;
	private List<String> wishlist;
	
	/**
     * Constructs an employee with the given username and an empty wish list.
     * 
     * @param name the username of this employee
     */
	public Employee(String name) {
		
		username = name;
		wishlist = new ArrayList<String>();
		
	}
	
	
	 /**
     * Return the username of this employee.
     * 
     * @return the username of the employee
     */
	public String getUsername() {
		return username;
		
	}
	
	
	/**
	 * Method to return wish list of this employee
	 * @param return the wish list of destination
	 */
	public List<String> getWishList(){
		
		return wishlist;
		
	}


	public boolean getUsername(String e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
