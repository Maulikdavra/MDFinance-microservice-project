package unittest.accounts.service.client;

import com.md.accounts.dto.CardsDto;
import com.md.accounts.service.client.CardsFallback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class for CardsFallback
 * @Author Maulik Davra
 * @Created On July 7th, 2024

 */
public class CardsFallbackTest {

    private CardsFallback cardsFallback;

    @BeforeEach
    public void setUp() {
        // GIVEN
        cardsFallback = new CardsFallback();
    }

    /**
     * Test to verify that fetchCardDetails returns null
     */
    @Test
    public void testFetchCardDetailsReturnsNull() {
        // WHEN
        ResponseEntity<CardsDto> response = cardsFallback.fetchCardDetails("someCorrelationId",
                "someMobileNumber");

        // THEN
        assertNull(response, "The response should be null as per the fallback implementation");
    }
}