package pl.sda.hibernate.entity;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
public final class Item {

    @Id
    private long id;

    @Column(name = "item_name", nullable = false, length = 50)
    private String name;

    @Column(nullable = false, precision = 19, scale = 2) // 2939939399393.23
    private BigDecimal price;

    private int quantity;

    public Item(String name, BigDecimal price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return quantity == item.quantity &&
            name.equals(item.name) &&
            price.equals(item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity);
    }

    @Override
    public String toString() {
        return "Item{" +
            "name='" + name + '\'' +
            ", price=" + price +
            ", quantity=" + quantity +
            '}';
    }
}