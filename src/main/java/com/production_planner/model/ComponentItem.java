package com.production_planner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

import java.io.Serial;
import java.io.Serializable;


@Entity
@Getter @Setter
@SoftDelete
public class ComponentItem implements Serializable {

    @Serial
    private static final long serialVersionUID = -6271797125992431966L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Component component;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Model model;

    @Column
    private int quantity;

}
