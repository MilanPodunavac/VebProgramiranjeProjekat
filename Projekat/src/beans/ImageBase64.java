package beans;

public class ImageBase64 {

	private String id;
	private String base64Data;
	
	public ImageBase64() {
		
	}

	public ImageBase64(String id, String base64Data) {
		super();
		this.id = id;
		this.base64Data = base64Data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBase64Data() {
		return base64Data;
	}

	public void setBase64Data(String base64Data) {
		this.base64Data = base64Data;
	}
	
	
	
}
