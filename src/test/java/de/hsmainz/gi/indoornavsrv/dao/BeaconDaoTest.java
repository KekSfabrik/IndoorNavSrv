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

import de.hsmainz.gi.types.Beacon;
import de.hsmainz.gi.indoornavsrv.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu"/>
 */
public class BeaconDaoTest extends TestCase {
    
    private static BeaconDao bd = new BeaconDao();
    
    /* exists in the db */
    private static final Beacon testBeacon = new Beacon(1, "00000000000000000000000000000000", 100, 12);
    
    public BeaconDaoTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        bd.openCurrentSession();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        bd.closeCurrentSession();
    }

    /**
     * Test of findAll method, of class BeaconDao.
     */
    public void testGetBeacons() {
        System.out.println("getBeacons");
        int res = 10;
        List<Beacon> result = bd.findAll();
        System.out.println("res.size=\t"+result.size());
        System.out.println("should be=\t"+res);
        assertEquals(result.size(), res);
        System.out.println("getBeacons done.");
    }

    /**
     * Test of findByUuid method, of class BeaconDao.
     */
    public void testGetBeaconsByUuid() {
        System.out.println("getBeaconsByUuid");
        String uuid = testBeacon.getUuid();
        List<Beacon> result = bd.findByUuid(uuid);
        System.out.println("res=\t"+result);
        System.out.println("b1=\t"+result);
        for (Beacon b : result) {
            System.out.println("\t"+ StringUtils.toString(b));
            if (b.getMajor() == testBeacon.getMajor() && b.getMinor()== testBeacon.getMajor())
                assertEquals(b, testBeacon);
        }
        System.out.println("getBeaconsByUuid done.");
    }

    /**
     * Test of findByMajor method, of class BeaconDao.
     */
    public void testGetBeaconsByMajor() {
        System.out.println("getBeaconsByMajor");
        int major = testBeacon.getMajor();
        List<Beacon> result = bd.findByMajor(major);
        for (Beacon b : result) {
            System.out.println("\t"+ StringUtils.toString(b));
            if (b.getMajor() == testBeacon.getMajor())
                assertEquals(b, testBeacon);
        }
        System.out.println("getBeaconsByMajor done.");
    }

    /**
     * Test of findByMinor method, of class BeaconDao.
     */
    public void testGetBeaconsByMinor() {
        System.out.println("getBeaconsByMinor");
        int minor = testBeacon.getMinor();
        List<Beacon> result = bd.findByMinor(minor);
        for (Beacon b : result) {
            System.out.println("\t"+ StringUtils.toString(b));
//            System.out.println("\tlocations: "+ Arrays.toString(StringUtils.listAll(new ArrayList<>(b.getLocations()))));
            if (b.getMinor() == testBeacon.getMinor())
                assertEquals(b, testBeacon);
        }
        System.out.println("getBeaconsByMinor done.");
    }

    /**
     * Test of findById method, of class BeaconDao.
     */
    public void testGetBeaconById() {
        System.out.println("getBeaconById");
        int id = 1;
        Beacon result = bd.findById(id);
        assertEquals(result, testBeacon);
        System.out.println("getBeaconById done.");
    }


    /**
     * Test of findById method, of class BeaconDao.
     */
    public void testGetMultiple() {
        System.out.println("testGetMultiple");
        List<Beacon> beacons = new ArrayList<>();
        beacons.add(new Beacon(0, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 14047));
        beacons.add(new Beacon(0, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 15873));
        beacons.add(new Beacon(0, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 15874));
        beacons.add(new Beacon(0, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 15884));
        beacons.add(new Beacon(0, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 15893));
        beacons.add(new Beacon(0, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 16027));
        Collections.sort(beacons);
        List<Beacon> result = bd.findMultiple(beacons);
        Collections.sort(result);
        assertEquals(result, beacons);
        System.out.println("testGetMultiple done.");
    }
    
    /**
     * Test of findById method, of class BeaconDao.
     */
    public void testGetBeaconByUuidMajorMinor() {
        System.out.println("getBeaconByUuidMajorMinor");
        Beacon tb = new Beacon();
        tb.setUuid(testBeacon.getUuid());
        tb.setMajor(testBeacon.getMajor());
        tb.setMinor(testBeacon.getMinor());
        Beacon result = bd.find(tb);
        System.out.println("res=\t"+result.getId() + "\t" + StringUtils.toString(result));
        System.out.println("should be=\t"+tb.getId() + "\t" + StringUtils.toString(tb));
        assertEquals(result, tb);
        System.out.println("getBeaconByUuidMajorMinor done.");
    }
}
