package demo;

import java.io.Serializable;

public class ScreenshotImage implements Serializable
{
	private int id;
	private String reference_id,image_name;
	private String actual_created_on,created_on;
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReference_id() {
		return reference_id;
	}
	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}
	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
	public String getActual_created_on() {
		return actual_created_on;
	}
	public void setActual_created_on(String actual_created_on) {
		this.actual_created_on = actual_created_on;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

}
