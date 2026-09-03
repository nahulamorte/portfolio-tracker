package org.portfoliotracker.portfolio.exception;

public class AssetNotFoundException extends RuntimeException{
    public AssetNotFoundException(String message){
        super(message);
    }
}
