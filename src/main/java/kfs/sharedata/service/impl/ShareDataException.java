package kfs.sharedata.service.impl;

/**
 *
 * @author pavedrim
 */
public class ShareDataException extends RuntimeException {
    public ShareDataException(String msg) {
        super(msg);
    }
    public ShareDataException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
