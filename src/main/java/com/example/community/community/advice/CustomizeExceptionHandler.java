package com.example.community.community.advice;

import com.alibaba.fastjson.JSON;
import com.example.community.community.dto.ResultDTO;
import com.example.community.community.exception.CustomizeErrorCode;
import com.example.community.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    //@ResponseBody//返回json时候用的
    /*ResponseEntity<?> handleControllerException*/
    ModelAndView handle(/*HttpServletRequest request,*/ Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        //HttpStatus status = getStatus(request);
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            //返回json
            if (e instanceof CustomizeException) {
                resultDTO= ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO=ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        } else {
            //错误页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute(CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }

            return new ModelAndView("error");/*new ResponseEntity<>(new CustomErrorType(status.value(), ex.getMessage()), status)
        }
       */
        }
    }

/*    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }*/

}
