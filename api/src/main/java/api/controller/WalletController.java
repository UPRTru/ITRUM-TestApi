package api.controller;

import api.dto.BodyRequestDto;
import api.dto.WalletDto;
import api.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Validated
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody @Valid WalletDto walletDto) {
        return walletService.create(walletDto);
    }

    @DeleteMapping("/delete/{WALLET_UUID}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable String WALLET_UUID) {
        return walletService.delete(WALLET_UUID);
    }

    //можно использовать @PatchMapping
    @PostMapping("/wallet")
    @ResponseStatus(HttpStatus.OK)
    public String editAmount(@RequestBody @Valid BodyRequestDto bodyRequestDto) {
        return walletService.editAmount(bodyRequestDto);
    }

    @GetMapping("/wallets/{WALLET_UUID}")
    @ResponseStatus(HttpStatus.OK)
    public String get(@PathVariable String WALLET_UUID) {
        return walletService.get(WALLET_UUID);
    }
}
