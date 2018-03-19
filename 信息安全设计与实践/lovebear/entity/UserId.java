package com.lovebear.entity;
// default package



/**
 * UserId entity. @author MyEclipse Persistence Tools
 */

public class UserId  implements java.io.Serializable {


    // Fields    

     private Integer uid;
     private String username;
     private String pwdsalt;
     private String email;
     private String salt;


    // Constructors

    /** default constructor */
    public UserId() {
    }

    
    /** full constructor */
    public UserId(Integer uid, String username, String pwdsalt, String email,String salt) {
        this.uid = uid;
        this.username = username;
        this.pwdsalt = pwdsalt;
        this.email = email;
        this.salt = salt;
    }

   
    // Property accessors

    public Integer getUid() {
        return this.uid;
    }
    
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwdsalt() {
        return this.pwdsalt;
    }
    
    public void setPwdsalt(String pwdsalt) {
        this.pwdsalt = pwdsalt;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
   
    public String getSalt() {
        return this.salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof UserId) ) return false;
		 UserId castOther = ( UserId ) other; 
         
		 return ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getUsername()==castOther.getUsername()) || ( this.getUsername()!=null && castOther.getUsername()!=null && this.getUsername().equals(castOther.getUsername()) ) )
 && ( (this.getPwdsalt()==castOther.getPwdsalt()) || ( this.getPwdsalt()!=null && castOther.getPwdsalt()!=null && this.getPwdsalt().equals(castOther.getPwdsalt()) ) )
 && ( (this.getEmail()==castOther.getEmail()) || ( this.getEmail()!=null && castOther.getEmail()!=null && this.getEmail().equals(castOther.getEmail()) ) )
 && ( (this.getSalt()==castOther.getSalt()) || ( this.getSalt()!=null && castOther.getSalt()!=null && this.getSalt().equals(castOther.getSalt()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getUsername() == null ? 0 : this.getUsername().hashCode() );
         result = 37 * result + ( getPwdsalt() == null ? 0 : this.getPwdsalt().hashCode() );
         result = 37 * result + ( getEmail() == null ? 0 : this.getEmail().hashCode() );
         result = 37 * result + ( getSalt() == null ? 0 : this.getSalt().hashCode() );
         return result;
   }   





}