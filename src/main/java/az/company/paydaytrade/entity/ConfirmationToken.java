package az.company.paydaytrade.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "confirmation_tokem")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CONFIRMATION_TOKEN")
    private String confirmationToken;

    @OneToOne
    @JoinColumn(nullable = false, name = "id")
    private User user;

    @Column(name = "INSERT_DATE")
    private LocalDateTime insertDate;

    @Column(name = "STATUS")
    @ColumnDefault(value = "1")
    private Integer status;

}
