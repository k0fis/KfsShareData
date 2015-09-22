package kfs.sharedata.service.impl;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kfs.kfsusers.domain.KfsUser;
import kfs.kfsusers.service.KfsUsersService;
import kfs.sharedata.domain.*;
import kfs.sharedata.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pavedrim
 */
@Service
public class SDmobileControllerImpl implements SDmobileController {

    private final Gson gs = new Gson();
    private String prefixListPartners = "/pl/";
    private String prefixListClients = "/cl/";
    private String prefixListNotes = "/cn/";
    private String prefixNoteAdd = "/an/";
    private String prefixClientAdd = "/ac/";
    private String prefixClientView = "/vc/";
    private String formatDate = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat sdf = new SimpleDateFormat(formatDate);

    @Autowired
    KfsUsersService kfsUsers;

    @Autowired
    ExchangeService ssdService;

    @Override
    public String getPrefixListClients() {
        return prefixListClients;
    }

    @Override
    public String getPrefixListNotes() {
        return prefixListNotes;
    }

    @Override
    public String getPrefixNoteAdd() {
        return prefixNoteAdd;
    }

    public void setPrefixListClients(String prefixListClients) {
        this.prefixListClients = prefixListClients;
    }

    public void setPrefixListNotes(String prefixListNotes) {
        this.prefixListNotes = prefixListNotes;
    }

    public void setPrefixNoteAdd(String prefixNoteAdd) {
        this.prefixNoteAdd = prefixNoteAdd;
    }

    public void setPrefixListPartners(String prefixListPartners) {
        this.prefixListPartners = prefixListPartners;
    }

