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

import java.io.Serializable;
import javax.xml.bind.annotation.XmlType;

/**
 * The Primary Key of a {@link de.hsmainz.gi.types.Location} (from the Database) 
 * aswell as the "well-known binary" (WKB) based {@link de.hsmainz.gi.types.WkbLocation} 
 * is the combination the ids of a {@link de.hsmainz.gi.types.Beacon} at a specific
 * {@link de.hsmainz.gi.types.Site}.
 * 
 * @version 1.0
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>
 */
@XmlType(
    name = "LocationId", 
    propOrder = { "beaconId", "site" }
)
public class LocationId implements Serializable, Comparable, IndoorNavEntity {

    private int     site;
    private int     beaconId;

    public LocationId() {
    }

    public LocationId(int site, int beaconId) {
        this.site = site;
        this.beaconId = beaconId;
    }

    public int getSite() {
        return this.site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public int getBeaconId() {
        return this.beaconId;
    }

    public void setBeaconId(int beaconId) {
        this.beaconId = beaconId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof LocationId)) {
            return false;
        }
        LocationId castOther = (LocationId) other;
        return (this.getSite() == castOther.getSite())
                && (this.getBeaconId() == castOther.getBeaconId());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + this.getSite();
        result = 37 * result + this.getBeaconId();
        return result;
    }

    @Override
    public int compareTo(Object o) {
        int out = 0;
        out += 42 * this.site - ((LocationId) o).getSite();
        out += 79 * this.beaconId - ((LocationId) o).getBeaconId();
        return out;
    }

//    @Override
//    public String toString() {
//        return "PK: " + this.site + ", " + this.beaconId;
//    }

}
