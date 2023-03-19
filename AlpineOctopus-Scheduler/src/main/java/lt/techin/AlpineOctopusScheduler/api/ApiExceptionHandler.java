package lt.techin.AlpineOctopusScheduler.api;

import lt.techin.AlpineOctopusScheduler.api.dto.ErrorDto;
import lt.techin.AlpineOctopusScheduler.api.dto.ErrorFieldDto;
import lt.techin.AlpineOctopusScheduler.exception.SchedulerValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {

//      pavyzdys response, Spring sugeneruoto validacijos pranesimo
//        {
//            "timestamp": "2023-01-10T17:04:12.995+00:00",
//                "status": 500,
//                "error": "Internal Server Error",
//                "path": "/api/v1/animals/marked"
//        }

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpServletRequest request, Exception ex) {
        logger.info("SQLException Occured:: URL=" + request.getRequestURL());
        return "database_error";
    }

//    @ExceptionHandler(IOException.class)
//    public void schedulerNotFoundException() {
//        logger.error("IOException handler executed");
//        //returning 404 error code
//    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
    @ExceptionHandler(IOException.class)
    public void handleIOException() {
        logger.error("IOException handler executed");
        //returning 404 error code
    }

//@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "SQL query error")
//    public void handleSQLGrammarException(SQLGrammarException exception) {
//        logger.error("All Exceptions handler executed: {}. SQL: {}. Get SQL: {}",
//                exception.getMessage(), exception.getSQLException(), exception.getSQL());
//        //returning 404 error code
//    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorDto> handleDataAccessException(HttpServletRequest request, DataAccessException dataAccessException) {
        logger.error("DataAccessException: {}. Cause?: {}",
                dataAccessException.getMessage(), dataAccessException.getMostSpecificCause().getMessage());

        var errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var errorFields = List.of(
                new ErrorFieldDto("sql", dataAccessException.getMostSpecificCause().getMessage(), null)
        );
        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                errorFields,
                dataAccessException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.internalServerError().body(errorDto);
    }

    @ExceptionHandler(SchedulerValidationException.class)
    public ResponseEntity<ErrorDto> handleSchedulerValidationException(HttpServletRequest request, SchedulerValidationException schedulerValidationException) {
        logger.error("schedulerValidationException: {}, for field: {}", schedulerValidationException.getMessage(), schedulerValidationException.getField());

        var errorStatus = HttpStatus.BAD_REQUEST;

        var errorFields = List.of(
                new ErrorFieldDto(schedulerValidationException.getField(), schedulerValidationException.getError(), schedulerValidationException.getRejectedValue())
        );

        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                errorFields,
                schedulerValidationException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDto);
    }


//    @ExceptionHandler(SchedulerServiceDisabledException.class)
//    public ResponseEntity<Void> handleSchedulerServiceDisabledException(HttpServletRequest request, SchedulerServiceDisabledException serviceDisabledException) {
//        logger.error("SchedulerValidationException: {}", serviceDisabledException.getMessage());
//
//        var errorStatus = HttpStatus.SERVICE_UNAVAILABLE;
//
//        return new ResponseEntity<>(errorStatus);
//    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException notValidException) {
//        logger.error("MethodArgumentNotValidException: {}", notValidException.getMessage());
//
//        var errorStatus = HttpStatus.BAD_REQUEST;
//
//        var errorFields = notValidException.getBindingResult()
//                .getAllErrors().stream()
//                .map(ErrorFieldMapper::toErrorFieldDto)
//                .collect(toList());
//
//        var errorDto = new ErrorDto(request.getRequestURL().toString(),
//                errorFields,
//                notValidException.getMessage(),
//                errorStatus.value(),
//                errorStatus.getReasonPhrase(),
//                request.getRequestURL().toString(),
//                LocalDateTime.now());
//        return ResponseEntity.badRequest().body(errorDto);
//    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "We do not support this")
    @ExceptionHandler(HttpMediaTypeException.class)
    public void handleHttpMediaTypeException(HttpMediaTypeException mediaTypeException) {
        logger.error("Not supported request resulted in HttpMediaTypeException: {}", mediaTypeException.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something really bad happened")
    @ExceptionHandler(Exception.class)
    public void handleAllExceptions(Exception exception) {
        logger.error("All Exceptions handler: {}", exception.getMessage());
    }


}

