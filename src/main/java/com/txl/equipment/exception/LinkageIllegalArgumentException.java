package com.txl.equipment.exception;

/**
 * Created by TangXiangLin on 2023-02-15 14:32
 * 非法参数异常
 */
public class LinkageIllegalArgumentException extends LinkageException{

    private static final long serialVersionUID = 1L;

    public LinkageIllegalArgumentException(String message) {
        super(message);
    }

    public LinkageIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

}
