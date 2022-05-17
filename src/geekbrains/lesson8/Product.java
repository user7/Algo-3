package geekbrains.lesson8;

import java.util.Objects;

public class Product {
    private final int id;
    private final String title;

    public Product(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String toString() {
        return "Product{title='" + this.title + "'}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Product product = (Product) o;
            return this.id == product.id && Objects.equals(this.title, product.title);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.id;
    }
}
