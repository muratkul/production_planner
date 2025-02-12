package com.production_planner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter @Setter
@SoftDelete
public class ModelItem implements Serializable {

    @Serial
    private static final long serialVersionUID = -314948168461110872L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Model model;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Period period;

    @Column
    private int percent;

}
