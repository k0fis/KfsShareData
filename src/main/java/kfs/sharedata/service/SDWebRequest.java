package kfs.sharedata.service;

/**
 *
 * @author pavedrim
 */
public interface SDWebRequest {

    String getUserLogin();
    String getPathInfo();
    Long getClientId();
    boolean isPartnerList();
    boolean isClientList();
    boolean isClientView();
    boolean isNotesList();
    boolean isNoteAdd();
    boolean isClientAdd();
    
    public <T> T getParameter(String name, Class<T> cls);
    public void send(Object obj);
    public void sendError(String message);
    
}
