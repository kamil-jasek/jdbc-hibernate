package pl.sda.hibernate;

import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import pl.sda.hibernate.entity.Item;

public final class ItemTest {

    private SessionFactory factory = HibernateUtil.getFactory();

    @Test
    public void testSaveItem() {
        // prepare
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // given
        Item item = new Item("item 1", BigDecimal.valueOf(10.0), 1);

        // when
        final Serializable id = session.save(item);

        // then
        assertNotNull(id);

        // end
        tx.commit();
    }
}
