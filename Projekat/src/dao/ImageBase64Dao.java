package dao;

import java.util.ArrayList;

import beans.Delivery;
import beans.ImageBase64;

public class ImageBase64Dao {

	private ArrayList<ImageBase64> images;
	
	public ImageBase64Dao(ArrayList<ImageBase64> images) {
		super();
		this.images = images;
	}
	
	public ArrayList<ImageBase64> getImages() {
		return images;
	}
	
	public void addImage(ImageBase64 image) {
		images.add(image);
	}
	
	public String getImageData(String id) {
		for(ImageBase64 image : images) {
			if(image.getId().equals(id)) {
				return image.getBase64Data();
			}
		}
		return "";
	}
	
	public String generateId() {
		int max = 0;
		String retVal = "0000000000";
		for(ImageBase64 image : images) {
			if(max < Integer.parseInt(image.getId())) {
				max = Integer.parseInt(image.getId());
			}
		}
		max += 1;
		String id = String.valueOf(max);
		retVal = retVal.substring(0, retVal.length() - id.length());
		retVal = retVal.concat(id);
		return retVal;
	}
	
}
