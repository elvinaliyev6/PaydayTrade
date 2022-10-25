package az.company.paydaytrade.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "STATUS")
    @ColumnDefault(value = "1")
    private Integer status;

    @Column(name = "INSERT_DATE")
    @CreationTimestamp
    private LocalDateTime insertDate;

    @Column(name = "BALANCE")
    private Double balance;

    @OneToOne(mappedBy = "user")
    private ConfirmationToken confirmationToken;

    @Column(name = "ACCOUNT_CONFIRMED")
    @ColumnDefault(value = "0")
    private Integer accountConfirmed;


}
