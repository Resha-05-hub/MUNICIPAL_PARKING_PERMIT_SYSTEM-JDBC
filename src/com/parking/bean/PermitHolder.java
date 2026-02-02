package com.parking.bean;
public class PermitHolder {
    private String permitHolderID;
    private String fullName;
    private String holderType;      
    private String addressLine1;
    private String addressLine2;    
    private String wardCode;
    private String mobile;
    private String email;
    private String status;       
    public String getPermitHolderID(){
        return permitHolderID;
    }
    public void setPermitHolderID(String permitHolderID){
        this.permitHolderID = permitHolderID;
    }
    public String getFullName(){
        return fullName;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public String getHolderType(){
        return holderType;
    }
    public void setHolderType(String holderType){
        this.holderType = holderType;
    }
    public String getAddressLine1(){
        return addressLine1;
    }
    public void setAddressLine1(String addressLine1){
        this.addressLine1 = addressLine1;
    }
    public String getAddressLine2(){
        return addressLine2;
    }
    public void setAddressLine2(String addressLine2){
        this.addressLine2 = addressLine2;
    }
    public String getWardCode(){
        return wardCode;
    }
    public void setWardCode(String wardCode){
        this.wardCode = wardCode;
    }
    public String getMobile(){
        return mobile;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
