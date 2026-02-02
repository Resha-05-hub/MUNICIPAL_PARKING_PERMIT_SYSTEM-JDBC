package com.parking.bean;
import java.sql.Date;
import java.math.BigDecimal;
public class ParkingPermit{
    private String permitID;                 
    private String permitHolderID;           
    private String vehicleRegNo;             
    private String vehicleType;              
    private String zoneCode;                
    private Date permitStartDate;          
    private Date permitEndDate;            
    private String permitStatus;            
    private int violationCount;             
    private BigDecimal outstandingFineAmount; 
    public String getPermitID(){
        return permitID;
    }
    public void setPermitID(String permitID){
        this.permitID = permitID;
    }
    public String getPermitHolderID(){
        return permitHolderID;
    }
    public void setPermitHolderID(String permitHolderID){
        this.permitHolderID = permitHolderID;
    }
    public String getVehicleRegNo(){
        return vehicleRegNo;
    }
    public void setVehicleRegNo(String vehicleRegNo){
        this.vehicleRegNo = vehicleRegNo;
    }
    public String getVehicleType(){
        return vehicleType;
    }
    public void setVehicleType(String vehicleType){
        this.vehicleType = vehicleType;
    }
    public String getZoneCode(){
        return zoneCode;
    }
    public void setZoneCode(String zoneCode){
        this.zoneCode = zoneCode;
    }
    public Date getPermitStartDate(){
        return permitStartDate;
    }
    public void setPermitStartDate(Date permitStartDate){
        this.permitStartDate = permitStartDate;
    }
    public Date getPermitEndDate(){
        return permitEndDate;
    }
    public void setPermitEndDate(Date permitEndDate){
        this.permitEndDate = permitEndDate;
    }
    public String getPermitStatus(){
        return permitStatus;
    }
    public void setPermitStatus(String permitStatus){
        this.permitStatus = permitStatus;
    }
    public int getViolationCount(){
        return violationCount;
    }
    public void setViolationCount(int violationCount){
        this.violationCount = violationCount;
    }
    public BigDecimal getOutstandingFineAmount(){
        return outstandingFineAmount;
    }
    public void setOutstandingFineAmount(BigDecimal outstandingFineAmount){
        this.outstandingFineAmount = outstandingFineAmount;
    }
}
