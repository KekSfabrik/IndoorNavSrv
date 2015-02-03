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

import de.hsmainz.gi.types.Site;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu"/>
 */
public class SiteDao extends GenericDao<Site, Integer> {

    public List<Site> findAll() {
        return this.findAll(Site.class);
    }
    
    public List<Site> findByApproximateName(String name) {
            Query q = getCurrentSession().createQuery("from Site where name like :name");
            q.setParameter("name", "%"+name+"%");
            return this.findMany(q);
    }
    
    public Site findByName(String name) {
            Query q = getCurrentSession().createQuery("from Site where name = :name");
            q.setParameter("name", name);
            return this.findOne(q);
    }
    
    public Site findById(int id) {
        return this.findByID(Site.class, id);
    }
    
}
