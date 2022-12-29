package com.springbootapirestcourse.users.model.response;

public class OperationStatusModel {
    private String status;
    private String operation;

    public OperationStatusModel(String status, String operation) {
        this.status = status;
        this.operation = operation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
