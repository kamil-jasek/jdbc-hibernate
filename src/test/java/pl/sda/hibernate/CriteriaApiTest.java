package pl.sda.hibernate;

import static org.junit.Assert.assertEquals;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;
import pl.sda.hibernate.entity.Person;

public final class CriteriaApiTest extends BaseEntityTest {

    @Test
    public void testFilterPerson() {
        // given
        final Person person1 = new Person("Jan Kowalski", "34039292030");
        final Person person2 = new Person("Adam Nowak", "7300303004");
        saveAndFlush(person1);
        saveAndFlush(person2);

        // when -- select p from Person p where p.name like '?' or p.pesel like '?'
        List<Person> results = executePersonCriteriaQuery("Jan", "34");
        // then
        assertEquals(1, results.size());
        assertEquals(person1, results.get(0));

        // when
        results = executePersonCriteriaQuery("Adam", null);
        // then
        assertEquals(1, results.size());
        assertEquals(person2, results.get(0));

        // when
        results = executePersonCriteriaQuery(null, "73");
        // then
        assertEquals(1, results.size());
        assertEquals(person2, results.get(0));

        // when
        results = executePersonCriteriaQuery(null, null);
        // then
        assertEquals(2, results.size());
    }

    private List<Person> executePersonCriteriaQuery(String name, String pesel) {
        final CriteriaBuilder builder = getSession().getCriteriaBuilder();
        final CriteriaQuery<Person> query = builder.createQuery(Person.class);

        final Root<Person> root = query.from(Person.class);

        final ParameterExpression<String> nameParameter = builder.parameter(String.class);
        Predicate namePredicate = null;
        if (name != null) {
            // p.name like '?'
            namePredicate = builder.like(root.get("name"), nameParameter);
        }

        final ParameterExpression<String> peselParameter = builder.parameter(String.class);
        Predicate peselPredicate = null;
        if (pesel != null) {
            // p.pesel like '?'
            peselPredicate = builder.like(root.get("pesel"), peselParameter);
        }

        // where p.name like '?' or p.pesel like '?'
        if (name != null && pesel != null) {
            query.where(
                builder.or(namePredicate, peselPredicate));
            return getSession().createQuery(query)
                .setParameter(nameParameter, "%" + name + "%")
                .setParameter(peselParameter, pesel + "%")
                .getResultList();
        } else if (name != null) {
            query.where(namePredicate);
            return getSession().createQuery(query)
                .setParameter(nameParameter, "%" + name + "%")
                .getResultList();
        } else if (pesel != null) {
            query.where(peselPredicate);
            return getSession().createQuery(query)
                .setParameter(peselParameter, pesel + "%")
                .getResultList();
        } else {
            return getSession().createQuery(query).getResultList();
        }
    }
}
