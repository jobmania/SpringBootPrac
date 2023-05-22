package com.example.test.validate;

import com.example.test.dto.UserCreateForm;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserCreateFormValidator implements Validator {
    private String adminPassword = "1234";

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateForm form = (UserCreateForm) target;

        if(form.getAdminPassword().equals(adminPassword)){
            return;
        }else {
            errors.rejectValue("adminPassword", "adminPassword.empty", "관리자 비밀번호를 입력해주세요.");
        }

    }
}
