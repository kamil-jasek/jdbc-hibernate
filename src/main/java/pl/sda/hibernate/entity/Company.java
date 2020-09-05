package pl.sda.hibernate.entity;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("COMPANY")
public final class Company extends Customer {

    private String vat;

    // for hibernate
    protected Company() {}

    public Company(String name, String vat) {
        super(name);
        requireNonNull(vat);
        this.vat = vat;
    }

    public String getVat() {
        return vat;
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
        Company company = (Company) o;
        return vat.equals(company.vat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vat);
    }
}
