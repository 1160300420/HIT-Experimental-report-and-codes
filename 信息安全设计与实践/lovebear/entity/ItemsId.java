package com.lovebear.entity;
// default package



/**
 * ItemsId entity. @author MyEclipse Persistence Tools
 */

public class ItemsId  implements java.io.Serializable {


    // Fields    

     private String itemId;
     private String itemName;
     private String itemPrice;
     private Integer inventory;
     private String imgUrl;


    // Constructors

    /** default constructor */
    public ItemsId() {
    }

    
    /** full constructor */
    public ItemsId(String itemId, String itemName, String itemPrice, Integer inventory, String imgUrl) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.inventory = inventory;
        this.imgUrl = imgUrl;
    }

   
    // Property accessors

    public String getItemId() {
        return this.itemId;
    }
    
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return this.itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return this.itemPrice;
    }
    
    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getInventory() {
        return this.inventory;
    }
    
    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }
    
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ItemsId) ) return false;
		 ItemsId castOther = ( ItemsId ) other; 
         
		 return ( (this.getItemId()==castOther.getItemId()) || ( this.getItemId()!=null && castOther.getItemId()!=null && this.getItemId().equals(castOther.getItemId()) ) )
 && ( (this.getItemName()==castOther.getItemName()) || ( this.getItemName()!=null && castOther.getItemName()!=null && this.getItemName().equals(castOther.getItemName()) ) )
 && ( (this.getItemPrice()==castOther.getItemPrice()) || ( this.getItemPrice()!=null && castOther.getItemPrice()!=null && this.getItemPrice().equals(castOther.getItemPrice()) ) )
 && ( (this.getInventory()==castOther.getInventory()) || ( this.getInventory()!=null && castOther.getInventory()!=null && this.getInventory().equals(castOther.getInventory()) ) )
 && ( (this.getImgUrl()==castOther.getImgUrl()) || ( this.getImgUrl()!=null && castOther.getImgUrl()!=null && this.getImgUrl().equals(castOther.getImgUrl()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getItemId() == null ? 0 : this.getItemId().hashCode() );
         result = 37 * result + ( getItemName() == null ? 0 : this.getItemName().hashCode() );
         result = 37 * result + ( getItemPrice() == null ? 0 : this.getItemPrice().hashCode() );
         result = 37 * result + ( getInventory() == null ? 0 : this.getInventory().hashCode() );
         result = 37 * result + ( getImgUrl() == null ? 0 : this.getImgUrl().hashCode() );
         return result;
   }   





}