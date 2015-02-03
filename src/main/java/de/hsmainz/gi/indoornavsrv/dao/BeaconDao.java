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
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>
 */
public class BeaconDao extends GenericDao<Beacon, Integer> {

    public List<Beacon> findAll() {
        return this.findAll(Beacon.class);
    }

    public List<Beacon> findByUuid(String uuid) {
        Query q = getCurrentSession().createQuery("from Beacon where uuid = :uuid");
        q.setString("uuid", uuid);
        return this.findMany(q);
    }

    public List<Beacon> findByMajor(int major) {
        Query q = getCurrentSession().createQuery("from Beacon where major = :major");
        q.setInteger("major", major);
        return this.findMany(q);
    }

    public List<Beacon> findByMinor(int minor) {
        Query q = getCurrentSession().createQuery("from Beacon where minor = :minor");
        q.setInteger("minor", minor);
        return this.findMany(q);
    }

    public Beacon findById(int id) {
        return this.findByID(Beacon.class, id);
    }
    
    public Beacon find(Beacon beacon) {
        return findByUuidMajorMinor(beacon.getUuid(), beacon.getMajor(), beacon.getMinor());
    }
    
    public List<Beacon> findMultiple(List<Beacon> beacons) {
        List<Beacon> result = new ArrayList<>();
        beacons.forEach(beacon -> result.add(find(beacon)));
        return result;
    }
    
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
