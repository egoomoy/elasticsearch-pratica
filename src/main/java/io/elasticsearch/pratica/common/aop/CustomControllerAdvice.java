package io.elasticsearch.pratica.common.aop;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class CustomControllerAdvice {
    //https://velog.io/@chb1828/Get-요청에-setter-constructor-없이-데이터-binding
    //https://jojoldu.tistory.com/407
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }
}