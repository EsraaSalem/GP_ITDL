package dataEntities;

import java.sql.Timestamp;

public class FacebookPost {
	
	
	private String userID;
	private String postID;
	private String postContent;
	private int read;
	private Timestamp postCreationDate;
	public FacebookPost(String userID, String postID, String postContent, int read, Timestamp postCreationDate) {
		super();
		this.userID = userID;
		this.postID = postID;
		this.postContent = postContent;
		this.read = read;
		this.postCreationDate = postCreationDate;
	}
	public Timestamp getPostCreationDate() {
		return postCreationDate;
	}
	public void setPostCreationDate(Timestamp postCreationDate) {
		this.postCreationDate = postCreationDate;
	}
	public FacebookPost() {
		super();
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPostID() {
		return postID;
	}
	public void setPostID(String postID) {
		this.postID = postID;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	@Override
	public String toString() {
		return "FacebookPost [userID=" + userID + ", postID=" + postID + ", postContent=" + postContent + ", read="
				+ read + ", postCreationDate=" + postCreationDate + "]";
	}
	

}
