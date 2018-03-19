package com.lovebear.entity;
// default package



/**
 * OrdersId entity. @author MyEclipse Persistence Tools
 */

public class OrdersId  implements java.io.Serializable {


    // Fields    

     private String orderId;
     private String custname;
     private String totalPrice;
     private String status;


    // Constructors

    /** default constructor */
    public OrdersId() {
    }

    
    /** full constructor */
    public OrdersId(String orderId, String custname, String totalPrice, String status) {
        this.orderId = orderId;
        this.custname = custname;
        this.totalPrice = totalPrice;
        this.status = status;
    }

   
    // Property accessors

    public String getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustname() {
        return this.custname;
    }
    
    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getTotalPrice() {
        return this.totalPrice;
    }
    
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof OrdersId) ) return false;
		 OrdersId castOther = ( OrdersId ) other; 
         
		 return ( (this.getOrderId()==castOther.getOrderId()) || ( this.getOrderId()!=null && castOther.getOrderId()!=null && this.getOrderId().equals(castOther.getOrderId()) ) )
 && ( (this.getCustname()==castOther.getCustname()) || ( this.getCustname()!=null && castOther.getCustname()!=null && this.getCustname().equals(castOther.getCustname()) ) )
 && ( (this.getTotalPrice()==castOther.getTotalPrice()) || ( this.getTotalPrice()!=null && castOther.getTotalPrice()!=null && this.getTotalPrice().equals(castOther.getTotalPrice()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getOrderId() == null ? 0 : this.getOrderId().hashCode() );
         result = 37 * result + ( getCustname() == null ? 0 : this.getCustname().hashCode() );
         result = 37 * result + ( getTotalPrice() == null ? 0 : this.getTotalPrice().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         return result;
   }   





}