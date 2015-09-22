package kfs.sharedata.domain;

import kfs.kfsvaalib.convertors.KfsObjectProperties;
import kfs.kfsusers.domain.KfsUser;

/**
 *
 * @author pavedrim
 */
public class UserConvertor extends KfsObjectProperties<KfsUser> {

    public UserConvertor() {
        super(KfsUser.class, " ", "getUserName");
    }
}
