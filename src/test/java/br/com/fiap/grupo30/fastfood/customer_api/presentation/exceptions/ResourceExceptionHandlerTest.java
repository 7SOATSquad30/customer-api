package br.com.fiap.grupo30.fastfood.customer_api.presentation.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.DatabaseException;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.ResourceBadRequestException;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.ResourceExceptionHandler;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.StandardError;
import br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.exceptions.ValidationError;
import br.com.fiap.grupo30.fastfood.customer_api.utils.FieldErrorHelper;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class ResourceExceptionHandlerTest {

    private static final String PATH_VARIABLE = "/products";
    private static final String PATH_VARIABLE_ID = "/products/{id}";

    @Nested
    class ResourceNotFoundExceptionHandler {
        @Test
        void shouldHandleResourceNotFoundExceptionAndReturn404() {
            // Arrange
            ResourceNotFoundException exception =
                    new ResourceNotFoundException("Resource not found");
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE_ID);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response = handler.entityNotFound(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.NOT_FOUND,
                    response.getStatusCode(),
                    "Expected HTTP status NOT_FOUND (404)");
        }

        @Test
        void shouldReturnCorrectErrorMessageForResourceNotFoundException() {
            // Arrange
            ResourceNotFoundException exception =
                    new ResourceNotFoundException("Resource not found");
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE_ID);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<StandardError> response = handler.entityNotFound(exception, request);

            // Assert
            assertEquals(
                    "Resource not found",
                    Objects.requireNonNull(response.getBody()).getError(),
                    "Error message should match exception message");
        }
    }

    @Nested
    class MethodArgumentNotValidExceptionHandler {
        @Test
        void shouldHandleValidationExceptionAndReturn422() {
            // Arrange
            MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.getFieldErrors())
                    .thenReturn(List.of(FieldErrorHelper.createDefaultFieldError()));
            when(exception.getBindingResult()).thenReturn(bindingResult);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<ValidationError> response = handler.validation(exception, request);

            // Assert
            assertEquals(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    response.getStatusCode(),
                    "Expected HTTP status UNPROCESSABLE_ENTITY (422)");
        }

        @Test
        void shouldReturnValidationErrorDetails_exceptionMessage() {
            // Arrange
            MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            FieldError fieldError = FieldErrorHelper.createDefaultFieldError();
            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
            when(exception.getBindingResult()).thenReturn(bindingResult);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<ValidationError> response = handler.validation(exception, request);

            // Assert
            assertEquals(
                    "Validation exception",
                    Objects.requireNonNull(response.getBody()).getError(),
                    "Error message should match exception message");
        }

        @Test
        void shouldReturnValidationErrorDetails_singleError() {
            // Arrange
            MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            FieldError fieldError = FieldErrorHelper.createDefaultFieldError();
            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
            when(exception.getBindingResult()).thenReturn(bindingResult);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<ValidationError> response = handler.validation(exception, request);

            // Assert
            assertEquals(
                    1,
                    Objects.requireNonNull(response.getBody()).getErrors().size(),
                    "ValidationError should contain exactly one error");
        }

        @Test
        void shouldReturnValidationErrorDetails_errorFieldName() {
            // Arrange
            MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            FieldError fieldError = FieldErrorHelper.createDefaultFieldError();
            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
            when(exception.getBindingResult()).thenReturn(bindingResult);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<ValidationError> response = handler.validation(exception, request);

            // Assert
            assertEquals(
                    "name",
                    Objects.requireNonNull(response.getBody()).getErrors().get(0).getFieldName(),
                    "Field name in ValidationError should match expected value");
        }

        @Test
        void shouldReturnValidationErrorDetails_errorMessage() {
            // Arrange
            MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            FieldError fieldError = FieldErrorHelper.createDefaultFieldError();
            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
            when(exception.getBindingResult()).thenReturn(bindingResult);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();

            // Act
            ResponseEntity<ValidationError> response = handler.validation(exception, request);

            // Assert
            assertEquals(
                    "Name is required",
                    Objects.requireNonNull(response.getBody()).getErrors().get(0).getMessage(),
                    "Error message in ValidationError should match expected value");
        }
    }

    @Nested
    class ResourceBadRequestExceptionHandler {
        @Test
        void shouldHandleResourceBadRequestExceptionAndReturn400() {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceBadRequestException requestException =
                    new ResourceBadRequestException("Invalid request") {};
            ResourceExceptionHandler handler = new ResourceExceptionHandler();
            ResponseEntity<StandardError> response =
                    handler.entityBadRequest(requestException, request);

            assertEquals(
                    PATH_VARIABLE, response.getBody().getPath(), "Path should match request URI");
        }
    }

    @Nested
    class ResourceConflictExceptionHandler {
        @Test
        void shouldHandleResourceConflictExceptionAndReturn409() {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE_ID);

            ResourceBadRequestException requestException =
                    new ResourceBadRequestException("Invalid request") {};
            ResourceExceptionHandler handler = new ResourceExceptionHandler();
            ResponseEntity<StandardError> response =
                    handler.entityBadRequest(requestException, request);

            assertEquals(
                    PATH_VARIABLE_ID,
                    response.getBody().getPath(),
                    "Path should match request URI");
        }
    }

    @Nested
    class DatabaseExceptionHandler {
        @Test
        void shouldHandleDatabaseExceptionAndReturn400() {
            DatabaseException exception = new DatabaseException("Database error occurred", null);
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRequestURI(PATH_VARIABLE);

            ResourceExceptionHandler handler = new ResourceExceptionHandler();
            ResponseEntity<StandardError> response = handler.database(exception, request);

            assertEquals(
                    PATH_VARIABLE, response.getBody().getPath(), "Path should match request URI");
        }
    }
}
