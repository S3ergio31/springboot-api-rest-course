package com.springbootapirestcourse.users.model.response;

import lombok.Data;

@Data
public class OperationStatusModel {
    private String status;
    private String operation;

    public OperationStatusModel(String status, String operation) {
        this.status = status;
        this.operation = operation;
    }
}
