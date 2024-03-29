package api.mapper;

import api.dto.WalletDto;
import api.model.Wallet;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class WalletMapper {
    public static Wallet dtoToWallet(WalletDto walletDto) {
        return new Wallet(UUID.fromString(walletDto.getValletId()), walletDto.getAmount());
    }

    public static WalletDto walletToDto(Wallet wallet) {
        return new WalletDto(wallet.getValletId().toString(), wallet.getAmount());
    }
}
