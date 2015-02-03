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

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu"/>
 * @param   <T>     Type of the class
 * @param   <PK>    Type of the class' primary key
 */
public interface IGenericDao<T, PK extends Serializable> {
 
    /**
     * Save the given Entity to the Table (Resulting in SQL INSERT).
     * @param   entity  The Entity to write to the Table
     */
    public void save(T entity);
 
    /**
     * Make the given Entity persistent (Resulting in SQL UPDATE).
     * @param   entity  The Entity to make persistent
     */
    public void merge(T entity);
 
    /**
     * Delete the given Entity from the Table (Resulting in SQL DELETE).
     * @param   entity  The Entity to delete from the Table
     */
    public void delete(T entity);
 
    /**
     * Select all Entities satisfying the input {@link org.hibernate.Query}.
     * @param   query   The input Query
     * @return  All Entities satisfying the Query
     */
    public List<T> findMany(Query query);
 
    /**
     * Select just one Entity satisfying the input {@link org.hibernate.Query}.
     * @param   query   The input Query 
     * @return  One Entity satisfying the Query
     */
    public T findOne(Query query);
 
    /**
     * Select the Entity of the Type (class) from the Table identified by 
     * the specified Primary Key id.
     * @param   clazz   The type of the Entity
     * @param   id      The Primary Key of the given Entitytype
     * @return  The Entity of the Type with the specified id
     */
    public T findByID(Class clazz, PK id);
 
    /**
     * Select all from Table of Type class.
     * @param   clazz   The type of which to get all Entities
     * @return  All Entities of the given type
     */
    public List findAll(Class clazz);
}