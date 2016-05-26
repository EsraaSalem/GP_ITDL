package dataEntities;

public class UserEntity {
	private long UserId;
	private String UserName;
	private String UserEmail;
	private String UserTwitterAccount;
	private String Gender;
	private String UserPassword;
	private String City ;
	private String DateOfBirth;
	private int age;
	private String userDB_ID;
	private static UserEntity currentUser;
	
	
	private UserEntity() {
		super();
		userDB_ID = "111";
		UserTwitterAccount = "@yasmeenkloub";
		
	}

	
	public static UserEntity getUserInstance()
	{
		if(currentUser == null)
		{
			currentUser  = new UserEntity();
		}
		return currentUser;
	}
	private UserEntity(String userName, String userEmail,
			String userTwitterAccount, String gender, String userPassword,
			String city, String dateOfBirth) {
	
		UserName = userName;
		UserEmail = userEmail;
		UserTwitterAccount = userTwitterAccount;
		Gender = gender;
		UserPassword = userPassword;
		City = city;
		DateOfBirth = dateOfBirth;
	}

	public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserEmail() {
		return UserEmail;
	}

	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}

	public String getUserTwitterAccount() {
		return UserTwitterAccount;
	}

	public void setUserTwitterAccount(String userTwitterAccount) {
		UserTwitterAccount = userTwitterAccount;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUserDB_ID() {
		return userDB_ID;
	}

	public void setUserDB_ID(String userDB_ID) {
		this.userDB_ID = userDB_ID;
	}

	
	
}
