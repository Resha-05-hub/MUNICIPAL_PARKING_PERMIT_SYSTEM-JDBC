package com.parking.dao;
import java.sql.*;
import java.util.*;
import com.parking.bean.PermitHolder;
import com.parking.util.DBUtil;
public class PermitHolderDAO{
    public PermitHolder findPermitHolder(String permitHolderID){
        PermitHolder holder = null;
        try(Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM PERMIT_HOLDER_TBL WHERE Permit_Holder_ID = ?")){
            ps.setString(1, permitHolderID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                holder = new PermitHolder();
                holder.setPermitHolderID(rs.getString(1));
                holder.setFullName(rs.getString(2));
                holder.setHolderType(rs.getString(3));
                holder.setAddressLine1(rs.getString(4));
                holder.setAddressLine2(rs.getString(5));
                holder.setWardCode(rs.getString(6));
                holder.setMobile(rs.getString(7));
                holder.setEmail(rs.getString(8));
                holder.setStatus(rs.getString(9));
            }
        } 
        catch(SQLException e){ 
        	e.printStackTrace(); 
        	}
        return holder;
    }
    public List<PermitHolder> viewAllPermitHolders(){
        List<PermitHolder> list = new ArrayList<>();
        try(Connection con = DBUtil.getDBConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM PERMIT_HOLDER_TBL")){
            while(rs.next()){
                PermitHolder h = new PermitHolder();
                h.setPermitHolderID(rs.getString(1));
                h.setFullName(rs.getString(2));
                h.setHolderType(rs.getString(3));
                h.setAddressLine1(rs.getString(4));
                h.setAddressLine2(rs.getString(5));
                h.setWardCode(rs.getString(6));
                h.setMobile(rs.getString(7));
                h.setEmail(rs.getString(8));
                h.setStatus(rs.getString(9));
                list.add(h);
            }
        }
        catch(SQLException e){ 
        	e.printStackTrace(); 
        	}
        return list;
    }
    public boolean insertPermitHolder(PermitHolder holder){
        try(Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO PERMIT_HOLDER_TBL VALUES (?,?,?,?,?,?,?,?,?)")){
            ps.setString(1, holder.getPermitHolderID());
            ps.setString(2, holder.getFullName());
            ps.setString(3, holder.getHolderType());
            ps.setString(4, holder.getAddressLine1());
            ps.setString(5, holder.getAddressLine2());
            ps.setString(6, holder.getWardCode());
            ps.setString(7, holder.getMobile());
            ps.setString(8, holder.getEmail());
            ps.setString(9, holder.getStatus());
            return ps.executeUpdate() > 0;
        } 
        catch(SQLException e){ 
        	return false;
        }
    }
    public boolean updatePermitHolderStatus(String permitHolderID, String status){
        try(Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE PERMIT_HOLDER_TBL SET Status = ? WHERE Permit_Holder_ID = ?")){
            ps.setString(1, status);
            ps.setString(2, permitHolderID);
            return ps.executeUpdate() > 0;
        } 
        catch(SQLException e){ 
        	return false; 
        }
    }
    public boolean deletePermitHolder(String permitHolderID){
        try(Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM PERMIT_HOLDER_TBL WHERE Permit_Holder_ID = ?")){
            ps.setString(1, permitHolderID);
            return ps.executeUpdate() > 0;
        }
        catch(SQLException e){ 
        	return false; 
        }
    }
}