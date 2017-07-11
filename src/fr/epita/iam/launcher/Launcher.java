/**
 * 
 */
package fr.epita.iam.launcher;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoDeleteException;
import fr.epita.iam.exceptions.DaoSaveException;
import fr.epita.iam.exceptions.DaoSearchException;
import fr.epita.iam.exceptions.DaoUpdateException;
import fr.epita.iam.services.Authenticator;
import fr.epita.iam.services.JDBCIdentityDAO;
import fr.epita.logging.LogConfiguration;
import fr.epita.logging.Logger;

/**
 * @author Y Suresh
 *
 */
public class Launcher {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws DaoSearchException
	 */
	public static void main(String[] args)
			throws FileNotFoundException, SQLException, DaoSearchException, DaoSaveException {

		LogConfiguration conf = new LogConfiguration("./tmp/application.log");
		Logger logger = new Logger(conf);

		logger.log("beginning of the program");
		System.out.println("--- WELCOME TO THE BANK ---");
		login();
	}

	/**
	 * This is the method is used to login to our Banking System
	 */
	private static void login() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("User name :");
		String userName = scanner.nextLine();
		System.out.println("Password :");
		String password = scanner.nextLine();
		ArrayList<String> credentials = new ArrayList<String>();
		credentials.add(userName);
		credentials.add(password);
		try {
			application(userName, password);
		} catch (SQLException | DaoSearchException | DaoDeleteException | DaoUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This application() method will provide all the operations performed in
	 * our Banking System.
	 * 
	 * It will provide to create, search, update and delete the entries in the
	 * system
	 * 
	 * If you enter any identity name to update/delete it will search and get
	 * back to you with out any case issues. i.e. not case sensitive
	 * 
	 *
	 * @param userName
	 * @param password
	 * @throws SQLException
	 * @throws DaoSearchException
	 * @throws DaoDeleteException
	 * @throws DaoUpdateException
	 */
	private static void application(String userName, String password)
			throws SQLException, DaoSearchException, DaoDeleteException, DaoUpdateException {
		JDBCIdentityDAO dao = new JDBCIdentityDAO();

		LogConfiguration conf = new LogConfiguration("./tmp/application.log");
		Logger logger = null;
		try {
			logger = new Logger(conf);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);
		if (!Authenticator.authenticate(userName, password)) {
			logger.log("unable to authenticate " + userName);

			System.out.println("Sorry!!..Please Enter Correct Credientials!!!");
			login();

			return;
		} else {
			System.out.println("Successfully authenticated");
			String answer = "";
			while (!"3".equals(answer)) {

				System.out.println("1. Create Identity/User :");
				System.out.println("2. Search TO (Update/Delete) Identity/User :");
				System.out.println("3. Exit");
				System.out.println("Please Enter your choice : ");

				logger.log("User chose the " + answer + " choice");

				answer = scanner.nextLine();

				switch (answer) {
				
				/**
				 * Creates a new Identity/user
				 * takes inputs using 'scanner' into 'displayName', 'email','birthdate' Variables
				 * create an object identity with null for uid<created by unique id>,other three variables
				 * throw exception DaoSaveEception e if wrong format..call it by e.getmessage()
				 */
				case "1":
					System.out.println("Identity Creation");
					logger.log("selected the identity creation");
					System.out.println("Please input the identity display name :");
					String displayName = scanner.nextLine();
					System.out.println("identity email :");
					String email = scanner.nextLine();
					System.out.println("birthdate :");
					String birthdate = scanner.nextLine();

					Identity identity = new Identity(null, displayName, email, birthdate);
					try {
						dao.save(identity);
						System.out.println("Client Created successfully");
					} catch (DaoSaveException e) {
						System.out.println("The save operation is not able to complete, details, complete all..:"
								+ e.getMessage());
					}

					break;
					
				/**
				 * search option- user data(name email birthDate)
				 * 
				 * it should use Search function to perform the update, delete operations
				 * 
				 * select the record by displayName , if not available throw user defined exception
				 * 
				 * search can be done in any case..upper or lower case by using ignoreCase
				 * 
				 *  throw user understandable exception
				 *  
				 *  prepare 2 cases for Update and delete identity/user
				 */
				case "2":
					System.out.println("Identity Update");
					Identity criteria = new Identity(null, null, null, null);
					List<Identity> identities = dao.search(criteria);
					System.out.println("Please Enter Name to search : \n");
					String displayNametoUpdate = scanner.nextLine();
					String selectedIdentity = "";
					for (Identity idens : identities) {
						if (idens.getDisplayName().equalsIgnoreCase(displayNametoUpdate)) {
							selectedIdentity = idens.getDisplayName();
							System.out.println("Name : " + idens.getDisplayName() + "\t");
							System.out.println("Date of Birth : " + idens.getBirthdate() + "\t");
							System.out.println("Email : " + idens.getEmail() + "\n");
						}
					}
					if (selectedIdentity.equalsIgnoreCase(displayNametoUpdate)) {
						System.out.println("1.Update values");
						System.out.println("2.Delete values");
						System.out.println("3.Exit.");
						System.out.println("Enter your choice : ");
					} else {
						System.out.println("Invalid entry. Please try again. \n");
						application(userName, password);
					}

					Scanner sc = new Scanner(System.in);
					String ch = sc.nextLine();
					switch (ch) {
					
					/**
					 * take new data into displayName, email, birtDate by scanner
					 * 
					 */
					
					case "1":						
						System.out.println(
								"Please Enter Identity Display Name to update for :" + displayNametoUpdate);
						String displayName1 = scanner.nextLine();
						System.out.println("identity email :");
						String email1 = scanner.nextLine();
						System.out.println("Birthdate :");
						String birthdate1 = scanner.nextLine();

						Identity identity1 = new Identity(null, displayName1, email1, birthdate1);
						dao.update(identity1, displayNametoUpdate);
						System.out.println("The Update operation completed successfully");
						break;
					
					case "2":
						dao.delete(displayNametoUpdate);
						System.out.println("Successfully deleted the record for:" + displayNametoUpdate);

						break;
					
					case "3":
						break;
					}
					break;

				case "3":

					System.out.println("You decided to Exit, bye!");
					break;
				default:

					System.out.println("Unrecognized option : type 1,2,3 or 4 to EXIT");
					break;
				}

			}

		}
	}

}
