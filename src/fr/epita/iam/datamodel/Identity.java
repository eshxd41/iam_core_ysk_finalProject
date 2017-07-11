/**
 * 
 */
package fr.epita.iam.datamodel;

import java.util.Random;
import java.util.UUID;

/**
 * @author tbrou
 *
 */
public class Identity {
	
	private String displayName;
	private String uid;
	private String email;
	private String birthdate;
	
	
	public String getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}


	/**
	 * @param displayName
	 * @param uid
	 * @param email
	 */
	public Identity(String uid,String displayName,  String email , String birthdate) {
		Random random = new Random();
		this.displayName = displayName;
		this.uid = String.valueOf(random.nextInt(101));
		//System.out.println(this.uid);
		this.email = email;
		this.birthdate=birthdate;
	}
	
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Identity [uid=" + uid + ", displayName=" + displayName + ",  email=" + email + ", birthdate=" + birthdate + "]";
	}
	

	
	

}
