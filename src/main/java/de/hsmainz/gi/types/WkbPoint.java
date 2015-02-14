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

import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKBWriter;
import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlType;

/**
 * A WkbPoint represents a {@link com.vividsolutions.jts.geom.Point} in Form of a
 * "well-known binary" (WKB) Hex-String to allow marshalling and unmarshalling 
 * to and from XML. It is a wrapper of sorts that is only used to bring Points to
 * Clients.
 * 
 * @version 1.0
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>
 */
@XmlType(
    name = "WkbPoint", 
    propOrder = { "wkb" }
)
public class WkbPoint implements Serializable, Comparable, IndoorNavEntity {
    private static final WKBReader  reader = new WKBReader();
    /** WKBWriter for 3 dimensions with SRID */
    private static final WKBWriter  writer = new WKBWriter(3, true);
    
    private String                  wkb;
    
    public WkbPoint() { }
    
    public WkbPoint(String wkb) {
        this.wkb = wkb;
    }
    
    public WkbPoint(com.vividsolutions.jts.geom.Point point) {
        this.wkb = WKBWriter.toHex(writer.write(point));
    }
    
// <editor-fold desc="old implementation" defaultstate="collapsed">    
//    private int     SRID;
//    private double  x, 
//                    y, 
//                    z;
//
//    public WkbPoint(com.vividsolutions.jts.geom.WkbPoint point) {
//        this.SRID = point.getSRID();
//        this.x = point.getCoordinate().x;
//        this.y = point.getCoordinate().y;
//        this.z = point.getCoordinate().z;
//    }
//    
//    public WkbPoint(int SRID, double x, double y, double z) {
//        this.SRID = SRID;
//        this.x = x;
//        this.y = y;
//        this.z = z;
//    }
//    
//    /**
//     * @return the SRID
//     */
//    public int getSRID() {
//        return SRID;
//    }
//
//    /**
//     * @param SRID the SRID to set
//     */
//    public void setSRID(int SRID) {
//        this.SRID = SRID;
//    }
//
//    /**
//     * @return the x
//     */
//    public double getX() {
//        return x;
//    }
//
//    /**
//     * @param x the x to set
//     */
//    public void setX(double x) {
//        this.x = x;
//    }
//
//    /**
//     * @return the y
//     */
//    public double getY() {
//        return y;
//    }
//
//    /**
//     * @param y the y to set
//     */
//    public void setY(double y) {
//        this.y = y;
//    }
//
//    /**
//     * @return the z
//     */
//    public double getZ() {
//        return z;
//    }
//
//    /**
//     * @param z the z to set
//     */
//    public void setZ(double z) {
//        this.z = z;
//    }
//    
//    public com.vividsolutions.jts.geom.WkbPoint getPoint() {
//        return new GeometryFactory(
//                new PrecisionModel(
//                    de.hsmainz.gi.indoornavsrv.util.StringUtils.COORDINATE_PRECISION
//                ), 
//                SRID
//            )
//            .createPoint(
//                new Coordinate(
//                    x, 
//                    y, 
//                    z
//                )
//            );
//    }
//
//    @Override
//    public int compareTo(Object other) {
//        int out = 42;
//        out += 17 * Integer.compare(SRID, ((WkbPoint) other).getSRID());
//        out += 17 * Double.compare(x, ((WkbPoint) other).getX());
//        out += 17 * Double.compare(y, ((WkbPoint) other).getY());
//        out += 17 * Double.compare(z, ((WkbPoint) other).getZ());
//        return out;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 17 * hash + this.SRID;
//        hash = 17 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
//        hash = 17 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
//        hash = 17 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
//        return hash;
//    }
//    
//    @Override
//    public boolean equals(Object other) {
//        if (this == other) {
//            return true;
//        }
//        if (other == null) {
//            return false;
//        }
//        if (!(other instanceof WkbPoint)) {
//            return false;
//        }
//        WkbPoint castOther = (WkbPoint) other;
//        return  this.SRID == castOther.getSRID() 
//                && x - castOther.getX() <= de.hsmainz.gi.indoornavsrv.util.StringUtils.COORDINATE_PRECISION
//                && y - castOther.getY() <= de.hsmainz.gi.indoornavsrv.util.StringUtils.COORDINATE_PRECISION
//                && z - castOther.getZ() <= de.hsmainz.gi.indoornavsrv.util.StringUtils.COORDINATE_PRECISION; 
//    }
// </editor-fold>

    /**
     * @return the wkb
     */
    public String getWkb() {
        return wkb;
    }

    /**
     * This function parses the local {@link #wkb} and generates a new Point - in
     * case of failure a new <code>POINTZ(0 0 0)</code> is generated and returned.
     * @return  a valid Point
     */
    public com.vividsolutions.jts.geom.Point getPoint() {
        com.vividsolutions.jts.geom.Point point;
        try {
            point = (com.vividsolutions.jts.geom.Point) reader.read(WKBReader.hexToBytes(getWkb()));
        } catch (ParseException ex) {
            System.err.println("Couldn't parse WKB");
            point = new com.vividsolutions.jts.geom.GeometryFactory()
                        .createPoint(
                            new com.vividsolutions.jts.geom.Coordinate(
                                    0, 0, 0
                            )
                        );
            point.setSRID(4326);
        }
        return point;
    }
    
    /**
     * @param wkb the wkb to set
     */
    public void setWkb(String wkb) {
        this.wkb = wkb;
    }
    
    public void setWkb(com.vividsolutions.jts.geom.Point point) {
        this.wkb = WKBWriter.toHex(writer.write(point));
    }

    @Override
    public int compareTo(Object other) {
        return this.wkb.compareTo(((WkbPoint) other).getWkb());
    }

    @Override
    public int hashCode() {
        return 37 * Objects.hashCode(this.wkb);
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof WkbPoint)) {
            return false;
        }
        WkbPoint castOther = (WkbPoint) other;
        return  Objects.equals(this.wkb, castOther.getWkb());
    }
}
