package com.lovebear.entity;
// default package



/**
 * Orders entity. @author MyEclipse Persistence Tools
 */

public class Orders  implements java.io.Serializable {


    // Fields    

     private OrdersId id;


    // Constructors

    /** default constructor */
    public Orders() {
    }

    
    /** full constructor */
    public Orders(OrdersId id) {
        this.id = id;
    }

   
    // Property accessors

    public OrdersId getId() {
        return this.id;
    }
    
    public void setId(OrdersId id) {
        this.id = id;
    }
   








}