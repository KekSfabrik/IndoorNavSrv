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
import java.util.Objects;
import javax.xml.bind.annotation.XmlType;

/**
 * A Site is a Place where physical iBeacons ({@link de.hsmainz.gi.types.Beacon}s)
 * are bonded into a logical group.
 * 
 * @version 1.0
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>
 */
@XmlType(
    name = "Site", 
    propOrder = { "name", "site" }
)
public class Site implements Serializable, Comparable, IndoorNavEntity {

    private int             site;
    private String          name;
    
    public Site() {
    }

    public Site(int site, String name) {
        this.site = site;
        this.name = name;
    }

    public int getSite() {
        return this.site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        int out = 0;
        out += this.name.compareTo(((Site) o).getName());
        out += 42 * this.site - ((Site) o).getSite();
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
        if (!(other instanceof Site)) {
            return false;
        }
        Site castOther = (Site) other;
        return this.name.equals(castOther.getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.site;
        hash = 11 * hash + Objects.hashCode(this.name);
        return hash;
    }

//    @Override
//    public String toString() {
//        return "Site:\t\t" + this.site + ", '" + this.name + "'";
//    }
}
