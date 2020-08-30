package pl.sda.hibernate;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.junit.Assert;
import org.junit.Test;

public final class HibernateTest {

    private SessionFactory factory = HibernateUtil.getFactory();

    @Test
    public void testConnection() {
        // 1. open session
        Session session = factory.openSession();
        // 2. begin tx
        final Transaction tx = session.beginTransaction();

        final NativeQuery query = session.createSQLQuery("SELECT 1");
        List<Object> result = query.getResultList();

        assertEquals(BigInteger.valueOf(1), result.get(0));
        tx.rollback();
    }
}
