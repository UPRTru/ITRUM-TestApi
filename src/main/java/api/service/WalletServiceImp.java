package api.service;

import api.dto.BodyRequestDto;
import api.dto.WalletDto;
import api.exceptions.ErrorBodyRequest;
import api.exceptions.NotEnoughMoney;
import api.exceptions.NotFoundOperationType;
import api.exceptions.NotFoundValletId;
import api.model.Wallet;
import api.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static api.mapper.WalletMapper.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImp implements WalletService {
    private final String ERROR_BODY_REQUEST = "error body request";
    private final WalletRepository walletRepository;

    @Transactional
    @Override
    public WalletDto create(WalletDto walletDto) {
        try {
            Wallet wallet = dtoToWallet(walletDto);
            log.info("Save new wallet: {}", wallet);
            return walletToDto(walletRepository.save(wallet));
        } catch (Exception e) {
            log.info(ERROR_BODY_REQUEST);
            throw new ErrorBodyRequest(ERROR_BODY_REQUEST);
        }
    }

    @Transactional
    @Override
    public String delete(UUID valletId) {
        Wallet wallet = checkWallet(valletId);
        walletRepository.delete(wallet);
        return "deleted: " + valletId;
    }

    @Transactional
    @Override
    public WalletDto editAmount(BodyRequestDto bodyRequestDto) {
        if (bodyRequestDto.getAmount() == 0 || bodyRequestDto.getValletId() == null
                || bodyRequestDto.getOperationType() == null) {
            log.info(ERROR_BODY_REQUEST);
            throw new ErrorBodyRequest(ERROR_BODY_REQUEST);
        }
        OperationType ot = findByOperationType(bodyRequestDto.getOperationType());
        Wallet wallet = checkWallet(bodyRequestDto.getValletId());
        switch (ot) {
            case DEPOSIT:
                wallet.setAmount(wallet.getAmount() + bodyRequestDto.getAmount());
                break;
            case WITHDRAW:
                if (wallet.getAmount() < bodyRequestDto.getAmount()) {
                    long money = bodyRequestDto.getAmount() - wallet.getAmount();
                    log.info("Not enough {} money.", money);
                    throw new NotEnoughMoney(money);
                } else {
                    wallet.setAmount(wallet.getAmount() - bodyRequestDto.getAmount());
                }
                break;
        }
        log.info("Edit wallet: {}", wallet);
        return walletToDto(walletRepository.save(wallet));
    }

    @Transactional(readOnly = true)
    @Override
    public WalletDto get(UUID valletId) {
        return walletToDto(checkWallet(valletId));
    }

    private Wallet checkWallet(UUID valletId) {
        Wallet wallet;
        try {
            wallet = walletRepository.findByValletId(valletId);
        } catch (Exception e) {
            log.info("valletId :{} not found.", valletId);
            throw new NotFoundValletId(valletId);
        }
        return wallet;
    }

    private OperationType findByOperationType(String operationType) {
        for (OperationType ot : OperationType.values()) {
            if (ot.name().equals(operationType)) {
                return ot;
            }
        }
        log.info("operationType:{} not found.", operationType);
        throw new NotFoundOperationType(operationType);
    }
}
