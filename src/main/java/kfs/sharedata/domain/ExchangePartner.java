package kfs.sharedata.domain;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextArea;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import kfs.kfsusers.domain.KfsUser;
import kfs.kfsvaalib.fields.ComboField;
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
public class ExchangePartner {

    @Id
    @Column(length=40)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @KfsTablePos(@Pos(value = 10, genName = "id0"))
    private String id;
    
    @NotNull
    @Size(min = 3, max = 40)
    @Column(length = 40, nullable = false, unique = true)  
    @KfsMField(value = @KfsField(name = "ExchPartnerDlg", pos = 20, isRequired = true))
    @KfsTablePos(@Pos(value = 20))
    private String name="";

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(length = 1024, nullable = false)
    @KfsMField(value = @KfsField(name = "ExchPartnerDlg", pos = 40, fieldClass = TextArea.class))
    @KfsTablePos(@Pos(value = 60))
    private String note="";

    @NotNull
    @Pattern(regexp = "^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,8}$")
    @Size(min = 3, max = 512)
    @Column(length = 512, nullable = false)
    @KfsMField(value = @KfsField(name = "ExchPartnerDlg", pos = 60, isRequired = true))
    @KfsTablePos(@Pos(value = 45))
    private String email="";

    @Column(length = 30)
    @Size(max = 30)
    @KfsMField(value = @KfsField(name = "ExchPartnerDlg", pos = 80))
    @KfsTablePos(@Pos(value = 30))
    private String phone="";

    @NotNull
    @Column(nullable = false)
    @KfsMField(value = @KfsField(name = "ExchPartnerDlg", pos = 90, fieldClass = CheckBox.class))
    @KfsTablePos(@Pos(value = 40))
    private boolean active = true;

    @OneToOne(optional = false)
    @KfsMField(value = @KfsField(name = "ExchPartnerDlg", pos = 90, fieldClass = ComboField.class, isRequired = true))
    @KfsTablePos(@Pos(value = 110, converter = UserConvertor.class ))
    private KfsUser partner = null;
    
    @KfsMField(value = @KfsField(name = "ExchPartnerDlg", pos = 100, readOnly = true))
    @KfsTablePos(@Pos(value = 100))
    private Timestamp lastSave;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final ExchangePartner other = (ExchangePartner) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public KfsUser getPartner() {
        return partner;
    }

    public void setPartner(KfsUser partner) {
        this.partner = partner;
    }

    public Timestamp getLastSave() {
        return lastSave;
    }

    public void setLastSave(Timestamp lastSave) {
        this.lastSave = lastSave;
    }
    

}
