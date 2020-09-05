package pl.sda.hibernate.entity;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PERSON")
public final class Person extends Customer {

    private String pesel;

    // for hibernate
    protected Person() {}

    public Person(String name, String pesel) {
        super(name);
        requireNonNull(pesel);
        this.pesel = pesel;
    }

    public String getPesel() {
        return pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Person person = (Person) o;
        return pesel.equals(person.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pesel);
    }
}
