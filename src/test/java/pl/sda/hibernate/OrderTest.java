package pl.sda.hibernate;

import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import pl.sda.hibernate.entity.Item;
import pl.sda.hibernate.entity.Order;

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
        Serializable id = getSession().save(order);
        getSession().flush();

        // then
        assertNotNull(id);
    }
}
