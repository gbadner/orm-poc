package com.redhat.bz.das;

import com.redhat.bz.DistributionIteration;
import com.redhat.bz.DistributorAccount;
import com.redhat.bz.Partner;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Logger;

@Stateless
public class PartnerService implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(PartnerService.class.getName());

    // ======================================
    // =             Attributes             =
    // ======================================

    private static final Integer PID_NUMBER_COUNT = Integer.valueOf(6);

    @Inject
    private PartnerDAS partnerDAS;

    public Partner createNewPartner(Partner x) {
        DistributorAccount a = new DistributorAccount();
        a.setPartner(x);
        DistributionIteration distributionIteration = new DistributionIteration();
        distributionIteration.setCurrent(Boolean.TRUE);
        a.addDistributionIteration(distributionIteration);
        return partnerDAS.persist(x);
    }
}
