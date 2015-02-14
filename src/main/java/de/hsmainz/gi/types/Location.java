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
package de.hsmainz.gi.types;
// Generated 16.01.2015 12:45:14 by Hibernate Tools 4.3.1

import com.vividsolutions.jts.geom.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Location is the combination of the coordinate ({@link com.vividsolutions.jts.geom.Point})
 * of a {@link de.hsmainz.gi.types.Beacon} at a specific {@link de.hsmainz.gi.types.Site}.
 * In the Database it has the ids of both the Beacon and Site as its Primary Key
 * ({@link de.hsmainz.gi.types.LocationId}).
 * 
 * @version 1.0
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>
 */
public class Location implements Serializable, Comparable, IndoorNavEntity {

    private LocationId  id;
    private Beacon      beacon;
    private Site        site;
    private Point       coord;

    public Location() {
    }
    
    public Location(WkbLocation location) {
        this.id = location.getId();
        this.beacon = location.getBeacon();
        this.site = location.getSite();
        this.coord = location.getCoord().getPoint();
    }

    public Location(LocationId id, Beacon beacon, Site site) {
        this.id = id;
        this.beacon = beacon;
        this.site = site;
    }

    public Location(LocationId id, Beacon beacon, Site site, Point coord) {
        this.id = id;
        this.beacon = beacon;
        this.site = site;
        this.coord = coord;
    }

    public LocationId getId() {
        return this.id;
    }

    public void setId(LocationId id) {
        this.id = id;
    }

    public Beacon getBeacon() {
        return this.beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }

    public Site getSite() {
        return this.site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Point getCoord() {
        return this.coord;
    }

    public void setCoord(Point coord) {
        this.coord = coord;
    }

    @Override
    public int compareTo(Object o) {
        int out = 0;
        out += this.id.compareTo(((Location) o).getId());
        out += this.beacon.compareTo(((Location) o).getBeacon());
        out += this.site.compareTo(((Location) o).getSite());
        return out;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof Location)) {
            return false;
        }
        Location castOther = (Location) other;
        return (this.site.equals(castOther.getSite()))
                && (this.beacon.equals(castOther.getBeacon()));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.beacon);
        hash = 79 * hash + Objects.hashCode(this.site);
        hash = 79 * hash + Objects.hashCode(this.coord);
        return hash;
    }
//    @Override
//    public String toString() {
//        return "Location:\t" + this.id 
//                + " (" + this.beacon.getUuid() + ", " + this.beacon.getMajor() + ", " + this.beacon.getMinor() 
//                + ") at " + this.site.getName() 
//                + " @ " + this.coord.toText() ;
//    }
}
