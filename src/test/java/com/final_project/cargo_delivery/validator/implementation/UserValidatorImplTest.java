package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.dao.interfaces.UserDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ValidationException;
import com.final_project.cargo_delivery.validator.interfaces.UserValidator;
import com.final_project.cargo_delivery.web.dto.UserCreateDto;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorImplTest {

    @Mock
    UserDao userDao;

    @InjectMocks
    UserValidator sut = new UserValidatorImpl();

    private LocaleApplication localeApplication = new LocaleApplication();
    private ResourceBundle messages;

    private UserCreateDto initUserCreateDto() {
        return new UserCreateDto("firstName", "lastName", "email",
                1, "password");
    }

    @Before
    public void initLocalization() {
        localeApplication.setShortName("en_EN");
        localeApplication.setIdLocale(1);
        messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
    }

    @Test
    public void testValidateUserCredentialsEmptyEmailShouldThrowException() {
        //Given
        String email = "";
        String password = "some password";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateUserCredentials(localeApplication, email, password));
        Assert.assertEquals(messages.getString("exception.error.invalid_credentials"),
                validationException.getMessage());
    }

    @Test
    public void testValidateUserCredentialsEmptyPasswordShouldThrowException() {
        //Given
        initLocalization();
        String email = "some email";
        String password = "";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateUserCredentials(localeApplication, email, password));
        Assert.assertEquals(messages.getString("exception.error.invalid_credentials"),
                validationException.getMessage());
    }

    @Test
    public void testValidateNewUserShouldCheckIfUserNotExist() {
        //Given
        UserCreateDto userCreateDto = initUserCreateDto();
        Mockito.when(userDao.isUserExist(localeApplication, userCreateDto.getEmail())).thenReturn(false);

        //When
        sut.validateNewUser(localeApplication, userCreateDto);

        //Then
        Mockito.verify(userDao, Mockito.times(1))
                .isUserExist(localeApplication, userCreateDto.getEmail());
        Mockito.verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testValidateNewUserShouldThrowExceptionIfUserExist() {
        //Given
        UserCreateDto userCreateDto = initUserCreateDto();
        Mockito.when(userDao.isUserExist(localeApplication, userCreateDto.getEmail())).thenReturn(true);

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateNewUser(localeApplication, userCreateDto));
        Assert.assertEquals(messages.getString("exception.error.user_exist"),
                validationException.getMessage());
    }

    @Test
    public void testValidateNewUserShouldThrowExceptionIfFirstNameEmpty() {
        //Given
        UserCreateDto userCreateDto = initUserCreateDto();
        userCreateDto.setFirstName("");

        //When

        //Then
        ValidationException validationException1 = assertThrows(
                ValidationException.class, () -> sut.validateNewUser(localeApplication, userCreateDto));
        Assert.assertEquals(messages.getString("exception.error.invalid_user_name") + userCreateDto.getFirstName(),
                validationException1.getMessage());
    }

    @Test
    public void testIsCorrectPasswordAndEmailShouldThrowExceptionIfEmailIsNotEquals() {
        //Given
        UserViewDto userViewDto = new UserViewDto();
        userViewDto.setEmail("email");
        userViewDto.setPassword("password");
        String predefinedEmail = "notEqualEmail";
        String predefinedPassword = "password";

        //When

        //Then
        ValidationException validationException1 = assertThrows(
                ValidationException.class,
                () -> sut.isCorrectPasswordAndEmail(localeApplication, userViewDto,
                        predefinedEmail, predefinedPassword));
        Assert.assertEquals(messages.getString("exception.error.wrong_password"),
                validationException1.getMessage());
    }

    @Test
    public void testIsCorrectPasswordAndEmailShouldThrowExceptionIfPasswordIsNotEquals() {
        //Given
        UserViewDto userViewDto = new UserViewDto();
        userViewDto.setEmail("email");
        userViewDto.setPassword("password");
        String predefinedEmail = "email";
        String predefinedPassword = "notEqualPassword";

        //When

        //Then
        ValidationException validationException1 = assertThrows(
                ValidationException.class,
                () -> sut.isCorrectPasswordAndEmail(localeApplication, userViewDto,
                        predefinedEmail, predefinedPassword));
        Assert.assertEquals(messages.getString("exception.error.wrong_password"),
                validationException1.getMessage());
    }
}