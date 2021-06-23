package beans;

import java.util.*;

public class Deliverer extends User {
   private java.util.List<Delivery> deliveries;
   
   public Deliverer(String username, String password, String name, String surname, Gender gender, Date dateOfBirth,
			ArrayList<Delivery> deliveries) {
		super(username, password, name, surname, gender, dateOfBirth);
		this.deliveries = deliveries;
	}

   public java.util.List<Delivery> getDeliveries() {
      if (deliveries == null)
         deliveries = new java.util.Vector<Delivery>();
      return deliveries;
   }
   
   public java.util.Iterator getIteratorDeliveries() {
      if (deliveries == null)
         deliveries = new java.util.Vector<Delivery>();
      return deliveries.iterator();
   }
   
   public void setDeliveries(java.util.List<Delivery> newDelivery) {
      removeAllDeliveries();
      for (java.util.Iterator iter = newDelivery.iterator(); iter.hasNext();)
         addDelivery((Delivery)iter.next());
   }
   
   public void addDelivery(Delivery newDelivery) {
      if (newDelivery == null)
         return;
      if (this.deliveries == null)
         this.deliveries = new java.util.Vector<Delivery>();
      if (!this.deliveries.contains(newDelivery))
         this.deliveries.add(newDelivery);
   }
   
   public void removeDelivery(Delivery oldDelivery) {
      if (oldDelivery == null)
         return;
      if (this.deliveries != null)
         if (this.deliveries.contains(oldDelivery))
            this.deliveries.remove(oldDelivery);
   }
   
   public void removeAllDeliveries() {
      if (deliveries != null)
         deliveries.clear();
   }

}