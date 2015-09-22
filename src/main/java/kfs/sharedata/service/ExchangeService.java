package kfs.sharedata.service;

import java.util.List;
import kfs.kfsusers.domain.KfsUser;
import kfs.sharedata.domain.ExchangeEvent;
import kfs.sharedata.domain.ExchangeEventHist;
import kfs.sharedata.domain.ExchangePartner;

/**
 *
 * @author pavedrim
 */
public interface ExchangeService {
    
    void partnerSave(ExchangePartner partner);
    void partnerDelete(ExchangePartner partner);
    ExchangePartner partnerFind(String pid);
    List<ExchangePartner> partnerLoadAll();
    List<ExchangePartner> partnerLoadActive();
    List<ExchangePartner> partnerLoadInactive();
    
    void eventHistAdd(ExchangeEventHist hist, KfsUser user);
    List<ExchangeEventHist> eventHistLoad(ExchangeEvent event);
    
    void eventSave(ExchangeEvent event, KfsUser user);
    void eventDelete(ExchangeEvent event);
    List<ExchangeEvent> eventLoad(ExchangePartner partner);
    List<ExchangeEvent> eventLoad(KfsUser partner);
    ExchangeEvent eventLoad(Long eeId);

    
    boolean seenIs(ExchangeEvent event, KfsUser user);
    void seenSetOther(ExchangeEvent event, KfsUser user);
    void seenDrop(ExchangeEvent event, KfsUser user);
}
