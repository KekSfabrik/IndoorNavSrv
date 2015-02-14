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

package de.hsmainz.gi.indoornavsrv.util;

import de.hsmainz.gi.types.Beacon;
import de.hsmainz.gi.types.IndoorNavEntity;
import de.hsmainz.gi.types.Location;
import de.hsmainz.gi.types.LocationId;
import de.hsmainz.gi.types.Site;
import de.hsmainz.gi.types.WkbLocation;
import de.hsmainz.gi.types.WkbPoint;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility class that implements an alternative to {@link java.lang.Object#toString }
 * that does not mess up XML marshalling or unmarshalling.
 * 
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>, 23.01.2015
 */
public abstract class StringUtils {

    /* LOL! */
    public static double COORDINATE_PRECISION = 1e-5;
    
    public static String[] listAll(List<? extends IndoorNavEntity> list) {
        if (Objects.nonNull(list) && list.size() <= 1) {
            return list.parallelStream().map(i -> toString(i)).collect(Collectors.toList()).toArray(new String[0]);
        }
        else {
            return new String[]{ "" };
        }
    }
    
//    public static String toString(Class clazz, IndoorNavEntity entity) {
//        if (entity.getClass().equals(clazz))
//            return toString((clazz.getName()) entity);
//    }
    
    public static String toString(Beacon b) {
        if (Objects.isNull(b))
            throw new NullPointerException();
        return "Beacon:\t" + b.getId() + ", '" + b.getUuid() + "', " + b.getMajor() + ", " + b.getMinor();
    }
    
    public static String toString(Location l) {
        if (Objects.isNull(l))
            throw new NullPointerException();
        return "Location:\t" + toString(l.getId()) 
                + " (" + l.getBeacon().getUuid() + ", " + l.getBeacon().getMajor() + ", " + l.getBeacon().getMinor() 
                + ") at " + l.getSite().getName() 
                + " @ " + l.getCoord().toText() ;
    }
    
    public static String toString(WkbLocation l) {
        if (Objects.isNull(l))
            throw new NullPointerException(); 
        return toString(new Location(l));
    }
    
    public static String toString(LocationId lid) {
        if (Objects.isNull(lid))
            throw new NullPointerException();
        return "PK: " + lid.getSite() + ", " + lid.getBeaconId();
    }
    
    public static String toString(Site s) {
        if (Objects.isNull(s))
            throw new NullPointerException();
        return "Site:\t\t" + s.getSite() + ", '" + s.getName() + "'";
    }
    
    public static String toString(WkbPoint p) {
        if (Objects.isNull(p))
            throw new NullPointerException(); 
        return "WkbPoint:\t" + p.getWkb() + " = " + new com.vividsolutions.jts.io.WKTWriter(3).write(p.getPoint());
    }
    
    public static String toString(Object o) {
        return o.toString();
    }
}
