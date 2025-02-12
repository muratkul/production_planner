package com.production_planner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Period implements Serializable {

    @Serial
    private static final long serialVersionUID = -8713479356411657314L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "period", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value = "percent DESC")
    private Set<ModelItem> models;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Project project;

}
