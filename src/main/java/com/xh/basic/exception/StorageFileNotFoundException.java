package com.xh.basic.exception;

/**
 * @author szq
 * @Package com.xh.basic.exception
 * @Description: to do ...
 * @date 2018/4/2713:55
 */
public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
