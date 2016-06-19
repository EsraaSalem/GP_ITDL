package Model;


import java.util.Calendar;
import java.util.GregorianCalendar;

public class User {
	long  UserId;
	String UserName;
	String UserEmail;
	String UserTwitterAccount;
	String Gender;
	String UserPassword;
	String City ;
	String DateOfBirth;
	int age;
	
	
	public User(String useremail,String password) {
		UserEmail=useremail;
		UserPassword=password;
	
	}

	public User(long ID){
		UserId =ID;
	}
	
	/**
	 * @param userName
	 * @param userEmail
	 * @param userTwitterAccount
	 * @param gender
	 * @param userPassword
	 * @param city
	 * @param dateOfBirth
	 * @param age
	 */
	public User(String userName, String userEmail,  String userPassword
				,String userTwitterAccount, String gender, String city,
				String dateOfBirth) {
		UserName = userName;
		UserEmail = userEmail;
		UserTwitterAccount = userTwitterAccount;
		Gender = gender;
		UserPassword = userPassword;
		City = city;
		DateOfBirth = dateOfBirth;
	}
	
	
	
	public User(String name, String email, String pass,long id ) {
		UserName = name;

		UserEmail = email;

		UserPassword = pass;
		UserId=id;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return UserId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		UserId = userId;
	}


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return UserName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		UserName = userName;
	}

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return UserEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}

	/**
	 * @return the userTwitterAccount
	 */
	public String getUserTwitterAccount() {
		return UserTwitterAccount;
	}

	/**
	 * @param userTwitterAccount the userTwitterAccount to set
	 */
	public void setUserTwitterAccount(String userTwitterAccount) {
		UserTwitterAccount = userTwitterAccount;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return Gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		Gender = gender;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return UserPassword;
	}

	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return City;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		City = city;
	}

	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return DateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}

	/**
	 * @return the age
	 */
	public int getAge (int _year, int _month, int _day) {

	        GregorianCalendar cal = new GregorianCalendar();
	        int y, m, d;         
	        
	        y = cal.get(Calendar.YEAR);
	        m = cal.get(Calendar.MONTH);
	        d = cal.get(Calendar.DAY_OF_MONTH);
	        cal.set(_year, _month, _day);
	        age = y - cal.get(Calendar.YEAR);
	        if ((m < cal.get(Calendar.MONTH))
	                        || ((m == cal.get(Calendar.MONTH)) && (d < cal
	                                        .get(Calendar.DAY_OF_MONTH)))) {
	                --age;
	        }
	        if(age < 0)
	                throw new IllegalArgumentException("Age < 0");
	        return age;
	    
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	

}
