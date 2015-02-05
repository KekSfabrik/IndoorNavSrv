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
import de.hsmainz.gi.types.LocationId;
import de.hsmainz.gi.types.Site;
import de.hsmainz.gi.indoornavsrv.util.StringUtils;
import de.hsmainz.gi.types.WkbLocation;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>
 */
public class BeaconLocatorServiceTest extends TestCase {
    
    private static final Beacon     testBeacon = new Beacon(1, "00000000000000000000000000000000", 100, 12);
    private static final Site       testSite = new Site(1, "KekSfabrik");
    private static final com.vividsolutions.jts.geom.Point testJtsPoint = new com.vividsolutions.jts.geom.GeometryFactory()
                        .createPoint(
                            new com.vividsolutions.jts.geom.Coordinate(
                                    0, 0, 1
                            )
                        );
    static {
        testJtsPoint.setSRID(4326);
    }
    private static final WkbPoint      testPoint = new WkbPoint(testJtsPoint);
    private static final WkbLocation   testLocation = new WkbLocation(
                                                    new LocationId(testBeacon.getId(), testSite.getSite()),
                                                    testBeacon,
                                                    testSite,
                                                    testPoint
                                                );

    
    public BeaconLocatorServiceTest(String testName) {
        super(testName);
    }

    /**
     * Test of getSites method, of class BeaconLocatorService.
     */
    public void testGetSites_Beacon() {
        System.out.println("getSites(Beacon)");
        Beacon beacon = testBeacon;
        BeaconLocatorService instance = new BeaconLocatorService();
        List<Site> expResult = Arrays.asList(new Site[]{ testSite });
        List<Site> result = instance.getSites(beacon);
        System.out.println("result\t" + Arrays.toString(StringUtils.listAll(result)));
        System.out.println("should\t" + Arrays.toString(StringUtils.listAll(expResult)));
        assertEquals(expResult, result);
        System.out.println("getSites(Beacon) done.");
    }

    /**
     * Test of getSites method, of class BeaconLocatorService.
     */
    public void testGetSites_List() {
        System.out.println("getSites(List<Beacon>)");
        List<Beacon> beacons = Arrays.asList(new Beacon[]{ testBeacon });
        BeaconLocatorService instance = new BeaconLocatorService();
        List<Site> expResult = Arrays.asList(new Site[]{ testSite });
        List<Site> result = instance.getSites(beacons);
        System.out.println("result\t" + Arrays.toString(StringUtils.listAll(result)));
        System.out.println("should\t" + Arrays.toString(StringUtils.listAll(expResult)));
        assertEquals(expResult, result);
        System.out.println("getSites(List<Beacon>) done.");
    }

    /**
     * Test of getCoordinate method, of class BeaconLocatorService.
     */
    public void testGetCoordinate() {
        System.out.println("getCoordinate");
        Site site = testSite;
        Beacon beacon = testBeacon;
        BeaconLocatorService instance = new BeaconLocatorService();
        WkbPoint expResult = new WkbPoint(testJtsPoint);
        WkbPoint result = instance.getCoordinate(site, beacon);
        System.out.println("result:\t" + result.getWkb() + "\t" + result.getPoint());
        System.out.println("should:\t" + expResult.getWkb() + "\t" + expResult.getPoint());
//        System.out.println("result\tPOINT(" + expResult.getCoordinate().x + ", " + expResult.getCoordinate().y + ", " + expResult.getCoordinate().z + ") = " + expResult.toString());
//        System.out.println("result\tPOINT(" + result.getCoordinate().x + ", " + result.getCoordinate().y + ", " + result.getCoordinate().z + ") = " + result.toString());
//        assertEquals(expResult.getCoordinate().x, result.getCoordinate().x, 1e-5d);
//        assertEquals(expResult.getCoordinate().y, result.getCoordinate().y, 1e-5d);
//        assertEquals(expResult.getCoordinate().z, result.getCoordinate().z, 1e-5d);
        assertEquals(expResult, result);
        System.out.println("getCoordinate done.");
    }
    /**
     * Test of getCoordinate method, of class BeaconLocatorService.
     */
    public void testGetCoordinateBeaconWithoutId() {
        System.out.println("getCoordinateBeaconWithoutId");
        Site site = testSite;
        Beacon tb = new Beacon();
        tb.setUuid(testBeacon.getUuid());
        tb.setMajor(testBeacon.getMajor());
        tb.setMinor(testBeacon.getMinor());
        BeaconLocatorService instance = new BeaconLocatorService();
        WkbPoint expResult = new WkbPoint(testJtsPoint);
        WkbPoint result = instance.getCoordinate(site, tb);
        System.out.println("result:\t" + result.getWkb() + "\t" + result.getPoint());
        System.out.println("should:\t" + expResult.getWkb() + "\t" + expResult.getPoint());
//        System.out.println("result\tPOINT(" + expResult.getCoordinate().x + ", " + expResult.getCoordinate().y + ", " + expResult.getCoordinate().z + ") = " + expResult.toString());
//        System.out.println("result\tPOINT(" + result.getCoordinate().x + ", " + result.getCoordinate().y + ", " + result.getCoordinate().z + ") = " + result.toString());
//        assertEquals(expResult.getCoordinate().x, result.getCoordinate().x, 1e-5d);
//        assertEquals(expResult.getCoordinate().y, result.getCoordinate().y, 1e-5d);
//        assertEquals(expResult.getCoordinate().z, result.getCoordinate().z, 1e-5d);
        assertEquals(expResult, result);
        System.out.println("getCoordinateBeaconWithoutId done.");
    }

