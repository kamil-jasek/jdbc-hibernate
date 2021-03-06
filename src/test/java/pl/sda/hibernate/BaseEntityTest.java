package pl.sda.hibernate;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;

abstract class BaseEntityTest {

    private SessionFactory factory = HibernateUtil.getFactory();
    private Session session;
    private Transaction tx;

    @Before
    public void before() {
        // prepare
        session = factory.openSession();
        tx = session.beginTransaction();
    }

    @After
    public void after() {
        tx.rollback();
        session.close();
    }

    protected Session getSession() {
        return session;
    }

    protected Serializable saveAndFlush(Object object) {
        final Serializable id = session.save(object);
        session.flush();
        session.clear();
        return id;
    }
}
