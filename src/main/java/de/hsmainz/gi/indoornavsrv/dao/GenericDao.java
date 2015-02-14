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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Abstract Class that implements the {@link de.hsmainz.gi.indoornavsrv.dao.IGenericDao}
 * Interface and adds concrete implementations for the Transaction-Layer in the
 * {@link de.hsmainz.gi.indoornavsrv.services}
 * 
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">email</a>
 * @param <T> Type of the class
 * @param <PK> Type of the class' primary key
 */
public abstract class GenericDao<T, PK extends Serializable> implements IGenericDao<T, PK> {

    private static final SessionFactory sessionFactory;
    static {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }
    private Session     currentSession;
    private Transaction currentTransaction;

    /**
     * Open a new Session.
     * @return  a new Session
     */
    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    /**
     * Open a new Session and begin Transaction.
     * @return  the new session
     */
    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    /**
     * Close the current Session.
     */
    public void closeCurrentSession() {
        currentSession.close();
    }

    /**
     * Commit the current Transaction and close the current Session.
     */
    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    /**
     * Get the configured SessionFactory.
     * @return  the sessionfactory
     */
    private static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Get the current Session.
     * @return  the current Session
     */
    public Session getCurrentSession() {
        return currentSession;
    }

    /**
     * Set the current Session.
     * @param   currentSession  the session to replace the current Session
     */
    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    /**
     * Get the current Transaction.
     * @return  the current Transaction
     */
    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    /**
     * Set the current Transaction.
     * @param   currentTransaction  the Transaction to replace the current Transaction
     */
    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    /**
     * Save the given Entity to the Table (Resulting in SQL INSERT).
     *
     * @param   entity  The Entity to write to the Table
     */
    @Override
    public void save(T entity) {
        this.currentSession.saveOrUpdate(entity);
    }

    /**
     * Make the given Entity persistent (Resulting in SQL UPDATE).
     *
     * @param   entity  The Entity to make persistent
     */
    @Override
    public void merge(T entity) {
        this.currentSession.merge(entity);
    }

    /**
     * Delete the given Entity from the Table (Resulting in SQL DELETE).
     *
     * @param   entity  The Entity to delete from the Table
     */
    @Override
    public void delete(T entity) {
        this.currentSession.delete(entity);
    }

    /**
     * Select all Entities satisfying the input {@link org.hibernate.Query}.
     *
     * @param   query   The input Query
     * @return  All Entities satisfying the Query
     */
    @Override
    public List<T> findMany(Query query) {
        List<T> t;
        t = (List<T>) query.list();
        return t;
    }

    /**
     * Select just one Entity satisfying the input {@link org.hibernate.Query}.
     *
     * @param   query   The input Query
     * @return  One Entity satisfying the Query
     */
    @Override
    public T findOne(Query query) {
        T t;
        t = (T) query.uniqueResult();
        return t;
    }

    /**
     * Select the Entity of the Type (class) from the Table identified by the
     * specified Primary Key id.
     *
     * @param   clazz   The type of the Entity
     * @param   id      The Primary Key of the given Entitytype
     * @return  The Entity of the Type with the specified id
     */
    @Override
    public T findByID(Class clazz, PK id) {
        Session hibernateSession = this.currentSession;
        T t = null;
        t = (T) hibernateSession.get(clazz, id);
        return t;
    }

    /**
     * Select all from Table of Type class.
     *
     * @param   clazz   The type of which to get all Entities
     * @return  All Entities of the given type
     */
    @Override
    public List findAll(Class clazz) {
        Session hibernateSession = this.currentSession;
        List list = null;
        Query query = hibernateSession.createQuery("from " + clazz.getName());
        list = query.list();
        return list;
    }
}
