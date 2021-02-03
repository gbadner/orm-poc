package com.redhat.bz;


import com.redhat.bz.das.PartnerDAS;
import com.redhat.bz.das.PartnerService;

import com.redhat.bz.utils.AbstractIT;
import com.redhat.bz.utils.OrmDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.logging.Logger;


/**
 * Unit test for simple App.
 */
@RunWith(Arquillian.class)
public class PartnerServiceIT extends AbstractIT {
    private static final Logger LOGGER = Logger.getLogger(PartnerServiceIT.class.getName());

    @Inject
    private PartnerService partnerService;

    @Inject
    private PartnerDAS partnerDAS;

    @Deployment
    public static WebArchive deployment() {
        WebArchive war = OrmDeployment.deployment("test.war", false);
        return war;
    }

    @Test
    public void shouldTest() throws Exception {

        Partner x = new PartnerDataFactory().createPartner();

        DistributorAccount a = new DistributorAccount();
        a.setPartner(x);

        DistributionIteration b = new DistributionIteration();
        b.setCurrent(Boolean.TRUE);
        a.addDistributionIteration(b);

        this.partnerDAS.persist(x);

        DistributorTransaction c = new DistributorTransaction();
        b = a.getCurrentDistributionIteration().get();

        b.addDistributorTransaction(c);

        x = this.partnerDAS.update(x);

        LOGGER.info("X: "+  x.toString());
    }

}
