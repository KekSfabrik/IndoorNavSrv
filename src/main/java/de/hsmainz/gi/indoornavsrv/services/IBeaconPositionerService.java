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
import de.hsmainz.gi.types.Site;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * The POSITIONER WebService is meant to be available only to administrators with
 * the privileges of writing to the Database. It enables adding/removing and 
 * modification of Database entries.
 * 
 * 
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>
 */
@WebService
public interface IBeaconPositionerService {
    
    /**
     * Add a new {@link de.hsmainz.gi.types.Site} to the System.
     * @param   site    the Site to add
     * @return  whether site was successfully added
     */
    @WebMethod(operationName = "addSite")
    @WebResult(name="addSiteResponse")
    public boolean addSite(
            @WebParam(name = "site") Site site
    );
    
    /**
     * Wrapped call for {@link #addSite(de.hsmainz.gi.indoornavsrv.types.Site) } with
     * the implication of a new {@link de.hsmainz.gi.types.Site}
     * being constructed before adding it to the System.
     * @param   name    the new Site's name
     * @return  whether the site was successfully added
     */
    @WebMethod(operationName = "addSiteFromName")
    @WebResult(name="addSiteFromNameResponse")
    public boolean addSite(
            @WebParam(name = "name") String name
    );
    
    
    /**
     * Unregister a {@link #addSite(de.hsmainz.gi.indoornavsrv.types.Site) } from the
     * System and delete it from the Database.
     * @param   site    the Site to remove
     * @return  whether the site was successfully deleted from the database
     */
    @WebMethod(operationName = "deleteSite")
    @WebResult(name="deleteSiteResponse")
    public boolean deleteSite(
            @WebParam(name = "site") Site site
    );
    
    /**
     * Register a new {@link de.hsmainz.gi.types.Beacon} to the System.
     * @param   beacon  the Beacon to register to the System
     * @return  whether the Beacon was successully registered
     */
    @WebMethod(operationName = "addBeacon")
    @WebResult(name="addBeaconResponse")
    public boolean addBeacon(
            @WebParam(name = "beacon") Beacon beacon
    );
    
    /**
     * wrapped call to {@link #addBeacon(de.hsmainz.gi.indoornavsrv.types.Beacon) }
     * with a new new {@link de.hsmainz.gi.types.Beacon} being constructed
     * before registering with the System.
     * @param   uuid    the Beacons manufacturer uuid
     * @param   major   the Beacons major version
     * @param   minor   the Beacons minor version
     * @return  whether the Beacon was successully registered
     */
    @WebMethod(operationName = "addBeaconFromUuidMajorMinor")
    @WebResult(name="addBeaconFromUuidMajorMinorResponse")
    public boolean addBeacon(
            @WebParam(name = "uuid") String uuid, 
            @WebParam(name = "major") int major, 
            @WebParam(name = "minor") int minor
    );
    
    /**
     * Unregister a {@link de.hsmainz.gi.types.Beacon} from a specific
     * {@link de.hsmainz.gi.types.Site}
     * @param   beacon  the Beacon to unregister from the Site
     * @param   site    the Site to remove the Beacon from
     * @return  whether successful
     */
    @WebMethod(operationName = "removeBeaconFromSite")
    @WebResult(name="removeBeaconFromSiteResponse")
    public boolean removeBeaconFromSite(
            @WebParam(name = "beacon") Beacon beacon,
            @WebParam(name = "site") Site site
    );
    
    /**
     * Unregister a {@link de.hsmainz.gi.types.Beacon} from a specific
     * {@link de.hsmainz.gi.types.Site} by its 
     * {@link de.hsmainz.gi.types.WkbLocation}.
     * @param   location    the location to remove
     * @return  whether successful
     */
    @WebMethod(operationName = "removeLocation")
    @WebResult(name="removeLocationResponse")
    public boolean removeBeaconFromSite(
            @WebParam(name = "location") WkbLocation location
    );
    
    /**
     * Unregister a {@link de.hsmainz.gi.types.Beacon} from the System
     * and remove it from the Database.
     * @param   beacon  the Beacon to unregister from the System
     * @return  whether successful
     */
    @WebMethod(operationName = "deleteBeacon")
    @WebResult(name="deleteBeaconResponse")
    public boolean deleteBeacon(
            @WebParam(name = "beacon") Beacon beacon
    );
    
    /**
     * Place a {@link de.hsmainz.gi.types.Beacon} on a 
     * {@link de.hsmainz.gi.types.Site} with a 
     * {@link com.vividsolutions.jts.geom.Point}
     * @param   site        the Site to place the Beacon on
     * @param   beacon      the Beacon that is being placed
     * @param   coordinate  the Beacons Coordinate on the given Site
     * @return  whether successful
     */
    @WebMethod(operationName = "placeBeacon")
    @WebResult(name="placeBeaconResponse")
    public boolean placeBeacon(
            @WebParam(name = "site") Site site, 
            @WebParam(name = "beacon") Beacon beacon, 
            @WebParam(name = "coordinate") WkbPoint coordinate
    );

    
    /**
     * Place a {@link de.hsmainz.gi.types.Beacon} on a 
     * {@link de.hsmainz.gi.types.Site} with a 
     * {@link com.vividsolutions.jts.geom.Point}
     * @param   location    the Location (Site, Beacon, WkbPoint)
     * @return  whether successful
     */
    @WebMethod(operationName = "placeBeaconAtLocation")
    @WebResult(name="placeBeaconAtLocationResponse")
    public boolean placeBeacon(
            @WebParam(name = "location") WkbLocation location
    );
    
    /**
     * Replace an existing {@link de.hsmainz.gi.types.Beacon} with a 
     * new one at the same {@link com.vividsolutions.jts.geom.Point}.
     * @param   site        the Site on which to replace the Beacon
     * @param   oldBeacon   the old Beacon
     * @param   newBeacon   the new Beacon
     * @return  whether or not the oldBeacon could be replaced by newBeacon
     */
    @WebMethod(operationName = "replaceBeacon")
    @WebResult(name="replaceBeaconResponse")
    public boolean replaceBeacon(
            @WebParam(name = "site") Site site,
            @WebParam(name = "oldBeacon") Beacon oldBeacon, 
            @WebParam(name = "newBeacon") Beacon newBeacon
    );

    /**
     * Relocate an existing {@link de.hsmainz.gi.types.Beacon} to a 
     * different {@link com.vividsolutions.jts.geom.Point} on another 
     * {@link de.hsmainz.gi.types.Site}.
     * @param   fromSite        from which Site the Beacon is moved
     * @param   toSite          to which Site the Beacon is moved to
     * @param   beacon          the Beacon that is being moved
     * @param   toCoordinate    the Beacons Coordinate on the new Site
     * @return  whether successful
     */
    @WebMethod(operationName = "relocateBeacon")
    @WebResult(name="relocateBeaconResponse")
    public boolean relocateBeacon(
            @WebParam(name = "fromSite") Site fromSite, 
            @WebParam(name = "toSite") Site toSite, 
            @WebParam(name = "beacon") Beacon beacon, 
            @WebParam(name = "toCoordinate") WkbPoint toCoordinate
    );
}
