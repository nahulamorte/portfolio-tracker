package org.portfoliotracker.portfolio.exception;

public class PortfolioNotFoundException extends RuntimeException{
    public PortfolioNotFoundException(String message){
        super(message);
    }
}
