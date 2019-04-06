package com.wt.springboot.excel;

public class ValidResponse {
    private boolean isSuccess;
    private String errorMessage;

    public boolean isSuccess() {
        return isSuccess;
    }

    public ValidResponse setSuccess(boolean success) {
        isSuccess = success;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ValidResponse setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
