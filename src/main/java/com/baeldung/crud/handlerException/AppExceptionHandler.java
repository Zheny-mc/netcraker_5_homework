package com.baeldung.crud.handlerException;

import com.baeldung.crud.thowable.FileStorageException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(FileStorageException.class)
    public ModelAndView handleExceptionUploadFile(FileStorageException exception, RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("message", exception.getMsg());
        mav.setViewName("error");
        return mav;
    }

}