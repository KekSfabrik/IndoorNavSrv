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
package de.hsmainz.gi.indoornavsrv.dao;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import de.hsmainz.gi.indoornavsrv.util.StringUtils;
import de.hsmainz.gi.types.Beacon;
import de.hsmainz.gi.types.Location;
import de.hsmainz.gi.types.LocationId;
import de.hsmainz.gi.types.Site;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu"/>
 */
public class LocationDaoTest extends TestCase {

    private static LocationDao      ld = new LocationDao();
    private static final Beacon     testBeacon = new Beacon(1, "00000000000000000000000000000000", 100, 12);
    private static final String     tbWkb = "01010000A0E610000000000000000000000000000000000000000000000000F03F";
    private static final Beacon     testBeacon2 = new Beacon(10, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 16028);
    private static final String     tb2Wkb = "01010000A0E6100000000000000000F03F000000000000F03F0000000000000040";
    private static final Site       testSite = new Site(1, "KekSfabrik - Lotharstrasse 1,  55116 Mainz");
    private static final Location   testLocation = 
            new Location(
                new LocationId(testBeacon.getId(), testSite.getSite()),
                testBeacon,
                testSite,
                new GeometryFactory().createPoint(new Coordinate(0, 0, 1))
            );

    public LocationDaoTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ld.openCurrentSession();
        System.out.println("===================================================");
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        System.out.println("===================================================");
        ld.closeCurrentSession();
    }

    public void testSomeMethod() {
        System.out.println("start");
        Location result = ld.findById(testLocation.getId());
        System.out.println("result\t" + StringUtils.toString(result));
        System.out.println("should\t" + StringUtils.toString(testLocation));
        System.out.println("done");
    }

    /**
     * Test of findById method, of class LocationDao.
     */
    public void testFindById() {
        System.out.println("findById");
        LocationId id = new LocationId(testSite.getSite(), testBeacon.getId());
        Location expResult = testLocation;
        Location result = ld.findById(id);
        System.out.println("result\t" + StringUtils.toString(result));
        System.out.println("should\t" + StringUtils.toString(testLocation));
        System.out.println("---------------------------------------------------");
        System.out.println("result wkb should be: " + tbWkb);
        try {
            System.out.println("parsed result: " );
            String str = new com.vividsolutions.jts.io.WKBReader().read(com.vividsolutions.jts.io.WKBReader.hexToBytes(tbWkb)).toString();
            System.out.println(str );
        } catch (ParseException ex) {
            System.err.println("could not parse");
        }
        System.out.println("result toString is: " + result.getCoord().toString());
        System.out.println("result toText is: " + result.getCoord().toText());
        String hexWkb2D = com.vividsolutions.jts.io.WKBWriter.toHex(new com.vividsolutions.jts.io.WKBWriter(2, true).write(result.getCoord()));
        System.out.println("result wkb2d is: " + hexWkb2D);
        String hexWkb3D = com.vividsolutions.jts.io.WKBWriter.toHex(new com.vividsolutions.jts.io.WKBWriter(3, true).write(result.getCoord()));
        System.out.println("result wkb3d is: " + hexWkb3D);
        com.vividsolutions.jts.io.WKBReader wkbReader = new com.vividsolutions.jts.io.WKBReader();
        try {
            com.vividsolutions.jts.geom.Geometry p2d = wkbReader.read(com.vividsolutions.jts.io.WKBReader.hexToBytes(hexWkb2D));
            System.out.println("hexwkb2p2d: (" + p2d.getSRID() + ") " + p2d);
            System.out.println("result wkt is: " + new String(new com.vividsolutions.jts.io.WKTWriter(3).write(p2d)));
            com.vividsolutions.jts.geom.Geometry p3d = wkbReader.read(com.vividsolutions.jts.io.WKBReader.hexToBytes(hexWkb3D));
            System.out.println("hexwkb2p3d: (" + p3d.getSRID() + ") " + p3d);
            System.out.println("result wkt is: " + new String(new com.vividsolutions.jts.io.WKTWriter(3).write(p3d)));
        } catch (ParseException ex) {
            System.err.println("could not parse");
        }
        System.out.println("result wkt is: " + new String(new com.vividsolutions.jts.io.WKTWriter(3).write(result.getCoord())));
        System.out.println("---------------------------------------------------");
        assertEquals(expResult, result);
        System.out.println("findById done.");
    }

    /**
     * Test of findBySite method, of class LocationDao.
     */
    public void testFindBySite() {
        System.out.println("findBySite");
        Site site = testSite;
        List<Location> expResult = Arrays.asList(new Location[]{ testLocation });
        List<Location> result = ld.findBySite(site);
        System.out.println("result\t" + Arrays.toString(StringUtils.listAll(result)));
        System.out.println("should\t" + Arrays.toString(StringUtils.listAll(expResult)));
        assertEquals(expResult, result);
        System.out.println("findBySite done.");
    }

    /**
     * Test of findByBeacon method, of class LocationDao.
     */
    public void testFindByBeacon() {
        System.out.println("findByBeacon");
        Beacon beacon = testBeacon;
        List<Location> expResult = Arrays.asList(new Location[]{ testLocation });
        List<Location> result = ld.findByBeacon(beacon);
        System.out.println("result\t" + Arrays.toString(StringUtils.listAll(result)));
        System.out.println("should\t" + Arrays.toString(StringUtils.listAll(expResult)));
        assertEquals(expResult, result);
        System.out.println("findByBeacon done.");
    }

}
