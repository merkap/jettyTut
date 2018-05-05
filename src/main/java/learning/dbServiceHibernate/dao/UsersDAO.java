package db.dbServiceHibernate.dao;

import db.dbServiceHibernate.dataSets.UsersDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

public class UsersDAO {
    @PersistenceContext
    private EntityManager em;

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UsersDataSet getId(long id) throws HibernateException {
        return session.get(UsersDataSet.class, id);
    }


    public UsersDataSet getUser(String name) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UsersDataSet> cq = cb.createQuery(UsersDataSet.class);
        Metamodel m = (Metamodel) em.getMetamodel();
        EntityType<UsersDataSet> Users = m.entity(UsersDataSet.class);
        Root<UsersDataSet> users = cq.from(UsersDataSet.class);
        cq.where(cb.equal(users.get(Users.getName()), name));
        return em.createQuery(cq).getSingleResult();
    }

    public long insertUser(String name, String pass) throws HibernateException {
        return (Long) session.save(new UsersDataSet(name, pass));
    }

}


