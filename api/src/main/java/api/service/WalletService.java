package api.service;

import api.dto.BodyRequestDto;
import api.dto.WalletDto;

import java.util.UUID;

public interface WalletService {
    String create(WalletDto walletDto);

    String delete(String valletId);

    String editAmount(BodyRequestDto bodyRequestDto);

    String get(String valletId);
}
