package org.pzz.common.exception;

/**
 * @author  PZJ
 * @create  2021/4/30 16:01
 * @email   wuchzh0@gmail.com
 * @desc    全局异常捕获类
 **/
public class FazException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FazException(String message){
        super(message);
    }

    public FazException(Throwable cause)
    {
        super(cause);
    }

    public FazException(String message,Throwable cause)
    {
        super(message,cause);
    }
}
