package ExceptionHandling;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class HttpErrors {

	private String errorMessage;
	private List<String> errorDetails;
	private HttpStatus errorStatus;
	private LocalDateTime errorTime;

	public HttpErrors(String errorMessage, List<String> errorDetails, HttpStatus errorStatus, LocalDateTime errorTime) {
		super();
		this.errorMessage = errorMessage;
		this.errorDetails = errorDetails;
		this.errorStatus = errorStatus;
		this.errorTime = errorTime;
	}

}
