package az.company.paydaytrade.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "STOCKS")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "NAME")
    String name;

    @Column(name = "PRICE")
    Double price;

    @Column(name = "STATUS")
    @ColumnDefault(value = "1")
    Integer status;

    @CreationTimestamp
    @Column(name = "INSERT_DATE")
    LocalDateTime insertDate;

}
