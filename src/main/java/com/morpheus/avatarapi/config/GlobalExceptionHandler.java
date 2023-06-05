package com.morpheus.avatarapi.config;


import com.morpheus.avatarapi.utils.ResCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {


//    @ExceptionHandler(value={AccessException.class})
//    @ResponseBody
//    public ResponseEntity AccessExceptionHandle(AccessException e, HttpServletRequest request, HttpServletResponse response){
//        ResCode resCode = new ResCode();
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
//        String url = null;
//        if(e.getUrl().equals("/error")) {
//            url = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
//        }else{
//            url = e.getUrl();
//        }
//        return new ResponseEntity<>(resCode.getResultErrorJson(e.getAccessToken(), e.getResultCode(), resCode.resultMessage(e.getResultCode()),url,request),responseHeaders, HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
//        ResCode resCode = new ResCode();
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
//        String url = null;
//        if(request.getRequestURI().equals("/error")) {
//            url = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
//        }else{
//            url = request.getRequestURI();
//        }
//        String accessToken = request.getHeader("access_token");
//        return new ResponseEntity<>(resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),url,request),responseHeaders, HttpStatus.FORBIDDEN);
//    }
}
