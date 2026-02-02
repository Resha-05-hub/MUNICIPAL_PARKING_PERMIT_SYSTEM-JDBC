package com.parking.util;
public class ValidationException extends Exception{
	public String toString(){
	    return "Validation Error: Invalid input provided.";
	}
}