package pl.sda.hibernate;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.Session;
import org.junit.Test;
import pl.sda.hibernate.entity.Customer;
import pl.sda.hibernate.entity.Item;
import pl.sda.hibernate.entity.Order;
import pl.sda.hibernate.entity.Person;

public final class PersonTest extends BaseEntityTest {

    @Test
    public void testCreatePerson() {
        // given
        final Person person = new Person("Jan Kowalksi", "93848838823");
        final Order order = new Order();
        order.addItem(new Item("item1", new BigDecimal("25.50"), 1));
        person.addOrder(order);

        // when
        final Session session = getSession();
        final Serializable id = session.save(person);
        session.flush();
        session.clear();

        // then
        final Customer readPerson = session.get(Customer.class, id);
        assertEquals(person, readPerson);
    }
}
