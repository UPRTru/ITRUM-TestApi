package api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletDto {
    String valletId;
    Long amount;

    @Override
    public String toString() {
        return "Wallet{" +
                "valletId='" + valletId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
