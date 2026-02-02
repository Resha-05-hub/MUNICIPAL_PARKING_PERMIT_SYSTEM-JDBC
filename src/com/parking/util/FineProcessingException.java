package com.parking.util;
public class FineProcessingException extends Exception{
    public String toString(){
        return "Fine Processing Error: Fine-related update could not be applied safely.";
    }
}