    @Override
    public void logError(String err, Throwable ex) {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, err, ex);
    }

    @Override
    public SDWebRequest createSDWebRequest(HttpServletRequest req, HttpServletResponse resp) {
        return new SDWebRequestImpl(req, resp, gs, this);
    }

    @Override
    public String getPrefixListPartners() {
        return prefixListPartners;
    }

    @Override
    public String getPrefixClientAdd() {
        return prefixClientAdd;
    }

    public void setPrefixClientAdd(String prefixClientAdd) {
        this.prefixClientAdd = prefixClientAdd;
    }

    @Override
    public String getPrefixClientView() {
        return prefixClientView;
    }

    public void setPrefixClientView(String prefixClientView) {
        this.prefixClientView = prefixClientView;
    }

    @Override
    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
        sdf = new SimpleDateFormat(formatDate);
    }

    public String formatDate(Date d) {
        return sdf.format(d);
    }

    @Override
    public ExpPartner[] getExpPartnerArray(List<ExchangePartner> lst) {
        if ((lst == null) || (lst.isEmpty())) {
            return new ExpPartner[0];
        }
        ExpPartner ret[] = new ExpPartner[lst.size()];
        int inx = 0;
        for (ExchangePartner eh : lst) {
            ret[inx] = new ExpPartner();
            ret[inx].setId(eh.getId());
            ret[inx].setName(eh.getName());
            inx++;
        }
        return ret;
    }

    @Override
    public ExpClient[] getExpClientArray(List<ExchangeEvent> lst, KfsUser user) {
        if ((lst == null) || (lst.isEmpty())) {
            return new ExpClient[0];
        }
        ExpClient ret[] = new ExpClient[lst.size()];
        int inx = 0;
        for (ExchangeEvent ee : lst) {
            ret[inx] = new ExpClient();
            ret[inx].setId(ee.getId());
            ret[inx].setInsertDate(formatDate(ee.getInsertDate()));
            ret[inx].setCreateUser(ee.getCreateUser().getUserName());
            ret[inx].setLastStatus(ee.getLastStatus());
            ret[inx].setClientName(ee.getClientName());
            ret[inx].setClientSureName(ee.getClientSureName());
            ret[inx].setClientAddress(ee.getClientAddress());
            ret[inx].setClientEmail(ee.getClientEmail());
            ret[inx].setClientPhone(ee.getClientPhone());
            ret[inx].setDescription(ee.getDescription());
            ret[inx].setLastSave(formatDate(ee.getLastSave()));
            ret[inx].setSeen(ssdService.seenIs(ee, user));
            inx++;
        }
        return ret;
    }

    @Override
    public ExpNotes[] getExpClientNotesArray(List<ExchangeEventHist> lst) {
        if ((lst == null) || (lst.isEmpty())) {
            return new ExpNotes[0];
        }
        ExpNotes ret[] = new ExpNotes[lst.size()];
        int inx = 0;
        for (ExchangeEventHist eh : lst) {
            ret[inx] = new ExpNotes();
            ret[inx].setNote(eh.getNote());
            ret[inx].setUserName(eh.getUserName());
            ret[inx].setHistoryDate(formatDate(eh.getHistoryDate()));
            ret[inx].setExchangeEvent(eh.getExchangeEvent().getId());
            inx++;
        }
        return ret;
    }

    @Override
    public void doListClients(SDWebRequest req) {
        KfsUser user = kfsUsers.userLoad(req.getUserLogin());
        if (user == null) {
            throw new ShareDataException("Cannot find user: " + user);
        }
        req.send(getExpClientArray(ssdService.eventLoad(user), user));
    }

    @Override
    public void doListNotes(SDWebRequest req) {
        KfsUser user = kfsUsers.userLoad(req.getUserLogin());
        if (user == null) {
            throw new ShareDataException("Cannot find user: " + user);
        }
        if ((req.getClientId() == null) || (req.getClientId() <= 0)) {
            throw new ShareDataException("Cannot find client: " + req.getClientId());
        }
        ExchangeEvent ee = ssdService.eventLoad(req.getClientId());
        ssdService.seenDrop(ee, user);
        req.send(getExpClientNotesArray(ssdService.eventHistLoad(ee)));
    }

    @Override
    public void doNoteAdd(SDWebRequest request) {
        KfsUser user = kfsUsers.userLoad(request.getUserLogin());
        if (user == null) {
            throw new ShareDataException("Cannot find user: " + user);
        }
        ExpNotes o = request.getParameter("newNote", ExpNotes.class);
        if ((o == null)
                || (o.getNote().length() <= 0)
                || (o.getExchangeEvent() == null)
                || (o.getExchangeEvent() <= 0)) {
            throw new ShareDataException("Cannot add empty note");
        } else {
            ExchangeEvent ee = ssdService.eventLoad(o.getExchangeEvent());
            if (ee == null) {
                throw new ShareDataException("Cannot find client: " + o.getExchangeEvent());
            } else {
                ExchangeEventHist eh = new ExchangeEventHist();
                eh.setNote(o.getNote());
                eh.setUserName(request.getUserLogin());
                eh.setExchangeEvent(ee);
                ssdService.eventHistAdd(eh, user);
                request.send("danke");
            }
        }
    }

    @Override
    public void doClientAdd(SDWebRequest req) {
        KfsUser user = kfsUsers.userLoad(req.getUserLogin());
        if (user == null) {
            throw new ShareDataException("Cannot find user: " + user);
        }
        ExpClient o = req.getParameter("newClient", ExpClient.class);
        if ((o != null)
                && (o.getClientName().length() + o.getClientSureName().length() != 0)
                && (o.getClientEmail().length() + o.getClientPhone().length() != 0)) {

            ExchangePartner ep = ssdService.partnerFind(o.getPartnerId());
            if (ep == null) {
                throw new ShareDataException("Cannot find partner: " + o.getPartnerId());
            }
            ExchangeEvent ee = new ExchangeEvent();
            ee.setCreateUser(user);
            ee.setClientAddress(o.getClientAddress());
            ee.setClientEmail(o.getClientEmail());
            ee.setClientName(o.getClientName());
            ee.setClientPhone(o.getClientPhone());
            ee.setClientSureName(o.getClientSureName());
            ee.setDescription(o.getDescription());
            ee.setPartner(ep);
            ssdService.eventSave(ee, user);
            req.send("danke");

        } else {
            throw new ShareDataException("Cannot add empty client");
        }
    }

    @Override
    public void doListPartners(SDWebRequest req) {
        req.send(getExpPartnerArray(ssdService.partnerLoadActive()));
    }

    @Override
    public void doClientView(SDWebRequest param) {
        KfsUser user = kfsUsers.userLoad(param.getUserLogin());
        if (user == null) {
            throw new ShareDataException("Cannot find user: " + user);
        }
        ExchangeEvent ee = ssdService.eventLoad(param.getClientId());
        if (ee == null) {
            throw new ShareDataException("Cannot find client: " + param.getClientId());
        }
        ssdService.seenDrop(ee, user);
    }

}
