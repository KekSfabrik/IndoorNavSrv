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
import de.hsmainz.gi.types.Location;
import de.hsmainz.gi.types.LocationId;
import de.hsmainz.gi.types.Site;
import java.util.List;
import org.hibernate.Query;

/**
 * DAO Class for the {@link de.hsmainz.gi.types.Location} Class.
 * 
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>
 */
public class LocationDao extends GenericDao<Location, LocationId> {
    
    /**
     * Find the Location by the Primary Key.
     * 
     * @param   id  the LocationId of Beacon and Site
     * @return  the Location of the Beacon at the Site
     */
    public Location findById(LocationId id) {
        return this.findByID(Location.class, id);
    }
    
    /**
     * Find all Locations at a Site
     * 
     * @param   site    the Site to get Beacons and their coordinates at
     * @return  all Locations at the given Site
     */
    public List<Location> findBySite(Site site) {
        Query q = getCurrentSession().createQuery("from Location where site = :site");
        q.setInteger("site", site.getSite());
        return this.findMany(q);
    }
    
    /**
     * Find all Locations of a Beacon (Beacons can be in multiple Sites with 
     * different Coordinates in the local System).
     * 
     * @param   beacon  the Beacon to find Locations of
     * @return  all Locations of the given Beacon
     */
    public List<Location> findByBeacon(Beacon beacon) {
        Query q = getCurrentSession().createQuery("from Location where beacon_id = :id");
        q.setInteger("id", beacon.getId());
        return this.findMany(q);
    }
}
