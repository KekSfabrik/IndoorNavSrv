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

import de.hsmainz.gi.types.Site;
import de.hsmainz.gi.indoornavsrv.util.StringUtils;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu"/>
 */
public class SiteDaoTest extends TestCase {

    private static SiteDao sd = new SiteDao();
    
    /* exists in db */
    private static Site testSite = new Site(1, "KekSfabrik");

    public SiteDaoTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sd.openCurrentSession();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        sd.closeCurrentSession();
    }

    /**
     * Test of findAll method, of class SiteDao.
     */
    public void testGetSites() {
        System.out.println("getSites");
        int res = 7;
        List<Site> result = sd.findAll();
        System.out.println("res.size=\t" + result.size());
        System.out.println("should be=\t" + res);
        assertEquals(result.size(), res);
        System.out.println("getSites done.");
    }

    /**
     * Test of findByApproximateName method, of class SiteDao.
     */
    public void testGetSitesByApproximateName() {
        System.out.println("getSitesByApproximateName");
        String name = "KekSfabrik";
        List<Site> result = sd.findByApproximateName(name);
        result.stream().map((s) -> {
            System.out.println("\t"+ StringUtils.toString(s));
            return s;
        }).filter((s) -> (s.getName().equals(testSite.getName()))).forEach((s) -> {
            assertEquals(s, testSite);
        });
        System.out.println("getSitesByApproximateName done.");
    }

    /**
     * Test of findByName method, of class SiteDao.
     */
    public void testGetSitesByName() {
        System.out.println("getSitesByName");
        String name = testSite.getName();
        Site result = sd.findByName(name);
        System.out.println("res=\t" + StringUtils.toString(result));
        System.out.println("should be=\t" + StringUtils.toString(testSite));
        assertEquals(result, testSite);
        System.out.println("getSitesByName done.");
    }

    /**
     * Test of findById method, of class SiteDao.
     */
    public void testGetSiteById() {
        System.out.println("getSiteByID");
        int id = testSite.getSite();
        Site result = sd.findById(id);
        System.out.println("res=\t" + StringUtils.toString(result));
        System.out.println("should be=\t" + StringUtils.toString(testSite));
        assertEquals(result, testSite);
        System.out.println("getSiteByID done.");
    }

}
