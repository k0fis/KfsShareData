package kfs.sharedata.dao;

import java.util.List;
import kfs.sharedata.domain.ExchangePartner;
import kfs.springutils.BaseDao;

/**
 *
 * @author pavedrim
 */
public interface ExchangePartnerDao extends BaseDao<ExchangePartner, String>{
    
    List<ExchangePartner> loadAll();
    List<ExchangePartner> loadActive(boolean active);
}
