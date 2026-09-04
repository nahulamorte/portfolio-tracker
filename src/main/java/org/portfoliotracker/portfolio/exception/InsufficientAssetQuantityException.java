package org.portfoliotracker.portfolio.exception;

public class InsufficientAssetQuantityException extends RuntimeException{
    public InsufficientAssetQuantityException(String message){
        super(message);
    }
}
