package kfs.sharedata.domain;


/**
 *
 * @author pavedrim
 */
public class ExpNotes {
    private String note;

    private String userName;

    private String historyDate;

    private Long exchangeEvent;
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(String historyDate) {
        this.historyDate = historyDate;
    }

    public Long getExchangeEvent() {
        return exchangeEvent;
    }

    public void setExchangeEvent(Long exchangeEvent) {
        this.exchangeEvent = exchangeEvent;
    }
}
