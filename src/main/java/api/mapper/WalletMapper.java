package api.mapper;

import api.dto.WalletDto;
import api.model.Wallet;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WalletMapper {
    public static Wallet dtoToWallet(WalletDto walletDto) {
        return new Wallet(walletDto.getValletId(), walletDto.getAmount());
    }

    public static WalletDto walletToDto(Wallet wallet) {
        return new WalletDto(wallet.getValletId(), wallet.getAmount());
    }
}
