package me.dio.cloud.service.exception;

public class NotFoundException extends BusinessException {

    private static final long sserialVersionUID = 1L;

    public NotFoundException(){
        super("Resource not found.");
    }
}
