package kfs.sharedata.dao.jpa;

import java.util.List;
import kfs.sharedata.dao.ExchangeEventHistDao;
import kfs.sharedata.domain.ExchangeEvent;
import kfs.sharedata.domain.ExchangeEventHist;
import kfs.springutils.BaseDaoJpa;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pavedrim
 */
@Repository
public class ExchangeEventHistDaoJpa extends BaseDaoJpa<ExchangeEventHist, String> implements ExchangeEventHistDao {

    @Override
    protected Class<ExchangeEventHist> getDataClass() {
        return ExchangeEventHist.class;
    }

    @Override
    protected String getId(ExchangeEventHist t) {
        return t.getId();
    }

    @Override
    public List<ExchangeEventHist> loadByEvent(ExchangeEvent event){
        return em.createQuery("SELECT h FROM ExchangeEventHist h WHERE h.exchangeEvent = :event ORDER BY h.historyDate DESC")
                .setParameter("event", event)
                .getResultList();
    }
}
