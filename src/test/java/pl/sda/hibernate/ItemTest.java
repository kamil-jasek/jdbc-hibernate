package pl.sda.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.sda.hibernate.entity.Item;

public final class ItemTest extends BaseEntityTest {

    @Test
    public void testSaveItem() {
        // given
        Item item = new Item("item 1", BigDecimal.valueOf(10.0), 1);

        // when
        final Serializable id = getSession().save(item);
        getSession().flush();

        // then
        assertNotNull(id);
    }

    @Test
    public void testReadItem() {
        // given
        Item item = new Item("item 1", new BigDecimal("10.00"), 1);

        // when
        final Serializable id = getSession().save(item);
        getSession().flush();
        getSession().clear();

        // then
        Item readItem = getSession().get(Item.class, id);
        assertEquals(item, readItem);
    }
}
