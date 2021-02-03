package com.redhat.bz;

public class PartnerDataFactory {

    public static Partner createPartner() {
        Partner p = new Partner();
        p.setPid("123456");
        return  p;
    }
}
