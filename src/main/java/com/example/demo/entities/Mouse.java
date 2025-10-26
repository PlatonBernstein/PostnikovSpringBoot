package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import java.util.UUID;

@Entity
@Table(name = "mouses")
@Data
@SQLDelete(sql = "UPDATE mouses SET is_deleted=true WHERE id=?")
@FilterDef(name = "deletedProductFilter", parameters = @ParamDef(name = "isDeleted", type = boolean.class))
@Filter(name = "deletedProductFilter", condition = "is_deleted = :isDeleted")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mouse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean is_deleted = false;
}
