package vending;

public class NotSufficientBalanceForChangeException extends RuntimeException {

    private String message;

    public NotSufficientBalanceForChangeException(String string)
    {
        this.message = string;
    }

    @Override
    public String getMessage(){ return message;
    }
}

