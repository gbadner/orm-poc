package com.redhat.bz.das;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Base Data Access Service class. Functionality of the searches can be replicated using the criteria API from JPA or a simple
 * query by example
 *
 * @param <E>  Entity type.
 * @param <PK> Primary key type.
 */
public abstract class AbstractDAS<E, PK extends Serializable> implements Serializable {

    @Inject
    protected transient EntityManager em;

    protected Class<E> entityType;
    protected Class<PK> idType;


    public E update(E entity) {
        checkProperInit();
        return em.merge(entity);
    }

    public E updateAndFlush(E entity) {
        checkProperInit();
        final E updatedEntity = this.update(entity);
        this.flush();
        return updatedEntity;
    }


    /**
     * Persist (new entity) or merge the given entity.
     *
     * @param entity Entity to save.
     * @return Returns the modified entity.
     */
    public E persist(E entity) {
        checkProperInit();
        em.persist(entity);
        return entity;
    }

    /**
     * {@link #persist(Object)}s the given entity and flushes the persistence context afterwards.
     *
     * @param entity Entity to save.
     * @return Returns the modified entity.
     */
    public E persistAndFlush(E entity) {
        checkProperInit();
        final E savedEntity = this.persist(entity);
        this.flush();
        return savedEntity;
    }

    /**
     * Convenience access to {@link javax.persistence.EntityManager#remove(Object)}.
     *
     * @param entity Entity to remove.
     */
    public void remove(E entity) {
        checkProperInit();
        em.remove(entity);
    }

    /**
     * Convenience access to {@link javax.persistence.EntityManager#refresh(Object)}.
     *
     * @param entity Entity to refresh.
     */
    public void refresh(E entity) {
        checkProperInit();
        em.refresh(entity);
    }

    /**
     * Convenience access to {@link javax.persistence.EntityManager#flush()}.
     */
    public void flush() {
        checkProperInit();
        em.flush();
    }

    /**
     * Entity lookup by primary key. Convenicence method around {@link javax.persistence.EntityManager#find(Class, Object)}.
     *
     * @param primaryKey DB primary key.
     * @return Entity identified by primary or null if it does not exist.
     */
    public E findBy(PK primaryKey) {
        checkProperInit();
        return em.find(this.entityType, primaryKey);
    }

    /**
     * Lookup all existing entities of entity class {@code <E>}.
     *
     * @return List of entities, empty if none found.
     */
    public List<E> findAll() {
        final TypedQuery<E> typedQuery = em.createQuery(createCriteria());
        return typedQuery.getResultList();
    }

    protected CriteriaQuery<E> createCriteria() {
        checkProperInit();
        final String entityName = em.getMetamodel().entity(this.entityType).getName();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> query = cb.createQuery(this.entityType);
        return query.select(query.from(this.entityType));
    }

    /**
     * Count all existing entities of entity class {@code <E>}.
     *
     * @return Counter.
     */
    public Long count() {
        checkProperInit();
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = cb.createQuery(Long.class);
        query.select(cb.count(query.from(this.entityType)));

        return em.createQuery(query).getSingleResult();
    }

    public void detach(E entityType) {
        em.detach(entityType);
    }

    private void checkProperInit() {
        if (this.idType == null || this.entityType == null) {
            throw new IllegalStateException("DAS not properly initialized.");
        }
    }

}
