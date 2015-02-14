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
import de.hsmainz.gi.types.WkbLocation;
import de.hsmainz.gi.types.Location;
import de.hsmainz.gi.types.LocationId;
import de.hsmainz.gi.types.Site;
import de.hsmainz.gi.indoornavsrv.dao.BeaconDao;
import de.hsmainz.gi.indoornavsrv.dao.LocationDao;
import de.hsmainz.gi.indoornavsrv.dao.SiteDao;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;

/**
 * The concrete Implementation of the {@link de.hsmainz.gi.indoornavsrv.services.IBeaconLocatorService}
 * Interface.
 * 
 * 
 * 
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>, 18.01.2015
 */
@WebService(
    endpointInterface   = "de.hsmainz.gi.indoornavsrv.services.IBeaconLocatorService", 
    serviceName         = "BeaconLocatorService"
)
public class BeaconLocatorService implements IBeaconLocatorService {

    private static LocationDao  ld;
    private static BeaconDao    bd;
    private static SiteDao      sd;

    public BeaconLocatorService() {
        BeaconLocatorService.ld = new LocationDao();
        BeaconLocatorService.bd = new BeaconDao();
        BeaconLocatorService.sd = new SiteDao();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Site> getSites(Beacon beacon) {
        if (beacon.getId() == 0) {
            bd.openCurrentSession();
            beacon = bd.find(beacon);
            bd.closeCurrentSession();
        }
        List<Site> sites = new ArrayList<>();
        if (beacon != null) {
            ld.openCurrentSession();
            ld.findByBeacon(beacon).forEach(l -> sites.add(l.getSite()));
            ld.closeCurrentSession();
        }
        return sites;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Site> getSites(List<Beacon> beacons) {
        List<Site> sites = new ArrayList<>();
        beacons.forEach(beacon -> sites.addAll(getSites(beacon)));
        return sites;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Site getSite(String name) {
        sd.openCurrentSession();
        Site result = sd.findByName(name);
        sd.closeCurrentSession();
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Site> getSiteByApproximateName(String name) {
        sd.openCurrentSession();
        List<Site> result = sd.findByApproximateName(name);
        sd.closeCurrentSession();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Beacon getBeacon(String uuid, int major, int minor) {
        bd.openCurrentSession();
        Beacon result = bd.findByUuidMajorMinor(uuid, major, minor);
        bd.closeCurrentSession();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Beacon getBeacon(Beacon beacon) {
        if (beacon.getId() == 0) {
            bd.openCurrentSession();
            beacon = bd.find(beacon);
            bd.closeCurrentSession();
        } 
        return beacon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Beacon> getBeacons(List<Beacon> beacons) {
        bd.openCurrentSession();
        List<Beacon> result = bd.findMultiple(beacons);
        bd.closeCurrentSession();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WkbPoint getCoordinate(Site site, Beacon beacon) {
        if (beacon.getId() == 0) {
            bd.openCurrentSession();
            beacon = bd.find(beacon);
            bd.closeCurrentSession();
        }
        if (site.getSite() == 0) {
            sd.openCurrentSession();
            site = sd.findByName(site.getName());
            sd.closeCurrentSession();
        }
        if (beacon != null && site != null) {
            ld.openCurrentSession();
            WkbPoint point = new WkbPoint(ld.findById(new LocationId(site.getSite(), beacon.getId())).getCoord());
            ld.closeCurrentSession();
            return point;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WkbLocation> getBeaconLocations(Site site) {
        if (site.getSite() == 0) {
            sd.openCurrentSession();
            site = sd.findByName(site.getName());
            sd.closeCurrentSession();
        }
        List<WkbLocation> locations = new ArrayList<>();
        if (site != null) {
            ld.openCurrentSession();
            for (Location l : ld.findBySite(site)) {
                locations.add(new WkbLocation(l));
            }
            ld.closeCurrentSession();
        }
        return locations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WkbLocation> getBeaconLocations(List<Beacon> beacons) {
        ld.openCurrentSession();
        List<WkbLocation> locations = new ArrayList<>();
        bd.openCurrentSession();
        beacons.forEach(
            b -> {
                if (b.getId() == 0) {
                    b = bd.find(b);
                }
                if (b != null) {
                    for (Location l : ld.findByBeacon(b)) {
                        locations.add(new WkbLocation(l));
                    }
                }
            }
        );
        bd.closeCurrentSession();
        ld.closeCurrentSession();
        return locations;
    }
}
