package beans;

public class DeliveryRequest {
	private String managerUsername;
	private String delivererUsername;
	private String deliveryId;
	private boolean deleted;
	private boolean pending;
	
	public DeliveryRequest() {
		deleted = false;
		pending = true;
	}
	
	public DeliveryRequest(String managerUsername, String delivererUsername, String deliveryId) {
		super();
		this.managerUsername = managerUsername;
		this.delivererUsername = delivererUsername;
		this.deliveryId = deliveryId;
		deleted = false;
		pending = true;
	}
	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getManagerUsername() {
		return managerUsername;
	}
	public void setManagerUsername(String managerUsername) {
		this.managerUsername = managerUsername;
	}
	public String getDelivererUsername() {
		return delivererUsername;
	}
	public void setDelivererUsername(String delivererUsername) {
		this.delivererUsername = delivererUsername;
	}
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
}
