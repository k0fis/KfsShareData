package kfs.sharedata.service.impl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import kfs.kfsusers.domain.KfsUser;
import kfs.sharedata.dao.*;
import kfs.sharedata.domain.*;
import kfs.sharedata.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author pavedrim
 */
@Service
@Transactional
public class ShareDataServiceImpl implements ExchangeService {

    @Autowired
    private ExchangePartnerDao partnerDao;
    @Autowired
    private ExchangeEventHistDao eventHistDao;
    @Autowired
    private ExchangeEventDao eventDao;
    @Autowired
    private ExchangeUserViewDao seenDao;

    @Override
    public void partnerSave(ExchangePartner partner) {
        partner.setLastSave(new Timestamp(new Date().getTime()));
        if (partner.getId() == null) {
            partnerDao.insert(partner);
        } else {
            partnerDao.update(partner);
        }
    }

    @Override
    public void partnerDelete(ExchangePartner partner) {
        for (ExchangeEvent ee : eventLoad(partner)) {
            eventDelete(ee);
        }
        partnerDao.delete(partner);
    }

    @Override
    public ExchangePartner partnerFind(String pid) {
        return partnerDao.findById(pid);
    }

    @Override
    public List<ExchangePartner> partnerLoadAll() {
        return partnerDao.loadAll();
    }

    @Override
    public List<ExchangePartner> partnerLoadActive() {
        return partnerDao.loadActive(true);
    }

    @Override
    public List<ExchangePartner> partnerLoadInactive() {
        return partnerDao.loadActive(false);
    }

    @Override
    public void eventHistAdd(ExchangeEventHist hist, KfsUser user) {
        hist.setHistoryDate(new Timestamp(new Date().getTime()));
        String s = hist.getNote();
        if (s.length() > ExchangeEvent.lastStatusLength) {
            s = s.substring(0, ExchangeEvent.lastStatusLength);
        }
        hist.getExchangeEvent().setLastStatus(s);
        hist.getExchangeEvent().setLastSave(new Timestamp(new Date().getTime()));
        eventDao.update(hist.getExchangeEvent());
        eventHistDao.insert(hist);
        seenSetOther(hist.getExchangeEvent(), user);
    }

    @Override
    public List<ExchangeEventHist> eventHistLoad(ExchangeEvent event) {
        if ((event == null) || (event.getId() == null)) {
            return Arrays.<ExchangeEventHist>asList();
        }
        return eventHistDao.loadByEvent(event);
    }

    @Override
    public void eventSave(ExchangeEvent event, KfsUser user) {
        event.setLastSave(new Timestamp(new Date().getTime()));
        if (event.getId() == null) {
            String s = event.getDescription();
            if (s.length() > ExchangeEvent.lastStatusLength) {
                s = s.substring(0, ExchangeEvent.lastStatusLength);
            }
            event.setLastStatus(s);
            ExchangeEventHist newHist = new ExchangeEventHist();
            newHist.setUserName(event.getCreateUser().getUserName());
            newHist.setExchangeEvent(event);
            newHist.setNote(event.getDescription());
            newHist.setHistoryDate(new Timestamp(new Date().getTime()));
            event.setInsertDate(event.getLastSave());
            eventDao.insert(event);
            eventHistDao.insert(newHist);
        } else {
            eventDao.update(event);
        }
        seenSetOther(event, user);
    }

    @Override
    public void eventDelete(ExchangeEvent event) {
        for (ExchangeEventHist hi : eventHistDao.loadByEvent(event)) {
            eventHistDao.delete(hi);
        }
        eventDao.delete(event);
    }

    @Override
    public List<ExchangeEvent> eventLoad(ExchangePartner partner) {
        return eventDao.loadByPartner(partner);
    }

    @Override
    public List<ExchangeEvent> eventLoad(KfsUser partner) {
        return eventDao.loadByPartnerUser(partner);
    }

    @Override
    public ExchangeEvent eventLoad(Long eeId) {
        return eventDao.findById(eeId);
    }

    @Override
    public boolean seenIs(ExchangeEvent event, KfsUser user) {
        ExchangeUserView data = seenDao.load(event, user);
        return (data == null);
    }
    
    @Override
    public void seenDrop(ExchangeEvent event, KfsUser user) {
        if (event.getId() == null) {
            return;
        }
        ExchangeUserView data = seenDao.load(event, user);
        if (data != null) {
            seenDao.delete(data);
        }
    }

    @Override
    public void seenSetOther(ExchangeEvent event, KfsUser userOther) {
        KfsUser user = null;
        if (!event.getCreateUser().getLogin().equals(userOther.getLogin())) {
            user = event.getCreateUser();
        } else if (!event.getPartner().getPartner().getLogin().equals(userOther.getLogin())) {
            user = event.getPartner().getPartner();
        }
        if (user == null) {
            throw new ShareDataSoftException("Cannot find other user for ee: " + event.getId() + ", u: " + userOther.getLogin());
        }
        ExchangeUserView data = seenDao.load(event, user);
        if (data == null) {
            data = new ExchangeUserView();
            data.setUser(user);
            data.setClient(event);
            seenDao.insert(data);
        }
    }
}
