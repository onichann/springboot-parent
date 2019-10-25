package com.wt.springboot.oldweb.ftp;

/**
 * Created by admin on 2016/4/8.
 */
public class DataGrabException extends Exception {
    public DataGrabException() {
    }

    public DataGrabException(String s) {
        super(s);
    }

    public DataGrabException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DataGrabException(Throwable throwable) {
        super(throwable);
    }

    public DataGrabException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
