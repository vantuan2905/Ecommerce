package org.example.ecom00.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    /** Unique id for the product. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /** The name of the product. */
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    /** The short description of the product. */
    @Column(name = "short_description", nullable = false)
    private String shortDescription;
    /** The long description of the product. */
    @Column(name = "long_description")
    private String longDescription;
    /** The price of the product. */
    @Column(name = "price", nullable = false)
    private Double price;
    //relation inventory
    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE, optional = false, orphanRemoval = true)
    private Inventory inventory;

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
