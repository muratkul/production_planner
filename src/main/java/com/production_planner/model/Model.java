package com.production_planner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter @Setter
@SoftDelete
public class Model implements Serializable {

    @Serial
    private static final long serialVersionUID = -2496573223463853858L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value = "id")
    private Set<ComponentItem> components;



}
