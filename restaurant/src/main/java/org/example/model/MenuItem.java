package org.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class MenuItem extends BaseEntity<Long> {
    private String name;
    private List<SubMenuItem> subMenuItems;

    public MenuItem(Long id, String name, List<SubMenuItem> subMenuItems) {
        super(id);
        this.name = name;
        this.subMenuItems = subMenuItems;
    }
}
