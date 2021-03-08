package ExceptionHandling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import Utility.GlobalConstants;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	private List<String> errorsList = new ArrayList<>();

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		errorsList.add(ex.getMessage());
		HttpErrors errors = new HttpErrors(GlobalConstants.METHOD_NOT_SUPPORTED, errorsList, status,
				LocalDateTime.now());
		return ResponseEntity.status(status).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		errorsList.add(ex.getMessage());
		HttpErrors errors = new HttpErrors(GlobalConstants.UNSUPPORTED_MEDIA, errorsList, status, LocalDateTime.now());
		return ResponseEntity.status(status).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		errorsList.add(ex.getMessage());
		HttpErrors errors = new HttpErrors(GlobalConstants.MISSING_PATH, errorsList, status, LocalDateTime.now());
		return ResponseEntity.status(status).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		errorsList.add(ex.getMessage());
		HttpErrors errors = new HttpErrors(GlobalConstants.MISSING_PARAM, errorsList, status, LocalDateTime.now());
		return ResponseEntity.status(status).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		errorsList.add(ex.getMessage());
		HttpErrors errors = new HttpErrors(GlobalConstants.MISMATCH, errorsList, status, LocalDateTime.now());
		return ResponseEntity.status(status).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		errorsList.add(ex.getMessage());
		HttpErrors errors = new HttpErrors(GlobalConstants.UNREADABLE_REQUEST, errorsList, status, LocalDateTime.now());
		return ResponseEntity.status(status).body(errors);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		errorsList.add(ex.getMessage());
		HttpErrors errors = new HttpErrors(GlobalConstants.FALL_BACK_EXCEPTIONS, errorsList, status,
				LocalDateTime.now());
		return ResponseEntity.status(status).body(errors);

	}

}
