package in.abhijeet.expensetracker.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import in.abhijeet.expensetracker.entity.ErrorObject;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorObject> handleExpenseNotFoundException(ResourceNotFoundException ex,
			WebRequest request)
	{
		ErrorObject error=new ErrorObject();
		error.setMessage(ex.getMessage());
		error.setStatusCode(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(new Date());
		return new ResponseEntity<ErrorObject>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorObject> handleMethodArgumentMismatchException(MethodArgumentTypeMismatchException ex,
			WebRequest request)
	{
		ErrorObject error=new ErrorObject();
		error.setMessage(ex.getMessage());
		error.setStatusCode(HttpStatus.BAD_REQUEST.value());
		error.setTimestamp(new Date());
		return new ResponseEntity<ErrorObject>(error,HttpStatus.BAD_REQUEST);
	}
	
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	        MethodArgumentNotValidException ex,
	        HttpHeaders headers,
	        HttpStatusCode status,
	        WebRequest request)
	{
		Map<String,Object> map=new HashMap<>();
		map.put("timestamp", new Date());
		map.put("statusCode",HttpStatus.BAD_REQUEST);
		List<String> errors=ex.getBindingResult().getFieldErrors().stream()
				.map(x->x.getDefaultMessage())
				.collect(Collectors.toList());
		map.put("message", errors);
		return new ResponseEntity<Object>(map,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(ItemAlreadyExistsException.class)
	public ResponseEntity<ErrorObject> handleItemAlreadyExistsException(ItemAlreadyExistsException ex,
			WebRequest request)
	{
		ErrorObject error=new ErrorObject();
		error.setMessage(ex.getMessage());
		error.setStatusCode(HttpStatus.CONFLICT.value());
		error.setTimestamp(new Date());
		return new ResponseEntity<ErrorObject>(error,HttpStatus.CONFLICT);
	}
}
