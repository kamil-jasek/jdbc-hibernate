package pl.sda.hibernate;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import pl.sda.hibernate.entity.Company;
import pl.sda.hibernate.entity.Customer;
import pl.sda.hibernate.entity.Item;
import pl.sda.hibernate.entity.Order;
import pl.sda.hibernate.entity.Order.Status;

public final class CustomerHqlTest extends BaseEntityTest {

    @Test
    public void testHqlCustomersWithOrdersInProgress() {
        // given
        final Customer customer = new Company("company s.a.", "PL093923992");
        final Order order = new Order();
        customer.addOrder(order);

        saveAndFlush(customer);

        // when
        final Query<Customer> query = getSession()
            .createQuery("select c from Customer c inner join c.orders o where o.status = :status", Customer.class);
        query.setParameter("status", Status.IN_PROGRESS);
        final List<Customer> customers = query.getResultList();

        // then
        assertEquals(1, customers.size());
        assertEquals(customer, customers.get(0));
    }

    @Test
    public void testHqlSumOrdersOfAllCompanies() {
        // given
        final Customer customer = new Company("company s.a.", "PL093923992");
        final Order order1 = new Order();
        order1.addItem(new Item("item1", new BigDecimal("23.10"), 1));
        order1.addItem(new Item("item2", new BigDecimal("55.10"), 2));
        customer.addOrder(order1);

        final Order order2 = new Order();
        order2.addItem(new Item("item3", new BigDecimal("99.00"), 1));
        customer.addOrder(order2);

        saveAndFlush(customer);

        // when
        final BigDecimal result = getSession()
            .createQuery("select sum(i.price) from Company c inner join c.orders o inner join o.items i", BigDecimal.class)
            .getSingleResult();

        // then
        Assert.assertEquals(new BigDecimal("177.20"), result);
    }
}
