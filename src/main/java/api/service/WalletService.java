package api.service;

import api.dto.BodyRequestDto;
import api.dto.WalletDto;

import java.util.UUID;

public interface WalletService {
    WalletDto create(WalletDto walletDto);

    String delete(UUID valletId);

    WalletDto editAmount(BodyRequestDto bodyRequestDto);

    WalletDto get(UUID valletId);
}
