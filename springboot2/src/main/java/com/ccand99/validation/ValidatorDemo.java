package com.ccand99.validation;

import com.ccand99.domain.User;
import org.springframework.context.MessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Locale;
import java.util.Objects;

import static com.ccand99.validation.ErrorsMessageDemo.createMessageSource;

/**
 * 自定义 Spring {@link Validator} 示例
 */
public class ValidatorDemo {

    public static void main(String[] args) {
        //1. 创建 Validator
        Validator validator = new UserValidator();
        //2. 判断是否支持目标对象的类型
        User user = new User();
        System.out.println("User 对象是否被 UserValidator 支持校验： " + validator.supports(user.getClass()));
        //3. 创建 Errors 对象
        Errors errors = new BeanPropertyBindingResult(user,"user");
        validator.validate(user,errors);
        //获取MessageSource 对象，生产环境中应该是一个配置文件
        MessageSource messageSource = createMessageSource();
        System.out.println(user);
        errors.getAllErrors().forEach(error -> {
            String message = messageSource.getMessage(Objects.requireNonNull(error.getCode()),error.getArguments(), Locale.getDefault());
            System.out.println(message);
        });
    }

    static class UserValidator implements Validator {
        @Override
        public boolean supports(Class<?> clazz) {
            return User.class.isAssignableFrom(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            User user = (User) target;
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"id","id.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"age","age.required");
            //String userName = user
        }
    }
}
