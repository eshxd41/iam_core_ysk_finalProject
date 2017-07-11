/**
 * 
 */
package fr.epita.iam.tests.datamodel;

import fr.epita.iam.datamodel.Identity;

/**
 * @author tbrou
 *
 */
public class TestIdentity {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Identity identity = new Identity("001","Thomas Broussard",  "tbr@tbr.com","1994-6-25");
		
		System.out.println(identity);

	}

}
