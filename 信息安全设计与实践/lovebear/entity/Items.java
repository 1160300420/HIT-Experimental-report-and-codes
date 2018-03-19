package com.lovebear.entity;

// default package



/**
 * Items entity. @author MyEclipse Persistence Tools
 */

public class Items  implements java.io.Serializable {


    // Fields    

     private ItemsId id;


    // Constructors

    /** default constructor */
    public Items() {
    }

    
    /** full constructor */
    public Items(ItemsId id) {
        this.id = id;
    }

   
    // Property accessors

    public ItemsId getId() {
        return this.id;
    }
    
    public void setId(ItemsId id) {
        this.id = id;
    }
   








}