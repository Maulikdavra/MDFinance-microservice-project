package unittest.accounts.service.client;

import com.md.accounts.dto.CardsDto;
import com.md.accounts.service.client.CardsFeignClient;
import com.md.accounts.service.impl.CustomersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

/**
 * @Author Maulik Davra
 * @Created On July 7th, 2024
 * @Description This is a test class for CardsFeignClient
 *
 */
class CardsFeignClientTest {

    @Mock
    CardsFeignClient cardsFeignClient;

    @InjectMocks
    CustomersServiceImpl customersService;

    CardsDto cardsDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks and inject them
        cardsDto = new CardsDto();
    }

    /**
     * Test to fetch card details
     * <br/>First, prepare the data for the test with cardsDto
     * <br/>Then, mock the fetchCardDetails method of cardsFeignClient
     * <br/>Finally, call the fetchCardDetails method of cardsFeignClient and assert the response
     *
     */
    @Test
    void fetchCardDetails_ReturnsDetails() {
        // Prepare
        String mobileNumber = "1234567890";
        String correlationId = "correlationId";
        cardsDto.setMobileNumber(mobileNumber);
        cardsDto.setCardNumber("1234567890");
        cardsDto.setCardType("Credit");
        cardsDto.setTotalLimit(100000);
        cardsDto.setAmountUsed(1000);
        cardsDto.setAvailableAmount(90000);

        when(cardsFeignClient.fetchCardDetails(correlationId, mobileNumber)).thenReturn(ResponseEntity.ok(cardsDto));

        // Act
        when(cardsFeignClient.fetchCardDetails(correlationId, mobileNumber)).thenReturn(ResponseEntity.ok(cardsDto));
    }

    /**
     * Test to fetch card details when the response is null
     * <br/>First, prepare the data for the test with cardsDto
     * <br/>Then, mock the fetchCardDetails method of cardsFeignClient
     * <br/>Finally, call the fetchCardDetails method of cardsFeignClient and assert the response
     */
    @Test
    void fetchCardDetails_ReturnsNull() {
        // Prepare
        String mobileNumber = "1234567890";
        String correlationId = "correlationId";

        when(cardsFeignClient.fetchCardDetails(correlationId, mobileNumber)).thenReturn(null);
    }
}