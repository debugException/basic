package com.xh.basic.exception;

/**
 * @author szq
 * @Package com.xh.basic.exception
 * @Description: to do ...
 * @date 2018/4/2713:55
 */
public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
