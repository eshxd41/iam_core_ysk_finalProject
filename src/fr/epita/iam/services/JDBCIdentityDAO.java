/**
 * 
 */
package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoDeleteException;
import fr.epita.iam.exceptions.DaoSaveException;
import fr.epita.iam.exceptions.DaoSearchException;
import fr.epita.iam.exceptions.DaoUpdateException;

/**
 * 
 * This is a class that contains all the database operations for the class
 * Identity
 * 
 * <pre>
 *  JDBCIdentityDAO dao = new JDBCIdentityDAO();
 *  // save an identity
 *  dao.save(new Identity(...));
 *  
 *  //search with an example criteria (qbe)  
 *  dao.search(new Identity(...);
 * </pre>
 * 
 * <b>warning</b> this class is dealing with database connections, so beware to
 * release it through the {@link #releaseResources()}
 * 
 * @author tbrou
 *
 */
public class JDBCIdentityDAO implements IdentityDAO {

	Connection connection;

	/**
	 * @throws SQLException
	 * 
	 */
	public JDBCIdentityDAO() throws SQLException {
		String myDriver = "org.gjt.mm.mysql.Driver";
		String myUrl = "jdbc:mysql://localhost/yskdb";
		this.connection = DriverManager.getConnection(myUrl, "root", "root");
	}

	/**
	 * query for the saving the details of the user, takes
	 * UID,Displayname,EMAIL,Birthdate by using identity object execute the save
	 * option
	 * 
	 * Throws exception if the format is wrong example abc, thomas,suresh@gm,
	 * 1992-10-12
	 * 
	 */
	public void save(Identity identity) throws DaoSaveException {
		try {
			String query = " insert into identities (IDENTITY_UID,IDENTITY_DISPLAYNAME,IDENTITY_EMAIL,IDENTITY_BIRTHDATE)"
					+ " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = this.connection.prepareStatement(query);
			preparedStmt.setString(1, identity.getUid());
			preparedStmt.setString(2, identity.getDisplayName());
			preparedStmt.setString(3, identity.getEmail());
			preparedStmt.setString(4, identity.getBirthdate());
			preparedStmt.executeUpdate();
		} catch (SQLException sqle) {
			DaoSaveException exception = new DaoSaveException();
			exception.initCause(sqle);
			exception.setFaultObject(identity);
			throw exception;
		}
	}

	//
	/**
	 * search identity by criteria put all the identity into a list of array
	 * object returnedList write query to display all identities from the table
	 * according to criteria store the result of the found records in a veriable
	 * 'results'
	 * 
	 * @throw message if record not found
	 * 
	 * @throws DaoSearchException
	 * 
	 */
	public List<Identity> search(Identity criteria) throws DaoSearchException {
		List<Identity> returnedList = new ArrayList<Identity>();
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from IDENTITIES");

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				String displayName = results.getString("IDENTITY_DISPLAYNAME");
				String email = results.getString("IDENTITY_EMAIL");
				String birthdate = results.getString("IDENTITY_BIRTHDATE");
				returnedList.add(new Identity(null, displayName, email, birthdate));

			}
		} catch (SQLException sqle) {
			DaoSearchException daose = new DaoSearchException();
			daose.initCause(sqle);
			throw daose;
		}

		return returnedList;
	}

	/**
	 * this is releasing the database connection, so you should not use this
	 * instance of DAO anymore
	 */
	public void releaseResources() {
		try {
			this.connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.epita.iam.services.IdentityDAO#update(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void update(Identity identity, String name) throws DaoUpdateException {
		String query = "UPDATE identities SET IDENTITY_DISPLAYNAME='" + identity.getDisplayName()
				+ "', IDENTITY_EMAIL='" + identity.getEmail() + "', IDENTITY_BIRTHDATE='" + identity.getBirthdate()
				+ "' WHERE IDENTITY_DISPLAYNAME='" + name + "'";

		try {

			PreparedStatement preparedStmt = this.connection.prepareStatement(query);
			preparedStmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.epita.iam.services.IdentityDAO#delete(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	/**
	 * Creating a Delete fuction which can perform delete option on the database, 'displayname<col_name> is another criteria
	 * takes value into string variable 'name' 
	 * delete from table identities
	 * it will delete by using name of person a client, clears whole record from table identities
	 * 
	 * @throw exception if the record is not present .exception DaoDeleteException 
	 * execute it
	 * 
	 */
	public void delete(String name) throws DaoDeleteException {
		String query = "DELETE FROM identities WHERE IDENTITY_DISPLAYNAME='" + name + "'";
		PreparedStatement preparedStmt = null;
		try {
			preparedStmt = this.connection.prepareStatement(query);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {

			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
