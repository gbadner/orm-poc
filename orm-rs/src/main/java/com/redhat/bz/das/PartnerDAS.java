package com.redhat.bz.das;

import com.redhat.bz.Partner;

import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.UUID;

@Stateless
public class PartnerDAS extends AbstractDAS<Partner, UUID> implements Serializable {
    // ======================================
    // =             Attributes             =
    // ======================================


    // ======================================
    // =            Constructors            =
    // ======================================
    public PartnerDAS() {

        this.entityType = Partner.class;
        this.idType = UUID.class;
    }
}
