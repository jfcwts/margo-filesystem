package com.weets.filesystem.exception;

public class FileSystemException extends RuntimeException{

    public FileSystemException(String message){
        super(message);
    }

    public FileSystemException(String message, Throwable throwable){
        super(message, throwable);
    }
}
