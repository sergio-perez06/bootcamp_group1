package com.mercadolibre.fernandez_federico.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="discount_type")
@RequiredArgsConstructor
@NoArgsConstructor
public class DiscountType {
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    @NotNull(message = "type no puede ser nulo")
    private String type;

    @NonNull
    @Column(nullable = false)
    @NotNull(message = "rate no puede ser nulo")
    private Integer rate;

    @OneToMany(mappedBy = "discountType")
    @JsonManagedReference(value = "record-discounttype" )
    private List<Record> records;
}
