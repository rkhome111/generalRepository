package demo;

public class Images 
{
	int id;
	
	private String reference_id,image_name,folder_name;
	byte[] image_content;
	
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
	public String getFolder_name() {
		return folder_name;
	}
	public void setFolder_name(String folder_name) {
		this.folder_name = folder_name;
	}
	public byte[] getImage_content() {
		return image_content;
	}
	public void setImage_content(byte[] image_content) {
		this.image_content = image_content;
	}

}
