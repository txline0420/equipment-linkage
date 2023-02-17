package com.txl.equipment.exception;

/**
 * Created by TangXiangLin on 2023-02-15 14:34
 * 联动异常
 */
public class LinkageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LinkageException(String message, Throwable cause) {
        super(message, cause);
    }

    public LinkageException(String message) {
        super(message);
    }

}