    /**
     * Test of getBeaconLocations method, of class BeaconLocatorService.
     */
    public void testGetBeaconLocations_Site() {
        System.out.println("getBeaconLocations(Site)");
        Site site = testSite;
        BeaconLocatorService instance = new BeaconLocatorService();
        List<WkbLocation> expResult = Arrays.asList(new WkbLocation[]{ testLocation });
        List<WkbLocation> result = instance.getBeaconLocations(site);
        System.out.println("result\t" + Arrays.toString(StringUtils.listAll(result)));
        System.out.println("should\t" + Arrays.toString(StringUtils.listAll(expResult)));
        assertEquals(expResult, result);
        System.out.println("getBeaconLocations(Site) done.");
    }

    /**
     * Test of getBeaconLocations method, of class BeaconLocatorService.
     */
    public void testGetBeaconWkbLocations_List() {
        System.out.println("getBeaconLocations(List<Beacon>)");
        List<Beacon> beacons = Arrays.asList(new Beacon[]{ testBeacon });
        BeaconLocatorService instance = new BeaconLocatorService();
        List<WkbLocation> expResult = Arrays.asList(new WkbLocation[]{ testLocation });
        List<WkbLocation> result = instance.getBeaconLocations(beacons);
//        System.out.println("result\t" + Arrays.toString(StringUtils.listAll(result)));
//        System.out.println("should\t" + Arrays.toString(StringUtils.listAll(expResult)));
        assertEquals(expResult, result);
        System.out.println("getBeaconLocations(List<Beacon>) done.");
    }

    /**
     * Test of getSite method, of class BeaconLocatorService.
     */
    @Test
    public void testGetSite() {
        System.out.println("getSite");
        String name = testSite.getName();
        BeaconLocatorService instance = new BeaconLocatorService();
        Site expResult = testSite;
        Site result = instance.getSite(name);
        System.out.println("result\t" + result);
        System.out.println("should\t" + expResult);
        assertEquals(expResult, result);
        assertEquals(expResult.getSite(), result.getSite());
        System.out.println("getSite done.");
    }

    /**
     * Test of getBeacon method, of class BeaconLocatorService.
     */
    @Test
    public void testGetBeacon() {
        System.out.println("getBeacon done.");
        String uuid = testBeacon.getUuid();
        int major = testBeacon.getMajor();
        int minor = testBeacon.getMinor();
        BeaconLocatorService instance = new BeaconLocatorService();
        Beacon expResult = testBeacon;
        Beacon result = instance.getBeacon(uuid, major, minor);
        System.out.println("result\t" + result);
        System.out.println("should\t" + expResult);
        assertEquals(expResult, result);
        assertEquals(expResult.getId(), result.getId());
        System.out.println("getBeacon done.");
    }

    /**
     * Test of getSiteByApproximateName method, of class BeaconLocatorService.
     */
    @Test
    public void testGetSiteByApproximateName() {
        System.out.println("getSiteByApproximateName");
        String name = "KekS";
        BeaconLocatorService instance = new BeaconLocatorService();
        List<Site> expResult = Arrays.asList(new Site[]{ testSite });
        List<Site> result = instance.getSiteByApproximateName(name);
        System.out.println("result\t" + Arrays.toString(StringUtils.listAll(result)));
        System.out.println("should\t" + Arrays.toString(StringUtils.listAll(expResult)));
        assertEquals(expResult, result);
        System.out.println("getSiteByApproximateName done.");
    }

    /**
     * Test of getBeacon method, of class BeaconLocatorService.
     */
    @Test
    public void testGetBeacon_3args() {
        System.out.println("getBeacon");
        String uuid = testBeacon.getUuid();
        int major = testBeacon.getMajor();
        int minor = testBeacon.getMinor();
        BeaconLocatorService instance = new BeaconLocatorService();
        Beacon expResult = testBeacon;
        Beacon result = instance.getBeacon(uuid, major, minor);
        System.out.println("result\t" + result);
        System.out.println("should\t" + expResult);
        assertEquals(expResult, result);
        System.out.println("getBeacon done.");
    }

    /**
     * Test of getBeacon method, of class BeaconLocatorService.
     */
    @Test
    public void testGetBeacon_Beacon() {
        System.out.println("getBeacon");
        Beacon beacon = testBeacon;
        BeaconLocatorService instance = new BeaconLocatorService();
        Beacon expResult = testBeacon;
        Beacon result = instance.getBeacon(beacon);
        System.out.println("result\t" + result);
        System.out.println("should\t" + expResult);
        assertEquals(expResult, result);
        System.out.println("getBeacon done.");
    }

    /**
     * Test of getBeacons method, of class BeaconLocatorService.
     */
    @Test
    public void testGetBeacons() {
        System.out.println("getBeacons");
        List<Beacon> beacons = Arrays.asList(new Beacon[]{ testBeacon });
        BeaconLocatorService instance = new BeaconLocatorService();
        List<Beacon> expResult = Arrays.asList(new Beacon[]{ testBeacon });
        List<Beacon> result = instance.getBeacons(beacons);
        System.out.println("result\t" + Arrays.toString(StringUtils.listAll(result)));
        System.out.println("should\t" + Arrays.toString(StringUtils.listAll(expResult)));
        assertEquals(expResult, result);
        System.out.println("getBeacons done.");
    }
    
}
