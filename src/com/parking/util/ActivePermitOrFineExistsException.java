package com.parking.util;
public class ActivePermitOrFineExistsException extends Exception{
	public String toString(){
	    return "Active Permit or Outstanding Fine exists. Please clear all permits and fines before removal.";
	}
}