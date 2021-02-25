package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.converter.UserConverter;
import com.final_project.cargo_delivery.dao.interfaces.UserDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.User;
import com.final_project.cargo_delivery.service.interfaces.UserService;
import com.final_project.cargo_delivery.web.dto.UserCreateDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    UserDao userDao;
    @Mock
    UserConverter userConverter;

    @InjectMocks
    UserService sut = new UserServiceImpl();

    private LocaleApplication localeApplication = new LocaleApplication();

    @Before
    public void initLocalization() {
        localeApplication.setShortName("en_EN");
        localeApplication.setIdLocale(1);
    }

    @Test
    public void testRegister() {
        //Given
        UserCreateDto userCreateDto = new UserCreateDto("firstName", "lastName", "email",
                1, "password");
        User predefinedUser = new User();
        predefinedUser.setUserId(1L);
        predefinedUser.setFirstName("firstName");
        predefinedUser.setLastName("lastName");
        predefinedUser.setEmail("email");
        predefinedUser.setRoleId(1);
        predefinedUser.setPassword("password");

        User user = predefinedUser;
        user.setUserId(0L);

        Mockito.when(userConverter.convertUserCreateDtoAsUser(userCreateDto)).thenReturn(user);
        Mockito.when(userDao.save(localeApplication, user)).thenReturn(predefinedUser);

        //When
        sut.register(localeApplication, userCreateDto);

        //Then
        Mockito.verify(userConverter, Mockito.times(1)).convertUserCreateDtoAsUser(userCreateDto);
        Mockito.verify(userDao, Mockito.times(1)).save(localeApplication, user);
        Mockito.verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testLogin() {
        //Given
        String email = "email";
        String password = "password";
        User predefinedUser = new User();
        Mockito.when(userDao.findUserByEmail(localeApplication, email)).thenReturn(predefinedUser);

        //When
        sut.login(localeApplication, email, password);

        //Then
        Mockito.verify(userDao, Mockito.times(1)).findUserByEmail(localeApplication, email);
        Mockito.verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testGetUserById() {
        //Given
        long id = 1;
        User predefinedUser = new User();
        Mockito.when(userDao.getUserById(localeApplication, id)).thenReturn(predefinedUser);

        //When
        sut.getUserById(localeApplication, id);

        //Then
        Mockito.verify(userDao, Mockito.times(1)).getUserById(localeApplication, id);
        Mockito.verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testGetAllUsers() {
        //Given
        List<User> predefinedUsers= new ArrayList<>();
        Mockito.when(userDao.getAllUsers(localeApplication)).thenReturn(predefinedUsers);

        //When
        sut.getAllUsers(localeApplication);

        //Then
        Mockito.verify(userDao, Mockito.times(1)).getAllUsers(localeApplication);
        Mockito.verifyNoMoreInteractions(userDao);
    }
}