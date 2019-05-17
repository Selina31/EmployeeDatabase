import java.util.*;
import java.io.*;

///////////////////////////////////////////////////////////////////////////////
//
//Title:            InteractiveDBTester
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
 * Description: The InteractiveDBTester class creates and tests an 
 * EmployeeDatabase that represents information about employees and wish lists. 
 * Employee and wish list information is read from a text file using the 
 * method populateDB. The class also contains a variety of methods 
 * used to test the database.
 *
 * <p>Bugs: none
 *
 * @author Xuetong Du
 */

/**
 * Testing different methods
 *
 *
 * @author Xuetong Du
 * @version 1.0
 */

public class InteractiveDBTester {

	protected static EmployeeDatabase EmployeeDB = new EmployeeDatabase();

	/**
	 * Checks whether exactly one command-line argument is given as well as whether
	 * the input file exists and is readable. Loads the data from the input file and
	 * uses it to construct an employee database.
	 *
	 * @param args
	 */
	protected static void populateDB(String[] args) {

		// Step 1: check whether exactly one command-line argument is given
		Scanner scanner = new Scanner(System.in);
		if (args == null) {
			System.out.println("Please provide input file as " + "command-line argument");
			pushQuit();
		}
		System.out.println("Please enter a file name: ");
		String fileName = scanner.nextLine();
		File file = new File(fileName);

		// Checking if the file exists
		if (!file.exists()) {
			System.out.println("Error: Cannot access input file");
			System.out.close();
		}

		try {
			scan = new Scanner(file);
			while (scan.hasNext()) {
				String line = scan.nextLine();
				String[] values = line.split(",");
				String e = values[0].toLowerCase();
				EmployeeDB.addEmployee(e);
				for (int i = 1; i < values.length; i++) {
					String d = values[i].toLowerCase();
					EmployeeDB.addDestination(e, d);
				}
			}
		} catch (Exception ex) {
			System.out.println("Error: Cannot access input file");
			System.exit(1);
		}
	}

	static boolean GUIactive; // is GUI tester active?
	private static Scanner scan;

	/**
	 * A method to see if the employee exists
	 * 
	 * @param employee
	 * @return s
	 */
	protected static String pushFind(String employee) {

		// Code to implement find command
		String s = "";
		if (!EmployeeDB.containsEmployee(employee)) {
			return "Employee not found";
		} else {
			s = employee + ":" + Helper.printStringList(EmployeeDB.getDestinations(employee));
			return s;
		}
	}

	/**
	 * A method to see if the destination exists
	 * 
	 * @param destination
	 * @return A string of sentence
	 */
	protected static String pushDiscontinue(String destination) {

		// Code to implement discontinue command
		if (!EmployeeDB.containsDestination(destination)) {
			return "destination not found";
		} else {
			EmployeeDB.removeDestination(destination);
			return "destination discontinued.";
		}
	}

	/**
	 * A method to get the destinations
	 * 
	 * @param destination
	 * @return s
	 */
	protected static String pushSearch(String destination) {

		String s = "";

		// Code to implement search command
		if (!EmployeeDB.containsDestination(destination)) {
			return "destination not found";
		} else
			s = destination + ":" + Helper.printStringList(EmployeeDB.getEmployees(destination));
		return s;
	}

	/**
	 * A method to remove the employee
	 * 
	 * @param employee
	 * @return a string of sentence
	 */
	protected static String pushRemove(String employee) {

		// Code to implement remove command
		boolean result = EmployeeDB.removeEmployee(employee);
		if (!result) {
			return "Employee not found";
		} else {
			return "Employee removed";
		}
	}

