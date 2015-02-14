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
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;

/**
 * DAO Class for the {@link de.hsmainz.gi.types.Beacon} Class.
 * 
 * 
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>
 */
public class BeaconDao extends GenericDao<Beacon, Integer> {

    /**
     * Find all Beacons.
     * 
     * @return  all Beacons
     */
    public List<Beacon> findAll() {
        return this.findAll(Beacon.class);
    }

    /**
     * Find Beacons by their manufacturer UUID.
     * 
     * @param   uuid    the manufacturer id
     * @return  all Beacons from the given manufacturer
     */
    public List<Beacon> findByUuid(String uuid) {
        Query q = getCurrentSession().createQuery("from Beacon where uuid = :uuid");
        q.setString("uuid", uuid);
        return this.findMany(q);
    }

    /**
     * Find Beacons by the MAJOR id.
     * 
     * @param   major   the major id
     * @return  all Beacons matching this MAJOR id
     */
    public List<Beacon> findByMajor(int major) {
        Query q = getCurrentSession().createQuery("from Beacon where major = :major");
        q.setInteger("major", major);
        return this.findMany(q);
    }

    /**
     * Find Beacons by the MINOR id.
     * 
     * @param   minor   the MINOR id
     * @return  all Beacons matching this MINOR id
     */
    public List<Beacon> findByMinor(int minor) {
        Query q = getCurrentSession().createQuery("from Beacon where minor = :minor");
        q.setInteger("minor", minor);
        return this.findMany(q);
    }

    /**
     * Find Beacons by their Database serial ID.
     * 
     * @param   id      the database ID
     * @return  the Beacon with the given ID
     */
    public Beacon findById(int id) {
        return this.findByID(Beacon.class, id);
    }
    
    /**
     * Find a Beacon from a Beacon without serial ID.
     * 
     * @param   beacon  the Beacon to get the ID for
     * @return  the Beacon with its id
     */
    public Beacon find(Beacon beacon) {
        return findByUuidMajorMinor(beacon.getUuid(), beacon.getMajor(), beacon.getMinor());
    }
    
    /**
     * Find multiple Beacons without serial IDs.
     * 
     * @param   beacons a List of Beacons without IDs
     * @return  a List of all the Beacons with their IDs
     */
    public List<Beacon> findMultiple(List<Beacon> beacons) {
        List<Beacon> result = new ArrayList<>();
        beacons.forEach(beacon -> result.add(find(beacon)));
        return result;
    }
    
    /**
     * Find a Beacon by its UUID, MAJOR and MINOR fields.
     * 
     * @param   uuid    the Beacons manufacturer ID
     * @param   major   the Beacons MAJOR id
     * @param   minor   the Beacons MINOR id
     * @return  the Beacon matching all three input fields
     */
    public Beacon findByUuidMajorMinor(String uuid, int major, int minor) {
        Query q = getCurrentSession().createQuery(
            "from Beacon where uuid = :uuid and major = :major and minor = :minor"
        );
        q.setString("uuid", uuid);
        q.setInteger("major", major);
        q.setInteger("minor", minor);
        return this.findOne(q);
    }
}
