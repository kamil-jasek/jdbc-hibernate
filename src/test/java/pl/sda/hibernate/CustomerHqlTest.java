package pl.sda.hibernate;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import pl.sda.hibernate.entity.Company;
import pl.sda.hibernate.entity.Customer;
import pl.sda.hibernate.entity.Order;
import pl.sda.hibernate.entity.Order.Status;

public final class CustomerHqlTest extends BaseEntityTest {

    @Test
    public void testHqlCustomersWithOrdersInProgress() {
        // given
        final Customer customer = new Company("company s.a.", "PL093923992");
        final Order order = new Order();
        customer.addOrder(order);

        final Session session = getSession();
        session.save(customer);
        session.flush();
        session.clear();

        // when
        final Query<Customer> query = session
            .createQuery("select c from Customer c inner join c.orders o where o.status = :status", Customer.class);
        query.setParameter("status", Status.IN_PROGRESS);
        final List<Customer> customers = query.getResultList();

        // then
        assertEquals(1, customers.size());
        assertEquals(customer, customers.get(0));
    }
}
