package kfs.sharedata.dao.jpa;

import javax.persistence.NoResultException;
import kfs.kfsusers.domain.KfsUser;
import kfs.sharedata.dao.ExchangeUserViewDao;
import kfs.sharedata.domain.ExchangeEvent;
import kfs.sharedata.domain.ExchangeUserView;
import kfs.springutils.BaseDaoJpa;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pavedrim
 */
@Repository
public class ExchangeUserViewDaoJpa extends BaseDaoJpa<ExchangeUserView, String> implements ExchangeUserViewDao{

    @Override
    protected Class<ExchangeUserView> getDataClass() {
        return ExchangeUserView.class;
    }

    @Override
    protected String getId(ExchangeUserView data) {
        return data.getId();
    }

    @Override
    public ExchangeUserView load(ExchangeEvent event, KfsUser user) {
        try {
        return (ExchangeUserView)em.createQuery("SELECT a FROM ExchangeUserView a WHERE a.client = :event AND a.user = :user")
                .setParameter("event", event)
                .setParameter("user", user)
                .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
