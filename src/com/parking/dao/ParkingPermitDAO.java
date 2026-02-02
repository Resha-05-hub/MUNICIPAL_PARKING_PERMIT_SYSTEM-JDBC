package com.parking.dao;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
import com.parking.bean.ParkingPermit;
import com.parking.util.DBUtil;
public class ParkingPermitDAO{
    public ParkingPermit findParkingPermit(String permitID){
        ParkingPermit p = null;
        try(Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM PARKING_PERMIT_TBL WHERE Permit_ID = ?")){
            ps.setString(1, permitID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                p = mapRow(rs);
            }
        }
        catch(SQLException e){ 
        	e.printStackTrace(); 
        }
        return p;
    }
    public List<ParkingPermit> findPermitsByHolder(String permitHolderID){
        List<ParkingPermit> list = new ArrayList<>();
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM PARKING_PERMIT_TBL WHERE Permit_Holder_ID = ?")){
            ps.setString(1, permitHolderID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(mapRow(rs));
            }
        }
        catch(SQLException e){ 
        	e.printStackTrace();
        }
        return list;
    }
    public List<ParkingPermit> findActivePermitsByHolder(String permitHolderID){
        List<ParkingPermit> list = new ArrayList<>();
        try(Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM PARKING_PERMIT_TBL WHERE Permit_Holder_ID = ? AND Permit_Status = 'ACTIVE'")){
            ps.setString(1, permitHolderID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(mapRow(rs));
            }
        } 
        catch (SQLException e){ 
        	e.printStackTrace(); 
        	}
        return list;
    }
    public ParkingPermit findActivePermitForVehicleAndZone(String vehicleRegNo, String zoneCode){
        ParkingPermit p = null;
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM PARKING_PERMIT_TBL WHERE Vehicle_Reg_No = ? AND Zone_Code = ? AND Permit_Status = 'ACTIVE'")){
            ps.setString(1, vehicleRegNo);
            ps.setString(2, zoneCode);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                p = mapRow(rs);
            }
        } 
        catch(SQLException e){ 
        	e.printStackTrace(); 
        	}
        return p;
    }
    public boolean insertParkingPermit(ParkingPermit permit){
        try(Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO PARKING_PERMIT_TBL VALUES (?,?,?,?,?,?,?,?,?,?)")){
            ps.setString(1, permit.getPermitID());
            ps.setString(2, permit.getPermitHolderID());
            ps.setString(3, permit.getVehicleRegNo());
            ps.setString(4, permit.getVehicleType());
            ps.setString(5, permit.getZoneCode());
            ps.setDate(6, permit.getPermitStartDate());
            ps.setDate(7, permit.getPermitEndDate());
            ps.setString(8, permit.getPermitStatus());
            ps.setInt(9, permit.getViolationCount());
            ps.setBigDecimal(10, permit.getOutstandingFineAmount());
            return ps.executeUpdate() > 0;
        }
        catch(SQLException e) { 
        	return false; 
        	}
    }
    public boolean updatePermitDatesAndStatus(String permitID, java.sql.Date newStartDate, java.sql.Date newEndDate, String newStatus){
        try(Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE PARKING_PERMIT_TBL SET Permit_Start_Date=?, Permit_End_Date=?, Permit_Status=? WHERE Permit_ID=?")){
            ps.setDate(1, newStartDate);
            ps.setDate(2, newEndDate);
            ps.setString(3, newStatus);
            ps.setString(4, permitID);
            return ps.executeUpdate() > 0;
        } 
        catch(SQLException e){ 
        	return false; 
        }
    }
    public boolean updateViolationAndFine(String permitID, int newViolationCount, BigDecimal newOutstandingFine){
        try(Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE PARKING_PERMIT_TBL SET Violation_Count=?, Outstanding_Fine=? WHERE Permit_ID=?")) {
            ps.setInt(1, newViolationCount);
            ps.setBigDecimal(2, newOutstandingFine);
            ps.setString(3, permitID);
            return ps.executeUpdate() > 0;
        } 
        catch(SQLException e){ 
        	return false; 
        	}
    }
    public BigDecimal calculateTotalOutstandingFineForHolder(String permitHolderID){
        BigDecimal total = BigDecimal.ZERO;
        try(Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement("SELECT SUM(Outstanding_Fine) FROM PARKING_PERMIT_TBL WHERE Permit_Holder_ID = ?")) {
            ps.setString(1, permitHolderID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                total = rs.getBigDecimal(1);
            }
        }
        catch(SQLException e){ 
        	e.printStackTrace(); 
        	}
        return total == null ? BigDecimal.ZERO : total;
    }
    private ParkingPermit mapRow(ResultSet rs) throws SQLException{
        ParkingPermit p = new ParkingPermit();
        p.setPermitID(rs.getString(1));
        p.setPermitHolderID(rs.getString(2));
        p.setVehicleRegNo(rs.getString(3));
        p.setVehicleType(rs.getString(4));
        p.setZoneCode(rs.getString(5));
        p.setPermitStartDate(rs.getDate(6));
        p.setPermitEndDate(rs.getDate(7));
        p.setPermitStatus(rs.getString(8));
        p.setViolationCount(rs.getInt(9));
        p.setOutstandingFineAmount(rs.getBigDecimal(10));
        return p;
    }
}