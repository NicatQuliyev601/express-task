package com.nicat.task.expresstask.response;

import com.nicat.task.expresstask.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private Status status;
    private String error;
    private Object data;

    public Response() {
    }

    public Response(Status status) {
        this.status = status;
    }

    public Response(Status status, Object data) {
        this.status = status;
        this.data = data;
    }
}
