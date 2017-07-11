/**
 * 
 */
package fr.epita.iam.tests.services;

import java.sql.SQLException;
import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoSaveException;
import fr.epita.iam.exceptions.DaoSearchException;
import fr.epita.iam.services.FileIdentityDAO;
import fr.epita.iam.services.JDBCIdentityDAO;

/**
 * @author tbrou
 *
 */
public class TestFileIdentityDAO {

	/**
	 * @param args
	 * @throws DaoSearchException 
	 */
	public static void main(String[] args) throws DaoSearchException {
		// given the following service and that identity
		Identity identity = new Identity("123","Thomas Broussard", "tbr@tbr.com", "1994-06-25");
		JDBCIdentityDAO dao = null;
		try {
			dao = new JDBCIdentityDAO();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	/*	
		try {
			dao.save(identity);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		List<Identity> identityList = dao.search(null);
		System.out.println(identityList);*/
		
		Identity identity2 = new Identity(identity.getUid(),"bharath", identity.getEmail(), identity.getBirthdate());
		System.out.println(identity2);
		try
		{
			dao.update(identity2,"bharath");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		/*try {
			dao.save(identity);
		} catch (DaoSaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*try {
			dao.delete(identity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*// when you call the save method
		List<Identity> identityList = dao.search(null);
		int initialSize = identityList.size();
	
		try {
			;
		} catch (DaoSaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		identityList = dao.search(null);
		int finalSize = identityList.size();
		
		
		// then
		// TODO check that the file is getting created
		if (finalSize - initialSize != 1){
			System.out.println("error!");
		} 
		
		System.out.println(identityList);
		*/

	}

}
