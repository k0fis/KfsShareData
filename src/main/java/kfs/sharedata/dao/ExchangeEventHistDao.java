package kfs.sharedata.dao;

import java.util.List;
import kfs.sharedata.domain.ExchangeEvent;
import kfs.sharedata.domain.ExchangeEventHist;
import kfs.springutils.BaseDao;

/**
 *
 * @author pavedrim
 */
public interface ExchangeEventHistDao extends BaseDao<ExchangeEventHist, String> {
    
    List<ExchangeEventHist> loadByEvent(ExchangeEvent event);
}
