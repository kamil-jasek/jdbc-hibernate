package pl.sda.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.Session;
import org.junit.Test;
import pl.sda.hibernate.entity.Item;
import pl.sda.hibernate.entity.Order;
import pl.sda.hibernate.entity.Order.Status;

public final class OrderTest extends BaseEntityTest {

    @Test
    public void testSaveOrder() {
        // given
        Order order = new Order();

        // when
        final Serializable id = getSession().save(order);

        // then
        assertNotNull(id);
    }

    @Test
    public void testAddItems() {
        // given
        Order order = new Order();
        order.addItem(new Item("item1", new BigDecimal("5.23"), 1));

        // when
        final Session session = getSession();

        Serializable id = session.save(order);
        session.flush();
        session.clear();

        // then
        Order readOrder = session.get(Order.class, id);
        assertEquals(order, readOrder);
        assertEquals(1, readOrder.getItems().size());
    }

    @Test
    public void testUpdateOrder() {
        // given
        Order order = new Order();
        order.addItem(new Item("item1", new BigDecimal("5.23"), 1));
        final Session session = getSession();
        final Serializable id = session.save(order);
        session.flush();
        session.clear();

        // when
        Order updateOrder = session.get(Order.class, id);
        updateOrder.sendItems();
        session.flush();
        session.clear();

        // then
        Order readOrder = session.get(Order.class, id);
        assertEquals(Status.SENT, readOrder.getStatus());
    }
}
