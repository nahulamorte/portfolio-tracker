package org.portfoliotracker.portfolio.exception;

public class AssetTypeRequiredException extends RuntimeException{
    public AssetTypeRequiredException(String message){
        super(message);
    }
}
