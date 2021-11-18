package org.example.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class SubMenuItem extends BaseEntity<Long> {
    private String name;
    private VAT vat;
    private double price;

    public SubMenuItem(Long id, String name, VAT vat, double price) {
        super(id);
        this.price = price;
        this.name = name;
        this.vat = vat;
    }

    public double getPriceWithVAT() {
        return price + price * vat.getPercent();
    }
}
