package dataEntities;

import java.sql.Timestamp;

import org.json.simple.JSONObject;

public class ShoppingNoteEntity extends NoteEntity {

	@Override
	public String toString() {
		return "ShoppingNoteEntity [productToBuy=" + productToBuy + ", productCategory=" + productCategory
				+ ", servernoteID=" + servernoteID + ", userID=" + userID + ", creationDate=" + creationDate
				+ ", isDone=" + isDone + ", isTextCategorized=" + isTextCategorized + ", noteType=" + noteType + "]";
	}
	private String productToBuy;
	private String productCategory;
	public String getProductToBuy() {
		return productToBuy;
	}
	public void setProductToBuy(String productToBuy) {
		this.productToBuy = productToBuy;
	}
	public String getProductCategory() {
		return productCategory;
	}
	
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public ShoppingNoteEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ShoppingNoteEntity(String noteID, String userID, Timestamp creationDate, boolean isDone,
			boolean isTextCategorized, String noteType, String productToBuy, String productCategory,String priority) {
		super(noteID, userID, creationDate, isDone, isTextCategorized, noteType,priority);
		this.productToBuy = productToBuy;
		this.productCategory = productCategory;
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
}
