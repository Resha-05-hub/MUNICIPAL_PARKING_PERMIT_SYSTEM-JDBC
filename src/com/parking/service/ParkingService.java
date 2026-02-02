package com.parking.service;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Connection;
import java.sql.SQLException;
import com.parking.bean.PermitHolder;
import com.parking.bean.ParkingPermit;
import com.parking.dao.PermitHolderDAO;
import com.parking.dao.ParkingPermitDAO;
import com.parking.util.DBUtil;
import com.parking.util.ActivePermitOrFineExistsException;
import com.parking.util.FineProcessingException;
import com.parking.util.ValidationException;
public class ParkingService{
    private PermitHolderDAO holderDAO = new PermitHolderDAO();
    private ParkingPermitDAO permitDAO = new ParkingPermitDAO();
    public PermitHolder viewPermitHolderDetails(String permitHolderID){
        if(permitHolderID == null || permitHolderID.trim().isEmpty()){
            return null;
        }
        return holderDAO.findPermitHolder(permitHolderID);
    }
    public List<PermitHolder> viewAllPermitHolders(){
        return holderDAO.viewAllPermitHolders();
    }
    public boolean registerNewPermitHolder(PermitHolder holder) throws ValidationException{
        if (holder == null || 
            holder.getPermitHolderID() == null || 
            holder.getPermitHolderID().trim().isEmpty() ||
            holder.getFullName() == null || 
            holder.getFullName().trim().isEmpty() ||
            holder.getHolderType() == null || 
            holder.getHolderType().trim().isEmpty() ||
            holder.getAddressLine1() == null ||
            holder.getAddressLine1().trim().isEmpty() ||
            holder.getWardCode() == null || 
            holder.getWardCode().trim().isEmpty() ||
            holder.getMobile() == null || 
            holder.getMobile().trim().isEmpty()){
            throw new ValidationException();
        }
        if(!holder.getMobile().matches("\\d+") || holder.getMobile().length() < 10){
            throw new ValidationException();
        }
        if(holderDAO.findPermitHolder(holder.getPermitHolderID()) != null){
            throw new ValidationException();
        }
        if(holder.getStatus() == null || holder.getStatus().trim().isEmpty()){
            holder.setStatus("ACTIVE");
        }
        return holderDAO.insertPermitHolder(holder);
    }
    public ParkingPermit viewParkingPermitDetails(String permitID){
        return permitDAO.findParkingPermit(permitID);
    }
    public List<ParkingPermit>viewPermitsByHolder(String permitHolderID){
        return permitDAO.findPermitsByHolder(permitHolderID);
    }
    public boolean applyForNewPermit(ParkingPermit permit) throws ValidationException{
    	 if(permit==null ||
    		     permit.getPermitID()==null ||
    		     permit.getPermitID().trim().isEmpty() ||
    		     permit.getPermitHolderID()==null ||
    		     permit.getPermitHolderID().trim().isEmpty() ||
    		     permit.getVehicleRegNo()==null ||
    		     permit.getVehicleType()==null ||
    		     permit.getZoneCode()==null ||
    		     permit.getPermitStartDate()==null ||
    		     permit.getPermitEndDate()==null){
    		     throw new ValidationException();
    		  }
        if(!permit.getPermitEndDate().after(permit.getPermitStartDate())){
            throw new ValidationException();
        }
        PermitHolder holder = holderDAO.findPermitHolder(permit.getPermitHolderID());
        if(holder == null || !"ACTIVE".equals(holder.getStatus())){
            return false;
        }
        if(permitDAO.findActivePermitForVehicleAndZone(permit.getVehicleRegNo(), permit.getZoneCode()) != null){
            throw new ValidationException();
        }
        permit.setPermitStatus("ACTIVE");
        permit.setViolationCount(0);
        permit.setOutstandingFineAmount(BigDecimal.ZERO);
        try(Connection con = DBUtil.getDBConnection()){
            con.setAutoCommit(false);
            boolean result = permitDAO.insertParkingPermit(permit);
            if (result) con.commit(); else con.rollback();
            return result;
        }catch (SQLException e){
        	return false;
        	}
    }
    public boolean renewPermit(String permitID, Date newEndDate) throws ValidationException{
        if (permitID == null || permitID.trim().isEmpty() || newEndDate == null){
            throw new ValidationException();
        }
        ParkingPermit permit = permitDAO.findParkingPermit(permitID);
        if (permit == null || "CANCELLED".equals(permit.getPermitStatus())){
            return false;
        }
        if(!newEndDate.after(permit.getPermitEndDate())){
            throw new ValidationException();
        }
        try(Connection con = DBUtil.getDBConnection()){
            con.setAutoCommit(false); 
            boolean result = permitDAO.updatePermitDatesAndStatus(permitID, permit.getPermitStartDate(), newEndDate, "ACTIVE");
            if (result) con.commit(); 
            else con.rollback();
            return result;
        }catch (SQLException e){ 
        	return false; 
        	}
    }
    public boolean recordViolation(String permitID, BigDecimal fineToAdd) throws ValidationException, FineProcessingException{
        if(permitID == null || permitID.trim().isEmpty() || fineToAdd == null || fineToAdd.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValidationException();
        }
        ParkingPermit permit = permitDAO.findParkingPermit(permitID);
        if(permit == null) return false;
        if("CANCELLED".equals(permit.getPermitStatus()) || "EXPIRED".equals(permit.getPermitStatus())){
            throw new FineProcessingException();
        }
        int newCount = permit.getViolationCount() + 1;
        BigDecimal newFine = permit.getOutstandingFineAmount().add(fineToAdd);
        try(Connection con = DBUtil.getDBConnection()){
            con.setAutoCommit(false); 
            boolean result = permitDAO.updateViolationAndFine(permitID, newCount, newFine);
            if (result) con.commit(); else con.rollback();
            return result;
        }catch (SQLException e){
        	return false;
        	}
    }
    public boolean deactivateOrRemovePermitHolder(String permitHolderID) throws ValidationException, ActivePermitOrFineExistsException {
        if(permitHolderID == null || permitHolderID.trim().isEmpty()){
            throw new ValidationException();
        }
        List<ParkingPermit> activeList = permitDAO.findActivePermitsByHolder(permitHolderID);
        BigDecimal totalFine = permitDAO.calculateTotalOutstandingFineForHolder(permitHolderID);
        if(!activeList.isEmpty() || totalFine.compareTo(BigDecimal.ZERO) > 0){
            throw new ActivePermitOrFineExistsException();
        }
        return holderDAO.updatePermitHolderStatus(permitHolderID, "INACTIVE");
    }
}