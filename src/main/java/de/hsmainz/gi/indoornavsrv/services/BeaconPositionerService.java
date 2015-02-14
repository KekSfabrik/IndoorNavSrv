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
import de.hsmainz.gi.indoornavsrv.dao.BeaconDao;
import de.hsmainz.gi.indoornavsrv.dao.LocationDao;
import de.hsmainz.gi.indoornavsrv.dao.SiteDao;
import de.hsmainz.gi.types.WkbLocation;
import java.util.Objects;
import javax.jws.WebService;

/**
 * The concrete Implementation of the {@link de.hsmainz.gi.indoornavsrv.services.IBeaconPositionerService}
 * Interface.
 * 
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>, 21.01.2015
 */
@WebService(
    endpointInterface   = "de.hsmainz.gi.indoornavsrv.services.IBeaconPositionerService", 
    serviceName         = "BeaconPositionerService"
) 
public class BeaconPositionerService implements IBeaconPositionerService {

    private static LocationDao  ld;
    private static BeaconDao    bd;
    private static SiteDao      sd;
    
    public BeaconPositionerService() {
        BeaconPositionerService.ld = new LocationDao();
        BeaconPositionerService.bd = new BeaconDao();
        BeaconPositionerService.sd = new SiteDao();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addSite(Site site) {
        sd.openCurrentSessionwithTransaction();
        boolean res = false;
        Site tmp;
        if (site.getSite() == 0) {
            tmp = sd.findByName(site.getName());
            if (Objects.isNull(tmp)) {
                sd.save(site);
                res = true;
            }
        }
        sd.closeCurrentSessionwithTransaction();
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addSite(String name) {
        sd.openCurrentSessionwithTransaction();
        boolean res = false;
        Site tmp;
        if (Objects.nonNull(name)) {
            tmp = sd.findByName(name);
            if (Objects.isNull(tmp)) {
                Site site = new Site();
                site.setName(name);
                sd.save(site);
                res = true;
            }
        }
        sd.closeCurrentSessionwithTransaction();
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteSite(Site site) {
        sd.openCurrentSessionwithTransaction();
        boolean res = false;
        Site tmp;
        if (Objects.nonNull(site)
        && site.getSite() != 0) {
            sd.delete(site);
            res = true;
        }
        sd.closeCurrentSessionwithTransaction();
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addBeacon(Beacon beacon) {
        bd.openCurrentSessionwithTransaction();
        boolean res = false;
        Beacon tmp;
        if (Objects.nonNull(beacon)
        && beacon.getId() == 0) {
            tmp = bd.find(beacon);
            if (Objects.isNull(tmp)) {
                bd.save(beacon);
                res = true;
            }
        }
        bd.closeCurrentSessionwithTransaction();
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addBeacon(String uuid, int major, int minor) {
        bd.openCurrentSessionwithTransaction();
        boolean res = false;
        Beacon tmp;
        if (Objects.nonNull(uuid) 
        && Objects.nonNull(major)
        && Objects.nonNull(minor)) {
            tmp = bd.findByUuidMajorMinor(uuid, major, minor);
            if (Objects.isNull(tmp)) {
                tmp = new Beacon();
                tmp.setUuid(uuid);
                tmp.setMajor(major);
                tmp.setMinor(minor);
                bd.save(tmp);
                res = true;
            }
        }
        bd.closeCurrentSessionwithTransaction();
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeBeaconFromSite(Beacon beacon, Site site) {
        ld.openCurrentSessionwithTransaction();
        boolean res = false;
        Location tmp;
        if (Objects.nonNull(beacon)
        && beacon.getId() != 0
        && Objects.nonNull(site)
        && site.getSite() != 0) {
            tmp = ld.findById(new LocationId(site.getSite(), beacon.getId()));
            if (Objects.nonNull(tmp)) {
                ld.delete(tmp);
                res = true;
            }
        }
        ld.closeCurrentSessionwithTransaction();
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeBeaconFromSite(WkbLocation location) {
        ld.openCurrentSessionwithTransaction();
        boolean res = false;
        WkbLocation tmp;
        if (Objects.nonNull(location)
        && Objects.nonNull(location.getId())) {
            ld.delete(new Location(location));
            res = true;
        }
        ld.closeCurrentSessionwithTransaction();
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteBeacon(Beacon beacon) {
        bd.openCurrentSessionwithTransaction();
        boolean res = false;
        Beacon tmp;
        if (Objects.nonNull(beacon)
        && beacon.getId() != 0) {
            tmp = bd.findById(beacon.getId());
            if (Objects.nonNull(tmp)) {
                bd.delete(tmp);
                res = true;
            }
        }
        bd.closeCurrentSessionwithTransaction();
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean placeBeacon(Site site, Beacon beacon, WkbPoint coordinate) {
        ld.openCurrentSessionwithTransaction();
        boolean res = false;
        Location tmp;
        if (Objects.nonNull(beacon)
        && beacon.getId() != 0
        && Objects.nonNull(site)
        && site.getSite() != 0
        && Objects.nonNull(coordinate)) {
            tmp = ld.findById(new LocationId(site.getSite(), beacon.getId()));
            if (Objects.isNull(tmp)) {
                tmp = new Location(new LocationId(site.getSite(), beacon.getId()), beacon, site, coordinate.getPoint());
                ld.save(tmp);
                res = true;
            }
        }
        ld.closeCurrentSessionwithTransaction();
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean placeBeacon(WkbLocation location) {
        ld.openCurrentSessionwithTransaction();
        boolean res = false;
        Location tmp;
        if (Objects.nonNull(location)
        && Objects.nonNull(location.getId())
            && location.getId().getBeaconId() != 0
            && location.getId().getSite() != 0
        && Objects.nonNull(location.getBeacon())
        && Objects.nonNull(location.getSite())
        && Objects.nonNull(location.getCoord())) {
            tmp = ld.findById(location.getId());
            if (Objects.isNull(tmp)) {
                ld.save(new Location(location));
                res = true;
            }
        }
        ld.closeCurrentSessionwithTransaction();
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean replaceBeacon(Site site, Beacon oldBeacon, Beacon newBeacon) {
        ld.openCurrentSessionwithTransaction();
        boolean res = false;
        Location tmp;
        if (Objects.nonNull(site)
        && Objects.nonNull(site.getSite())
        && Objects.nonNull(oldBeacon)
        && oldBeacon.getId() != 0
        && Objects.nonNull(newBeacon)
        && newBeacon.getId() != 0) {
            LocationId l = new LocationId(site.getSite(), oldBeacon.getId());
            tmp = ld.findById(l);
            if (Objects.nonNull(tmp)) {
                com.vividsolutions.jts.geom.Point coord = tmp.getCoord();
                ld.delete(tmp);
                ld.save(new Location(new LocationId(site.getSite(), newBeacon.getId()), newBeacon, site, coord));
                res = true;
            }
        }
        ld.closeCurrentSessionwithTransaction();
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean relocateBeacon(Site fromSite, Site toSite, Beacon beacon, WkbPoint toCoordinate) {
        ld.openCurrentSessionwithTransaction();
        boolean res = false;
        Location tmp;
        if (Objects.nonNull(fromSite)
        && fromSite.getSite() != 0
        && Objects.nonNull(toSite)
        && toSite.getSite() != 0
        && Objects.nonNull(beacon)
        && beacon.getId() != 0
        && Objects.nonNull(toCoordinate)) {
            LocationId l = new LocationId(fromSite.getSite(), beacon.getId());
            tmp = ld.findById(l);
            if (Objects.nonNull(tmp)) {
                ld.delete(tmp);
                ld.save(new Location(new LocationId(toSite.getSite(), beacon.getId()), beacon, toSite, toCoordinate.getPoint()));
                res = true;
            }
        }
        ld.closeCurrentSessionwithTransaction();
        return res;
    }
}
