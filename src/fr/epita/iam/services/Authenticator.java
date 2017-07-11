/**
 * 
 */
package fr.epita.iam.services;

/**
 * Main authenticator service
 * @author suresh
 *
 */
public class Authenticator {
	
	/**
	 * This method is checking authentication
	 * @param userName
	 * @param password
	 * @return
	 */
	public static boolean authenticate(String userName, String password){
		
		return "root".equals(userName) && "root".equals(password);
		
	}

}
