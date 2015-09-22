package kfs.sharedata.dao.jpa;

import java.util.List;
import kfs.sharedata.dao.ExchangePartnerDao;
import kfs.sharedata.domain.ExchangePartner;
import kfs.springutils.BaseDaoJpa;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pavedrim
 */
@Repository
public class ExchangePartnerDaoJpa extends BaseDaoJpa<ExchangePartner, String> implements ExchangePartnerDao{

    @Override
    protected Class<ExchangePartner> getDataClass() {
        return ExchangePartner.class;
    }

    @Override
    protected String getId(ExchangePartner t) {
        return t.getId();
    }

    @Override
    public List<ExchangePartner> loadAll() {
        return em.createQuery("SELECT p FROM ExchangePartner p ORDER BY p.lastSave DESC")
                .getResultList();
    }

    @Override
    public List<ExchangePartner> loadActive(boolean active) {
        return em.createQuery("SELECT p FROM ExchangePartner p WHERE p.active = :active ORDER BY p.lastSave DESC")
                .setParameter("active", active)
                .getResultList();
    }

}
