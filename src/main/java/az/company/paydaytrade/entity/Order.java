package az.company.paydaytrade.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
     User user;

    @ManyToOne
    @JoinColumn(name = "stock_id",nullable = false)
    Stock stock;

    @Column(name = "COUNT")
    @ColumnDefault(value = "1")
    Integer count;

    @Column(name = "TOTAL_VALUE")
    Double value;

    @Column(name = "STATUS")
    @ColumnDefault(value = "1")
    Integer status;

    @Column(name = "INSERTDATE")
    @CreationTimestamp
    LocalDateTime insertDate;

}
