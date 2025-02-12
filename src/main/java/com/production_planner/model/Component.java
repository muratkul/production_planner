package com.production_planner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter @Setter
@SoftDelete
public class Component implements Serializable {

    @Serial
    private static final long serialVersionUID = 2373804970105973603L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

}
