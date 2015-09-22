package kfs.sharedata.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kfs.kfsusers.domain.KfsUser;
import kfs.sharedata.domain.ExchangeEvent;
import kfs.sharedata.domain.ExchangeEventHist;
import kfs.sharedata.domain.ExchangePartner;
import kfs.sharedata.domain.ExpClient;
import kfs.sharedata.domain.ExpNotes;
import kfs.sharedata.domain.ExpPartner;

/**
 *
 * @author pavedrim
 */
public interface SDmobileController {

    String getPrefixListClients();
    String getPrefixListNotes();
    String getPrefixNoteAdd();
    String getPrefixClientAdd();
    String getPrefixClientView();
    String getPrefixListPartners();
    String getFormatDate();
    
    void logError(String err, Throwable ex);
    SDWebRequest createSDWebRequest(HttpServletRequest req, HttpServletResponse resp);
    
    ExpPartner[]getExpPartnerArray(List<ExchangePartner> lst);
    ExpClient[]getExpClientArray(List<ExchangeEvent> lst, KfsUser user);
    ExpNotes[]getExpClientNotesArray(List<ExchangeEventHist> lst);
    
    void doListPartners(SDWebRequest req);
    void doListClients(SDWebRequest req);
    void doListNotes(SDWebRequest req);
    void doClientAdd(SDWebRequest req);
    void doNoteAdd(SDWebRequest req);
    void doClientView(SDWebRequest param);

}
