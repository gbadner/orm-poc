package com.redhat.bz.utils;

import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class WebInfRes {
    public static void addWebInfRes(WebArchive webArchive, boolean emptyAssetForBeansXml) {
        if (emptyAssetForBeansXml) {
            webArchive.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        } else {
            webArchive.addAsWebInfResource("beans.xml", "beans.xml");
        }
        webArchive.addAsWebInfResource("jboss-deployment-structure.xml", "jboss-deployment-structure.xml");
        webArchive.addAsWebInfResource("jboss-web.xml", "jboss-web.xml");
    }
}
