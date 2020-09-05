package pl.sda.hibernate;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import org.hibernate.Session;
import org.junit.Test;
import pl.sda.hibernate.entity.Customer;
import pl.sda.hibernate.entity.Person;

public final class PersonTest extends BaseEntityTest {

    @Test
    public void testCreatePerson() {
        // given
        final Person person = new Person("Jan Kowalksi", "93848838823");

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
