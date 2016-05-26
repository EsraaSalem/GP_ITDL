package dataEntities;

import java.util.Date;

public class InputType {
	
	private String text;
	private String sourcetype;
	private Date creationDate;
	private String textCategory;
	public InputType() {
		super();
	}
	public InputType(String text, String sourcetype, Date creationDate, String textCategory) {
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
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
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
