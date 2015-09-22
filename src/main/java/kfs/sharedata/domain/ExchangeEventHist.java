package kfs.sharedata.domain;

import com.vaadin.ui.TextArea;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kfs.kfsusers.domain.KfsUser;
import kfs.kfsvaalib.kfsForm.KfsField;
import kfs.kfsvaalib.kfsForm.KfsMField;
import kfs.kfsvaalib.kfsTable.KfsTablePos;
import kfs.kfsvaalib.kfsTable.Pos;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author pavedrim
 */
@Entity
public class ExchangeEventHist {

    @Id
    @Column(length = 40)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @Column(length = 1024, updatable = false, nullable = false)
    @Size(max = 1024)
    @KfsMField({
        @KfsField(pos = 100, name = "ExchEventDlg", fieldClass = TextArea.class, isRequired = true),})
    @KfsTablePos({@Pos(value = 10, name = "ExchEventDlg")})
    private String note;

    @NotNull
    @Column(length = KfsUser.userNameLength, updatable = false, nullable = false)
    @Size(max = KfsUser.userNameLength)
    @KfsTablePos({@Pos(value = 20, name = "ExchEventDlg")})
    private String userName;

    @NotNull
    @Column(updatable = false, nullable = false)
    @KfsTablePos({@Pos(value = 30, name = "ExchEventDlg")})
    private Timestamp historyDate;

    @OneToOne
    @JoinColumn(nullable = false, updatable = false)
    private ExchangeEvent exchangeEvent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Timestamp getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(Timestamp historyDate) {
        this.historyDate = historyDate;
    }

    public ExchangeEvent getExchangeEvent() {
        return exchangeEvent;
    }

    public void setExchangeEvent(ExchangeEvent exchangeEvent) {
        this.exchangeEvent = exchangeEvent;
    }



}
