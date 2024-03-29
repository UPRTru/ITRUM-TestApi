package api.service;

import api.dto.BodyRequestDto;
import api.dto.WalletDto;
import api.exceptions.*;
import api.model.Wallet;
import api.repository.WalletRepository;
import api.сache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static api.mapper.WalletMapper.dtoToWallet;
import static api.mapper.WalletMapper.walletToDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImp implements WalletService {
    private final String ERROR_BODY_REQUEST = "error body request";
    private final WalletRepository walletRepository;

    @Transactional
    @Override
    public String create(WalletDto walletDto) {
        try {
            try {
                checkWallet(UUID.fromString(walletDto.getValletId()));
                log.info("Wallet found: {}", walletDto.getValletId());
                throw new ErrorWalletFound(walletDto.getValletId());
            } catch (NotFoundValletId e) {
                WalletDto result = walletToDto(walletRepository.save(dtoToWallet(walletDto)));
                log.info("Save new wallet: {}", walletDto);
                return result.toString();
            }
        } catch (Exception e) {
            log.info(ERROR_BODY_REQUEST);
            throw new ErrorBodyRequest(ERROR_BODY_REQUEST);
        }
    }

    @Transactional
    @Override
    public String createNull(long amount) {
        try {
            Wallet wallet = new Wallet();
            wallet.setAmount(amount);
            WalletDto result = walletToDto(walletRepository.save(wallet));
            log.info("Save new wallet: {}", result);
            return result.toString();
        } catch (Exception e) {
            log.info(ERROR_BODY_REQUEST);
            throw new ErrorBodyRequest("error path request");
        }
    }

    @Transactional
    @Override
    public String delete(UUID valletId) {
        Wallet wallet = checkWallet(valletId);
        walletRepository.delete(wallet);
        Cache.remove(valletId);
        return "deleted: " + valletId;
    }

    @Transactional
    @Override
    public String editAmount(BodyRequestDto bodyRequestDto) {
        if (bodyRequestDto.getAmount() == 0 || bodyRequestDto.getValletId() == null
                || bodyRequestDto.getOperationType() == null) {
            log.info(ERROR_BODY_REQUEST);
            throw new ErrorBodyRequest(ERROR_BODY_REQUEST);
        }
        OperationType ot = findByOperationType(bodyRequestDto.getOperationType());
        Wallet wallet = checkWallet(UUID.fromString(bodyRequestDto.getValletId()));
        Cache.putIfAbsent(wallet.getValletId(), new AtomicLong(wallet.getAmount()));
        switch (ot) {
            case DEPOSIT:
                wallet.setAmount(Cache.addAndGet(wallet.getValletId(), bodyRequestDto.getAmount(), true));
                break;
            case WITHDRAW:
                if (Cache.get(wallet.getValletId()) < bodyRequestDto.getAmount()) {
                    long money = bodyRequestDto.getAmount() - Cache.get(wallet.getValletId());
                    log.info("Not enough {} money.", money);
                    throw new NotEnoughMoney(money);
                } else {
                    wallet.setAmount(Cache.addAndGet(wallet.getValletId(), bodyRequestDto.getAmount(), false));
                }
                break;
        }
        log.info("Edit wallet: {}", wallet);
        return walletToDto(walletRepository.save(wallet)).toString();
    }

    @Transactional(readOnly = true)
    @Override
    public String get(UUID valletId) {
        return walletToDto(checkWallet(valletId)).toString();
    }

    private Wallet checkWallet(UUID valletId) {
        Wallet wallet;
        try {
            wallet = walletRepository.findByValletId(valletId);
        } catch (Exception e) {
            log.info("valletId :{} not found.", valletId);
            throw new NotFoundValletId(valletId);
        }
        if (wallet == null) {
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
