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
import de.hsmainz.gi.types.Site;
import de.hsmainz.gi.types.WkbLocation;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * The LOCATOR WebService is meant to be accessible by anyone and only packs 
 * access to READ functions. The publicly available functions can be accessed to
 * allow navigation and positioning for everyone.
 * 
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>
 */
@WebService
public interface IBeaconLocatorService {
    
    
    /**
     * Get a the {@link de.hsmainz.gi.types.Site}s by it\'s name.
     * @param   name  the name of the Site to search for
     * @return  a Site Object
     */
    @WebMethod(operationName = "getSite")
    @WebResult(name="getSiteResponse")
    public Site getSite(
            @WebParam(name = "name") String name
    );
    
    /**
     * Get a List of {@link de.hsmainz.gi.types.Site}s by by a wildcarded
     * name (SQL LIKE "%name%").
     * @param   name  the approximate name of the Site to search for
     * @return  a List of Site Object
     */
    @WebMethod(operationName = "getSiteByApproximateName")
    @WebResult(name="getSiteByApproximateNameResponse")
    public List<Site> getSiteByApproximateName(
            @WebParam(name = "name") String name
    );
    /**
     * Get a List of {@link de.hsmainz.gi.types.Site}s where the provided
     * {@link de.hsmainz.gi.types.Beacon} is registered to.
     * @param   beacon  the Beacon to search for
     * @return  a List of Sites where this Beacon is registered
     */
    @WebMethod(operationName = "getSitesFromBeacon")
    @WebResult(name="getSiteFromBeaconResponse")
    public List<Site> getSites(
            @WebParam(name = "beacon") Beacon beacon
    );

    /**
     * Get a List of {@link de.hsmainz.gi.types.Site}s where the provided
     * {@link de.hsmainz.gi.types.Beacon}s are registered to.
     * @param   beacons   a list of Beacons to search for 
     * @return  a List of Sites where all provided Beacons are registered
     */
    @WebMethod(operationName = "getSitesFromBeaconList")
    @WebResult(name="getSiteFromBeaconListResponse")
    public List<Site> getSites(
            @WebParam(name = "beacons") List<Beacon> beacons
    );

    /**
     * Get the {@link de.hsmainz.gi.types.Beacon} Object by uuid, major 
     * and minor.
     * @param   uuid    the beacons manufacturer uuid
     * @param   major   the beacons major id
     * @param   minor   the beacons minor id
     * @return  the Beacon object if it exists
     */
    @WebMethod(operationName = "getBeaconFromUuidMajorMinor")
    @WebResult(name="getBeaconFromUuidMajorMinorResponse")
    public Beacon getBeacon(
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "major") int major,
            @WebParam(name = "minor") int minor
    );

    /**
     * Get the ID of a {@link de.hsmainz.gi.types.Beacon}.
     * @param   beacon     a List of Beacons to get IDs for
     * @return  a List of Beacons that exist
     */
    @WebMethod(operationName = "getBeacon")
    @WebResult(name="getBeaconResponse")
    public Beacon getBeacon(
            @WebParam(name = "beacon") Beacon beacon
    );

    /**
     * Get the IDs for a bunch of {@link de.hsmainz.gi.types.Beacon}s.
     * and minor.
     * @param   beacons     a List of Beacons to get IDs for
     * @return  a List of Beacons that exist
     */
    @WebMethod(operationName = "getBeacons")
    @WebResult(name="getBeaconsResponse")
    public List<Beacon> getBeacons(
            @WebParam(name = "beacons") List<Beacon> beacons
    );
    
    /**
     * Get the {@link com.vividsolutions.jts.geom.Point} at the provided
     * {@link de.hsmainz.gi.types.Site} where the provided
     * {@link de.hsmainz.gi.types.Beacon} is positioned.
     * @param   site    the Site of the Beacon
     * @param   beacon  the Beacon
     * @return  the Coordinate of the Beacon on the Site
     */
    @WebMethod(operationName = "getCoordinate")
    @WebResult(name="getCoordinateResponse")
    public WkbPoint getCoordinate(
            @WebParam(name = "site") Site site,
            @WebParam(name = "beacon") Beacon beacon
    );

    /**
     * Get all {@link de.hsmainz.gi.types.Location}s of 
     * {@link de.hsmainz.gi.types.Beacon}s at the specified 
     * {@link de.hsmainz.gi.types.Site}.
     * @param   site    the Site of the Locations
     * @return  all Locations of Beacons at the Site
     */
    @WebMethod(operationName = "getBeaconLocationsFromSite")
    @WebResult(name="getBeaconLocationsFromSiteResponse")
    public List<WkbLocation> getBeaconLocations(
            @WebParam(name = "site") Site site
    );

    /**
     * Attempt to find all {@link de.hsmainz.gi.types.Location}s for
     * the provided List of {@link de.hsmainz.gi.types.Beacon}s.
     * @param   beacons  the Beacons for which a Site should be found
     * @return  all Locations of Beacons on the Site determined by the input
     */
    @WebMethod(operationName = "getBeaconLocationsFromBeaconList")
    @WebResult(name="getBeaconLocationsFromBeaconListResponse")
    public List<WkbLocation> getBeaconLocations(
            @WebParam(name = "beacons") List<Beacon> beacons
    );

}
