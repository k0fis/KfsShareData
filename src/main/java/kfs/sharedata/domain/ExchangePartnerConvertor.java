package kfs.sharedata.domain;

import kfs.kfsvaalib.convertors.KfsObjectProperties;

/**
 *
 * @author pavedrim
 */
public class ExchangePartnerConvertor extends KfsObjectProperties<ExchangePartner> {

    public ExchangePartnerConvertor() {
        super(ExchangePartner.class, " ", "getName", "getPhone");
    }
}
