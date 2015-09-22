package kfs.sharedata.service.impl;

/**
 *
 * @author pavedrim
 */
public class ShareDataSoftException extends RuntimeException {
    public ShareDataSoftException(String msg) {
        super(msg);
    }
    public ShareDataSoftException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
