package kfs.sharedata.dao;

import java.util.List;
import kfs.kfsusers.domain.KfsUser;
import kfs.sharedata.domain.ExchangeEvent;
import kfs.sharedata.domain.ExchangePartner;
import kfs.springutils.BaseDao;

/**
 *
 * @author pavedrim
 */
public interface ExchangeEventDao extends BaseDao<ExchangeEvent, Long>{
    
    List<ExchangeEvent> loadByPartner(ExchangePartner partner);

    List<ExchangeEvent> loadByPartnerUser(KfsUser partner);
}
