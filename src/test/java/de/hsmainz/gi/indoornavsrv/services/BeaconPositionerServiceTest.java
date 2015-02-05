/*
 * Copyright (C) 2015 Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package de.hsmainz.gi.indoornavsrv.services;

import de.hsmainz.gi.types.WkbPoint;
import de.hsmainz.gi.types.Beacon;
import de.hsmainz.gi.types.Location;
import de.hsmainz.gi.types.LocationId;
import de.hsmainz.gi.types.Site;
import de.hsmainz.gi.indoornavsrv.util.StringUtils;
import java.util.Arrays;
import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>
 */
public class BeaconPositionerServiceTest extends TestCase {
    
    
    private static Beacon     testBeacon = new Beacon(10, "00000000000000000000000000000000", 0, 0);
    private static Beacon     repBeacon = new Beacon(11, "11111111111111111111111111111111", 1, 1);
    private static Site       testSite = new Site(7, "TESTSITE");
    private static Location   testLocation = new Location(
                                                    new LocationId(testBeacon.getId(), testSite.getSite()),
                                                    testBeacon,
                                                    testSite,
                                                    new com.vividsolutions.jts.geom.GeometryFactory()
                                                    .createPoint(
                                                        new com.vividsolutions.jts.geom.Coordinate(
                                                            1, 1, 2
                                                        )
                                                    )
                                                );
    private static Beacon     noIdBeacon = new Beacon();
    private static Site       noIdSite = new Site();
    private static Location   noIdLocation = new Location();
    private static WkbPoint      coord1 = new WkbPoint(new com.vividsolutions.jts.geom.GeometryFactory()
                                                .createPoint(
                                                    new com.vividsolutions.jts.geom.Coordinate(
                                                        1, 1, 1
                                                    )
                                                ));
    private static WkbPoint      coord2 = new WkbPoint(new com.vividsolutions.jts.geom.GeometryFactory()
                                                .createPoint(
                                                    new com.vividsolutions.jts.geom.Coordinate(
                                                        2, 2, 1
                                                    )
                                                ));
    
    static {
        repBeacon.setId(0);
        
        noIdBeacon.setUuid("00000000000000000000000000000000");
        noIdBeacon.setMajor(0);
        noIdBeacon.setMinor(0);
        
        noIdSite.setName("TESTSITE1234");
        
        noIdLocation.setBeacon(noIdBeacon);
        noIdLocation.setSite(noIdSite);
        noIdLocation.setCoord(coord1.getPoint());
    }
    
    
    public BeaconPositionerServiceTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }



    /**
     * Test of addSite method, of class BeaconPositionerService.
     */
    @Test
    public void testAll() throws InterruptedException {
        System.out.println("testAll");
        BeaconPositionerService instance = new BeaconPositionerService();
        tt("initial setup");
        
        // ---------------- sites
        
        
        // cannot add with existing ID
        boolean result = instance.addSite(testSite);
        tt("addSite");
        assertFalse(result);
        
        // can add without existing ID
        instance = new BeaconPositionerService();
        result = instance.addSite(noIdSite);
        tt("addSiteNoId");
        assertTrue(result);
      
        
        // ---------------- beacons
        
        
        // cannot add with id
        instance = new BeaconPositionerService();
        result = instance.addBeacon(testBeacon);
        tt("addBeacon");
        assertFalse(result);
        
        // should be able to add without id
        instance = new BeaconPositionerService();
        result = instance.addBeacon(noIdBeacon);
        tt("addBeaconNoId");
        assertTrue(result);
        
        // should be able to add manually
        instance = new BeaconPositionerService();
        result = instance.addBeacon(repBeacon.getUuid(), repBeacon.getMajor(), repBeacon.getMinor());
        tt("addBeacon uuid,maj,min");
        assertTrue(result);
        
        
        // ---------------- shuffle around
        
        
        // put the newly added beacon on the newly added site
        instance = new BeaconPositionerService();
        result = instance.placeBeacon(noIdSite, noIdBeacon, coord2);
        tt("placeBeacon");
        assertTrue(result);
        
        // get it from locatorservice first
        BeaconLocatorService bls = new BeaconLocatorService();
        repBeacon = bls.getBeacon(repBeacon.getUuid(), repBeacon.getMajor(), repBeacon.getMinor());
        // replace the newly added beacon by another newly added on this site
        instance = new BeaconPositionerService();
        result = instance.replaceBeacon(noIdSite, noIdBeacon, repBeacon);
        tt("replaceBeacon");
        assertTrue(result);
        
        // remove that beacon from the site
        instance = new BeaconPositionerService();
        result = instance.removeBeaconFromSite(repBeacon, noIdSite);
        tt("removeBeaconFromSite");
        assertTrue(result);
        
        
        // ---------------- get rid of everything
        
        
        // should be able to remove the given one
        instance = new BeaconPositionerService();
        result = instance.deleteSite(noIdSite);
        tt("deleteSite");
        assertTrue(result);
        
        // remove the newly added beacon
        instance = new BeaconPositionerService();
        result = instance.deleteBeacon(noIdBeacon);
        tt("deleteBeacon");
        assertTrue(result);
        
        // remove the replacement
        instance = new BeaconPositionerService();
        result = instance.deleteBeacon(repBeacon);
        tt("deleteBeacon (rep)");
        assertTrue(result);
        
        System.out.println("testAll done.");
    }
    
    private static void tt(String... text) {
        if (text != null) {
            System.out.println("##################### "+Arrays.toString(text)+ " #####################");
        } else {
            System.out.println("######################################################");
        }
        try {
            System.out.println("testBeacon\t= " + StringUtils.toString(testBeacon));
            System.out.println("repBeacon\t= " + StringUtils.toString(repBeacon));
            System.out.println("testSite\t\t= " + StringUtils.toString(testSite));
            System.out.println("testLocation\t= " + StringUtils.toString(testLocation));
            System.out.println("noIdBeacon\t= " + StringUtils.toString(noIdBeacon));
            System.out.println("noIdSite\t= " + StringUtils.toString(noIdSite));
            System.out.println("noIdLocation\t= " + StringUtils.toString(noIdLocation));
            System.out.println("######################################################");
        } catch (NullPointerException npe) {
            System.err.println("caught NPE, no problem");
        }
    }
}
