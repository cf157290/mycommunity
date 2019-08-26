package com.example.community.community.advice;

import com.example.community.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    //@ResponseBody//返回json时候用的
    /*ResponseEntity<?> handleControllerException*/ModelAndView handle(/*HttpServletRequest request,*/ Throwable e, Model model) {
        //HttpStatus status = getStatus(request);

        if (e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message","服务冒烟了，请稍后再试!!!");
        }

        return new ModelAndView("error");/*new ResponseEntity<>(new CustomErrorType(status.value(), ex.getMessage()), status)*/
    }

/*    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }*/

}
