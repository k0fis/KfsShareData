package kfs.sharedata.dao.jpa;

import java.util.List;
import kfs.kfsusers.domain.KfsUser;
import kfs.sharedata.dao.ExchangeEventDao;
import kfs.sharedata.domain.ExchangeEvent;
import kfs.sharedata.domain.ExchangePartner;
import kfs.springutils.BaseDaoJpa;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pavedrim
 */
@Repository
public class ExchangeEventDaoJpa extends BaseDaoJpa<ExchangeEvent, Long> implements ExchangeEventDao{

    @Override
    protected Class<ExchangeEvent> getDataClass() {
        return ExchangeEvent.class;
    }

    @Override
    protected Long getId(ExchangeEvent t) {
        return t.getId();
    }
    
    @Override
    public List<ExchangeEvent> loadByPartner(ExchangePartner partner) {
        return em.createQuery("SELECT e FROM ExchangeEvent e WHERE e.partner = :partner ORDER BY e.lastSave DESC")
                .setParameter("partner", partner)
                .getResultList();
    }
    
    @Override
    public List<ExchangeEvent> loadByPartnerUser(KfsUser partner) {
        return em.createQuery("SELECT e FROM ExchangeEvent e WHERE e.partner in ( SELECT a FROM ExchangePartner a WHERE a.partner = :partner ) ORDER BY e.lastSave DESC")
                .setParameter("partner", partner)
                .getResultList();
        
    }
}
