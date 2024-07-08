package unittest.accounts.exception;

import com.md.accounts.dto.ErrorResponseDto;
import com.md.accounts.exception.CustomerAlreadyExistException;
import com.md.accounts.exception.GlobalExceptionHandler;
import com.md.accounts.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GlobalExceptionHandler
 * <br/> One thing to note here that we are only testing HttpStatus and not the actual message but in a real world scenario we should test the actual message as well
 * @Author Maulik Davra
 * @Created On July 7th, 2024
 */
class GlobalExceptionHandlerTest {

    @Mock
    WebRequest webRequest;

    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    ErrorResponseDto errorResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        errorResponseDto = new ErrorResponseDto();
    }

    /**
     * Test to verify that handleMethodArgumentNotValid returns validation errors
     * <br/> First create a MethodArgumentNotValidException and then call handleMethodArgumentNotValid
     * <br/> Verify that the response entity contains the validation errors
     */
    @Test
    void handleGlobalException() {
        Exception exception = new Exception("Exception occurred");
        ResponseEntity<ErrorResponseDto> responseEntity = globalExceptionHandler.handleGlobalException(exception, webRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    /**
     * Test to verify that handleResourceNotFoundException returns 404 status code
     * <br/> First create a ResourceNotFoundException and then call handleResourceNotFoundException
     * <br/> Verify that the response entity contains the 404 status codes
     */
    @Test
    void handleResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found", "fieldName", "fieldValue");
        ResponseEntity<ErrorResponseDto> responseEntity = globalExceptionHandler.handleResourceNotFoundException(exception, webRequest);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    /**
     * Test to verify that handleCustomerAlreadyExistException returns 400 status code
     * <br/> First create a CustomerAlreadyExistException and then call handleCustomerAlreadyExistException
     * <br/> Verify that the response entity contains the 400 status codes
     */
    @Test
    void handleCustomerAlreadyExistException() {
        CustomerAlreadyExistException exception = new CustomerAlreadyExistException("Customer already exist with the mobile number: 1234567890");
        ResponseEntity<ErrorResponseDto> responseEntity = globalExceptionHandler.handleCustomerAlreadyExistsException(exception, webRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}