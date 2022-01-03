package com.ccand99.validation;

import com.ccand99.domain.User;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * 错误文案
 */
public class ErrorsMessageDemo {

    public static void main(String[] args) {
        //创建 User对象
        User user = new User();
        user.setId("test");
        //选择 errors 实现 -BeanPropertyBindingResult
        Errors errors = new BeanPropertyBindingResult(user,"user");
        // 调用reject 生成ObjectError

        // reject 生成FieldError
        errors.reject("user.properties.not.null");
        //相当于User.getId()
        errors.rejectValue("id","id.required");
        // 获取Errors 中 ObjectError 和 FieldError
        List<ObjectError> globalErrors = errors.getGlobalErrors();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        List<ObjectError> allErrors = errors.getAllErrors();

        //通过 ObjectError 和FieldError 中的code 和args 来关联 MessageSource实现
        MessageSource messageSource = createMessageSource();

        allErrors.forEach(error -> {
            String message = messageSource.getMessage(Objects.requireNonNull(error.getCode()),error.getArguments(),Locale.getDefault());
            System.out.println(message);
        });
    }

    static MessageSource createMessageSource() {
        StaticMessageSource messageSource = new StaticMessageSource();
        messageSource.addMessage("id.required", Locale.getDefault(),"the id of User must not be null.");
        messageSource.addMessage("age.required", Locale.getDefault(),"the age of User must not be null.");
        messageSource.addMessage("user.properties.not.null", Locale.getDefault(),"User 所有属性不能为空");
        return messageSource;
    }
}
