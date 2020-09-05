package pl.sda.hibernate.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer_orders")
public final class Order {

    public enum Status {
        IN_PROGRESS, SENT, DELIVERED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

    public Order() {
        this.status = Status.IN_PROGRESS;
        this.items = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void sendItems() {
        if (status.equals(Status.IN_PROGRESS)) {
            status = Status.SENT;
        }
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public void addItem(Item item) {
        if (!items.contains(item)) {
            items.add(item);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
