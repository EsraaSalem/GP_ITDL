package dataEntities;

import java.sql.Timestamp;
import java.util.Date;

public class InputType {
	
	private String text;
	private String sourcetype;
	private Timestamp creationDate;
	private String textCategory;
	public InputType() {
		super();
	}
	public InputType(String text, String sourcetype, Timestamp creationDate, String textCategory) {
		super();
		this.text = text;
		this.sourcetype = sourcetype;
		this.creationDate = creationDate;
		this.textCategory = textCategory;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSourcetype() {
		return sourcetype;
	}
	public void setSourcetype(String sourcetype) {
		this.sourcetype = sourcetype;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public String getTextCategory() {
		return textCategory;
	}
	public void setTextCategory(String textCategory) {
		this.textCategory = textCategory;
	}
	@Override
	public String toString() {
		return "InputType [text=" + text + ", sourcetype=" + sourcetype + ", creationDate=" + creationDate
				+ ", textCategory=" + textCategory + "]";
	}
	
	
	
	
}
