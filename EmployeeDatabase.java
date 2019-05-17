import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

///////////////////////////////////////////////////////////////////////////////
//
//Title:            EmployeeDatabase
//Files:            Employee.java
//					EmployeeDatabase.java
//					GUItester.java
//					Helper.java
//					InteractiveDBTester.java
//					TextBasedTest.java
//					Texttester.java
//					testFile.txt
//Semester:         CS367 Spring 2016
//Author:           Xuetong Du
//Email:            Xxdu49@wisc.edu
//CS Login:         xuetong
//Lecturer's Name:  Charles Fischer
//
///////////////////////////////////////////////////////////////////////////////

/**
 * Description: The EmployeeDatabase class stores all the employees as well as
 * all the methods
 *
 * <p>
 * Bugs: none
 *
 * @author Xuetong Du
 */

public class EmployeeDatabase implements Iterable<Employee> {

	private ArrayList<Employee> employeeList;

	/**
	 * Constructor passing the ArrayList
	 */
	public EmployeeDatabase() {
		employeeList = new ArrayList<Employee>();
	}

	/**
	 * Add the employee if it is not in the database
	 * 
	 * @param e
	 *            a string that holds all the employees
	 */
	public void addEmployee(String e) {

		// add employee to the end of the list
		if (e == null) {
			throw new IllegalArgumentException();
		}
		e = e.toLowerCase();
		if (containsEmployee(e)) {
			return;
		} else {
			employeeList.add(new Employee(e));
		}
	}

	/**
	 * Add the destination to the database if it doesn't exist
	 * 
	 * @param e
	 *            a string holds all the employees
	 * @param d
	 *            a string holds all the destinations
	 * @exception IllegalArgumentException
	 */
	public void addDestination(String e, String d) throws IllegalArgumentException {
		// add destination to the end of the list
		if (e == null || d == null) {
			throw new IllegalArgumentException();
		}
		e = e.toLowerCase();
		d = d.toLowerCase();
		if (containsEmployee(e)) {
			Iterator<Employee> employeeIter = iterator();
			while (employeeIter.hasNext()) {
				Employee emp = employeeIter.next();
				if (emp.getUsername().equals(e)) {
					if (hasDestination(e, d)) {
						// Employee has destination in wishlist
						return;
					} else {
						// Employee does not have destination in wishlist. Add
						emp.getWishList().add(d);
					}
				}
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Check if the employee is in the database
	 * 
	 * @return boolean
	 * @param e
	 *            A string holds all employees
	 */
	public boolean containsEmployee(String e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		e = e.toLowerCase();

		Iterator<Employee> employeeIter = iterator();
		while (employeeIter.hasNext()) {
			Employee emp = employeeIter.next();
			if (emp.getUsername().equals(e)) {
				// Employee is in database
				return true;
			}
		}
		// Employee not in database
		return false;
	}

	/**
	 * Check if the destination is in the database
	 * 
	 * @param d
	 *            A string holds all the destinations
	 * @return boolean
	 */
	public boolean containsDestination(String d) {
		if (d == null) {
			throw new IllegalArgumentException();
		}

		d = d.toLowerCase();
		Iterator<Employee> empIterator = iterator();
		while (empIterator.hasNext()) {
			Employee emp = empIterator.next();
			if (emp.getWishList().contains(d)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the employee has the destination
	 * 
	 * @param e
	 *            A string holds all the employees
	 * @param d
	 *            A string holds all the destinations
	 * @return boolean
	 */
	public boolean hasDestination(String e, String d) {
		if (e == null || d == null) {
			throw new IllegalArgumentException();
		}
		e = e.toLowerCase();
		d = d.toLowerCase();
		Iterator<Employee> employeeIter = iterator();
		while (employeeIter.hasNext()) {
			Employee emp = employeeIter.next();
			if (emp.getUsername().equals(e)) {
				if (emp.getWishList().contains(d)) {
					/* Employee is in database and has d as the destination */
					return true;
				} else {
					/* Employee is in database and does not have d as the destination */
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Return the list of employees who have destination d in their wish list.
	 * 
	 * @return employeeList
	 * @param d
	 *            A string holds all destinations
	 */
	public List<String> getEmployees(String d) {
		if (d == null) {
			throw new IllegalArgumentException();
		}
		if (!containsDestination(d)) {
			return null;
		}

		d = d.toLowerCase();
		List<String> employeeList = new ArrayList<String>();
		Iterator<Employee> empIterator = iterator();
		while (empIterator.hasNext()) {
			Employee e = empIterator.next();
			if (e.getWishList().contains(d)) {
				// Add employee only when the destination matches
				employeeList.add(e.getUsername());
			}
		}
		return employeeList;

	}

	/**
	 * Return the wish list for the employee e.
	 * 
	 * @return wishList
	 * @param e
	 *            A string holds all employee
	 */
	public List<String> getDestinations(String e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		if (!containsEmployee(e)) {
			return null;
		}
		e = e.toLowerCase();
		Iterator<Employee> empIterator = iterator();
		while (empIterator.hasNext()) {
			Employee emp = empIterator.next();
			if (emp.getUsername().equals(e)) {
				return emp.getWishList();
			}
		}
		return null;
	}

	/**
	 * Return an Iterator over the Employee objects in the database
	 * 
	 * @return iterator
	 */
	public Iterator<Employee> iterator() {
		return employeeList.iterator();
	}

	/**
	 * Remove employee e from the database
	 * 
	 * @return boolean
	 * @param e
	 *            a string holds all the employees
	 */
	public boolean removeEmployee(String e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}
		if (!containsEmployee(e)) {
			return false;
		}
		e = e.toLowerCase();

		for (int i = 0; i < employeeList.size(); i++) {
			if (employeeList.get(i).getUsername().equals(e)) {
				employeeList.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove destination d from the database
	 * 
	 * @return boolean
	 * @param d
	 *            a string holds all the destinations
	 */
	public boolean removeDestination(String d) {
		if (d == null) {
			throw new IllegalArgumentException();
		}
		d = d.toLowerCase();
		Iterator<Employee> empIterator = iterator();
		if (containsDestination(d)) {
			while (empIterator.hasNext()) {
				Employee emp = empIterator.next();
				if (emp.getWishList().contains(d)) {
					emp.getWishList().remove(d);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Return the number of employees in this database.
	 * 
	 * @return the size of employeeList
	 */
	public int size() {
		return employeeList.size();
	}
}
