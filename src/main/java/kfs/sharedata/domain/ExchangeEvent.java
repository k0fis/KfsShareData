package kfs.sharedata.domain;

import com.vaadin.ui.TextArea;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import kfs.kfsvaalib.kfsForm.KfsField;
import kfs.kfsvaalib.kfsForm.KfsMField;
import kfs.kfsvaalib.kfsTable.KfsTablePos;
import kfs.kfsvaalib.kfsTable.Pos;
import kfs.kfsusers.domain.KfsUser;

/**
 *
 * @author pavedrim
 */
@Entity
public class ExchangeEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ExchangeEventGen")
    @SequenceGenerator(name = "ExchangeEventGen", sequenceName = "ExchangeEventSeq", allocationSize = 1)    
    @KfsMField(value = {
        @KfsField(name = "ExchEventDlg2", pos = 0, readOnly = true),
        @KfsField(name = "ExchEventDlg3", pos = 0, readOnly = true)
    })
    @KfsTablePos({
        @Pos(value = 0, name = "ExchEventList", genName = "id0"),
        @Pos(value = 0, name = "ExchPartnerEventsList", genName = "id0"),})
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    @KfsTablePos({
        @Pos(value = 98, name = "ExchEventList", converter = ExchangePartnerConvertor.class),})
    private ExchangePartner partner;

    @Column(nullable = false, updatable = false)
    @KfsMField(value = {
        @KfsField(name = "ExchEventDlg2", pos = 20, readOnly = true),
        @KfsField(name = "ExchEventDlg3", pos = 20, readOnly = true)
    })
    @KfsTablePos({
        @Pos(value = 99, name = "ExchEventList"),
        @Pos(value = 99, name = "ExchPartnerEventsList"),})
    private Timestamp insertDate;

    @OneToOne
    @JoinColumn(nullable = false, updatable = false)
    @KfsTablePos({
        @Pos(value = 97, name = "ExchEventList")})
    private KfsUser createUser;

    public static final int lastStatusLength = 40;
    
    @Column(length = lastStatusLength)
    @KfsTablePos({
        @Pos(value = 15, name = "ExchEventList"),
        @Pos(value = 15, name = "ExchPartnerEventsList"),})
    private String lastStatus = "";

    @Size(max = 30)
    @Column(length = 30)
    @KfsMField(value = {
        @KfsField(name = "ExchEventDlg1", pos = 30),
        @KfsField(name = "ExchEventDlg2", pos = 30),
        @KfsField(name = "ExchEventDlg3", pos = 30, readOnly = true)
    })
    @KfsTablePos({
        @Pos(value = 10, name = "ExchEventList"),
        @Pos(value = 10, name = "ExchPartnerEventsList"),})
    private String clientName = "";

    @NotNull
    @Size(min = 2, max = 30)
    @Column(length = 30, nullable = false, updatable = false)
    @KfsMField(value = {
        @KfsField(name = "ExchEventDlg1", pos = 40, isRequired = true),
        @KfsField(name = "ExchEventDlg2", pos = 40, isRequired = true),
        @KfsField(name = "ExchEventDlg3", pos = 40, readOnly = true)
    })
    @KfsTablePos({
        @Pos(value = 20, name = "ExchEventList"),
        @Pos(value = 20, name = "ExchPartnerEventsList"),})
    private String clientSureName = "";

    @Size(max = 100)
    @Column(length = 100)
    @KfsMField(value = {
        @KfsField(name = "ExchEventDlg1", pos = 50),
        @KfsField(name = "ExchEventDlg2", pos = 50),
        @KfsField(name = "ExchEventDlg3", pos = 50, readOnly = true)
    })
    @KfsTablePos({
        @Pos(value = 30, name = "ExchEventList"),
        @Pos(value = 30, name = "ExchPartnerEventsList"),})
    private String clientAddress = "";

    @Pattern(regexp = "^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,6}$")
    @Size(max = 512)
    @Column(length = 512)
    @KfsMField(value = {
        @KfsField(name = "ExchEventDlg1", pos = 60, isRequired = true),
        @KfsField(name = "ExchEventDlg2", pos = 60, isRequired = true),
        @KfsField(name = "ExchEventDlg3", pos = 60, readOnly = true)
    })
    @KfsTablePos({
        @Pos(value = 40, name = "ExchEventList"),
        @Pos(value = 40, name = "ExchPartnerEventsList"),})
    private String clientEmail = "";

    @Size(max = 30)
    @Column(length = 30)
    @KfsMField(value = {
        @KfsField(name = "ExchEventDlg1", pos = 70),
        @KfsField(name = "ExchEventDlg2", pos = 70),
        @KfsField(name = "ExchEventDlg3", pos = 70, readOnly = true)
    })
    @KfsTablePos({
        @Pos(value = 50, name = "ExchEventList"),
        @Pos(value = 50, name = "ExchPartnerEventsList"),})
    private String clientPhone = "";

    @NotNull
    @Size(max = 1024)
    @Column(length = 1024)
    @KfsMField(value = {
        @KfsField(name = "ExchEventDlg1", pos = 80, isRequired = true, fieldClass = TextArea.class),
        @KfsField(name = "ExchEventDlg2", pos = 80, isRequired = true, fieldClass = TextArea.class),
        @KfsField(name = "ExchEventDlg3", pos = 80, fieldClass = TextArea.class, readOnly = true)
    })
    private String description = "";
    
    @KfsMField(value = {
        @KfsField(name = "ExchEventDlg2", pos = 100, readOnly = true),
        @KfsField(name = "ExchEventDlg3", pos = 100, readOnly = true)
    })
    @KfsTablePos({
        @Pos(value = 100, name = "ExchEventList"),
        @Pos(value = 100, name = "ExchPartnerEventsList"),})
    private Timestamp lastSave;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExchangeEvent other = (ExchangeEvent) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExchangePartner getPartner() {
        return partner;
    }

    public void setPartner(ExchangePartner partner) {
        this.partner = partner;
    }

    public Timestamp getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
    }

    public KfsUser getCreateUser() {
        return createUser;
    }

    public void setCreateUser(KfsUser createUser) {
        this.createUser = createUser;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSureName() {
        return clientSureName;
    }

    public void setClientSureName(String clientSureName) {
        this.clientSureName = clientSureName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Timestamp getLastSave() {
        return lastSave;
    }

    public void setLastSave(Timestamp lastSave) {
        this.lastSave = lastSave;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
