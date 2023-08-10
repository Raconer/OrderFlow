package com.order.flow.common.exception;

import com.order.flow.common.response.CommonRes;
import com.order.flow.common.response.ValidInfo;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ValidInfo> errorList = ex.getBindingResult().getFieldErrors().stream().map(it ->
                ValidInfo.builder().field(it.getField()).msg(it.getDefaultMessage()).build()
        ).collect(Collectors.toList());
        return CommonRes.Except(HttpStatus.BAD_REQUEST, errorList);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleBindException(DataIntegrityViolationException ex) {

        return CommonRes.Except(HttpStatus.BAD_REQUEST, "같은 상품을 중복 요청 하였습니다.");
    }

    @ExceptionHandler(ItemAlreadySoldException.class)
    public ResponseEntity<String> handleItemAlreadySoldException(ItemAlreadySoldException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
