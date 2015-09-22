package kfs.sharedata.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import kfs.kfsusers.domain.KfsUser;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author pavedrim
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"client_id","user_login"}))
public class ExchangeUserView {

    @Id
    @Column(length = 40)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    
    @OneToOne
    private ExchangeEvent client;
    
    @OneToOne
    private KfsUser user;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ExchangeEvent getClient() {
        return client;
    }

    public void setClient(ExchangeEvent client) {
        this.client = client;
    }

    public KfsUser getUser() {
        return user;
    }

    public void setUser(KfsUser user) {
        this.user = user;
    }

}
