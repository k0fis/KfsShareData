package kfs.sharedata.dao;

import kfs.kfsusers.domain.KfsUser;
import kfs.sharedata.domain.ExchangeEvent;
import kfs.sharedata.domain.ExchangeUserView;
import kfs.springutils.BaseDao;

/**
 *
 * @author pavedrim
 */
public interface ExchangeUserViewDao extends BaseDao<ExchangeUserView, String>{
    
    ExchangeUserView load(ExchangeEvent event, KfsUser user);
}
