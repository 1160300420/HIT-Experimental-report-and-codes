package com.lovebear.entity;
// default package



/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User  implements java.io.Serializable {


    // Fields    

     private UserId id;


    // Constructors

    /** default constructor */
    public User() {
    }

    
    /** full constructor */
    public User(UserId id) {
        this.id = id;
    }

   
    // Property accessors

    public UserId getId() {
        return this.id;
    }
    
    public void setId(UserId id) {
        this.id = id;
    }
   








}