package api;

import api.dto.BodyRequestDto;
import api.dto.WalletDto;
import api.exceptions.ErrorBodyRequest;
import api.exceptions.NotFoundOperationType;
import api.exceptions.NotFoundValletId;
import api.repository.WalletRepository;
import api.service.WalletService;
import api.service.WalletServiceImp;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
public class TestApi {
    private WalletRepository repository;

    @Test
    public void testNotFoundAndErrorBodyRequest() {
        String notFound = "00000000-0000-0000-0000-000000000000";
        WalletService walletService = new WalletServiceImp(repository);
        Throwable exception = assertThrows(NotFoundValletId.class, () -> walletService.get(notFound));
        assertEquals("valletId :" + notFound + " not found", exception.getMessage());
        exception = assertThrows(NotFoundValletId.class, () -> walletService.delete(notFound));
        assertEquals("valletId :" + notFound + " not found", exception.getMessage());

        BodyRequestDto bodyRequestDto = new BodyRequestDto();
        bodyRequestDto.setValletId(notFound);
        bodyRequestDto.setOperationType("NULL");
        bodyRequestDto.setAmount(100);
        exception = assertThrows(NotFoundOperationType.class, () -> walletService.editAmount(bodyRequestDto));
        assertEquals("operationType :NULL not found", exception.getMessage());

        bodyRequestDto.setValletId(null);
        bodyRequestDto.setOperationType(null);
        bodyRequestDto.setAmount(0);
        exception = assertThrows(ErrorBodyRequest.class, () -> walletService.editAmount(bodyRequestDto));
        assertEquals("error body request", exception.getMessage());
    }
}
