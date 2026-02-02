package com.parking.app;
import com.parking.bean.PermitHolder;
import com.parking.bean.ParkingPermit;
import com.parking.service.ParkingService;
import com.parking.util.ValidationException;
public class ParkingMain{
    private static ParkingService service = new ParkingService();
    public static void main(String[] args){
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.println("--- Municipal Parking Permit Console ---");
        try{
            PermitHolder h = new PermitHolder();
            h.setPermitHolderID("PH2001");
            h.setFullName("Meenakshi Rao");
            h.setHolderType("RESIDENT");
            h.setAddressLine1("Flat 5C, Sunrise Apartments");
            h.setAddressLine2("Near Central Park");
            h.setWardCode("WARD10");
            h.setMobile("9998887771");
            h.setEmail("meenakshi.rao@example.com");
            h.setStatus("ACTIVE");
            boolean ok = service.registerNewPermitHolder(h);
            System.out.println(ok ? "HOLDER REGISTERED" : "REGISTRATION FAILED");
        }catch(ValidationException e){
            System.out.println("Validation Error: " + e.toString());
        }catch(Exception e){
            System.out.println("System Error: " + e.getMessage());
        }
        try{
            ParkingPermit p = new ParkingPermit();
            p.setPermitID("PMT2025-010");
            p.setPermitHolderID("PH2001");
            p.setVehicleRegNo("TN09CD7890");
            p.setVehicleType("CAR");
            p.setZoneCode("ZONE-A");
            java.sql.Date start = new java.sql.Date(System.currentTimeMillis());
            java.sql.Date end = new java.sql.Date(System.currentTimeMillis() + 180L*24*60*60*1000);
            p.setPermitStartDate(start);
            p.setPermitEndDate(end);
            p.setPermitStatus("ACTIVE");
            p.setViolationCount(0);
            p.setOutstandingFineAmount(new java.math.BigDecimal("0.00"));
            boolean ok = service.applyForNewPermit(p);
            System.out.println(ok ? "PERMIT CREATED" : "PERMIT CREATION FAILED");
        }catch (ValidationException e){
            System.out.println("Validation Error: " + e.toString());
        }catch (Exception e){
            System.out.println("System Error: " + e.getMessage());
        }
        sc.close();
    }	
}