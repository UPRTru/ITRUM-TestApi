package api.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID valletId;
    @Column(nullable = false, unique = true)
    Long amount;
}
