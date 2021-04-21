package com.mercadolibre.fernandez_federico.models;

<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="stock")
public class StockDealer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idStockDealer;
    private Integer quantity;
    private Integer minStock;


=======
public class StockDealer {
>>>>>>> 3aa5f7e2601ca8550c812adf9614bbb0812d5bd1
}
