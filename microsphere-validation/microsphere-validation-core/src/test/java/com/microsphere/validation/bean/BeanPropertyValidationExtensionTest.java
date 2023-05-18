package com.microsphere.validation.bean;

import com.microsphere.validation.FieldError;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
class BeanPropertyValidationExtensionTest {

    @Test
    void validate() {
        final User user = new User(null, null);
        final BeanPropertyBindingResult<User> bindingResult = new BeanPropertyBindingResult<>(user.getClass().getName(), user);
        final BeanPropertyValidationExtension<User> validationExtension = new BeanPropertyValidationExtension<>(bindingResult);

        Assertions.assertTrue(validationExtension.hasErrors());
        for (FieldError error : validationExtension.getFieldErrors()) {
            System.out.printf("field name：[%s]，bad message：[%s] \n", error.getFieldName(), error.message());
        }
    }

    static class User {

        @NotNull(message = "username cannot be null")
        private String username;

        @NotNull(message = "password cannot be null")
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}