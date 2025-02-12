package com.production_planner.model;

import com.production_planner.dto.ManagementType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter @Setter
@SoftDelete
public class Project implements Serializable {

    @Serial
    private static final long serialVersionUID = -5043831567754957435L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ManagementType managementType;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value = "id")
    private List<Period> periods;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value = "id")
    private List<TargetQuantity> quantities;

}