	/**
	 * A method to output all the information
	 * 
	 * @return sb a StringBuffer contains all the information
	 */
	protected static String pushInformation() {

		// Code to implement information command

		// 1. Return employee and unique destinations
		StringBuffer sb = new StringBuffer();
		int totalEmployees = EmployeeDB.size();
		List<String> uniqueDestinations = new ArrayList<String>();
		List<Integer> empDestinationCounts = new ArrayList<Integer>();
		List<Integer> destinationEmpCounts = new ArrayList<Integer>();

		Iterator<Employee> empIterator = EmployeeDB.iterator();
		while (empIterator.hasNext()) {
			Employee e = empIterator.next();
			List<String> destinations = EmployeeDB.getDestinations(e.getUsername());
			if (destinations != null) {
				//
				empDestinationCounts.add(destinations.size());
				for (String destination : destinations) {
					if (!uniqueDestinations.contains(destination)) {
						uniqueDestinations.add(destination);
					}
				}
			}
		}
		int uniqueDestinationsCount = uniqueDestinations.size();
		sb.append("Employees: " + totalEmployees + ", Destinations: " + uniqueDestinationsCount + "\n");

		// #2.
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		int sum = 0;
		Float average = (float) 0.0;

		for (Integer c : empDestinationCounts) {
			sum = sum + c;
			if (c < min) {
				min = c;
			}
			if (c > max) {
				max = c;
			}
		}
		average = (float) ((float) sum / totalEmployees);
		average = (float) Math.round(average * 10) / 10;
		sb.append("# of destinations/Employee: most " + max + ", least " + min + ", average " + average + "\n");

		// #3.
		int mostDestCount = Integer.MIN_VALUE;
		int leastDestCount = Integer.MAX_VALUE;
		float avgDestCount = (float) 0.0;
		sum = 0;
		for (String dest : uniqueDestinations) {
			List<String> employees = EmployeeDB.getEmployees(dest);
			int count = 0;

			if (employees != null) {
				count = employees.size();
			} else {
				count = 0;
			}
			destinationEmpCounts.add(count);
			if (count > mostDestCount) {
				mostDestCount = count;
			}
			if (count < leastDestCount) {
				leastDestCount = count;
			}
			sum = sum + count;
		}
		avgDestCount = (float) ((float) sum / uniqueDestinationsCount);
		avgDestCount = (float) Math.round(avgDestCount * 10) / 10;
		sb.append("# of Employees/destination: most " + mostDestCount + ", least " + leastDestCount + ", average "
				+ avgDestCount + "\n");

		StringBuffer mostDestination = new StringBuffer();

		for (int i = 0; i < destinationEmpCounts.size(); i++) {
			if (destinationEmpCounts.get(i) == mostDestCount) {
				if (mostDestination.length() == 0) {
					mostDestination.append(uniqueDestinations.get(i));
				} else {
					mostDestination.append(",");
					mostDestination.append(uniqueDestinations.get(i));
				}

			}
		}
		sb.append("Most popular destination: " + mostDestination.toString() + " [" + mostDestCount + "]");
		return sb.toString();

	}

	/**
	 * A method to display all the employees and destinations
	 * 
	 * @return sb a StringBuffer contains all the information
	 */
	protected static String pushList() {

		// Code to implement list command
		String s = "";
		StringBuffer sb = new StringBuffer();
		for (Employee em : EmployeeDB) {

			sb.append(em.getUsername() + ":" + Helper.printStringList(em.getWishList()) + "\n");
		}
		return sb.toString();

	}

	/**
	 * A method to show help information
	 * 
	 * @return cmd
	 */
	protected static String pushHelp() {
		String cmds = "";
		if (GUIactive) {
			cmds += "Available commands:\n" + "\tFind employee\n" + "\tDiscontinue destination\n"
					+ "\tSearch destination\n" + "\tRemove employee\n" + "\tInformation on database\n"
					+ "\tList contents of database\n" + "\tText interface activated\n"
					+ "\tHelp on available commands\n" + "\tQuit database testing\n";
		} else {
			cmds += ("discontinue/d <destination> - discontinue the given <destination>\n")
					+ ("find/f <Employee> - find the given <Employee>\n") + ("gui/g Switch to GUI testing interface\n")
					+ ("help/h - display this help menu\n")
					+ ("information/i - display information about this Employee database\n")
					+ ("list/l - list contents of Employee database\n")
					+ ("search/s <destination> - search for the given <destination>\n") + ("quit/q - quit\n")
					+ ("remove/r <Employee> - remove the given <Employee>\n");

		}
		return cmds;
	}

	/**
	 * A method to quit the program
	 * 
	 * @return ""
	 */
	protected static String pushQuit() {
		System.exit(0);
		return "";
	}
}
