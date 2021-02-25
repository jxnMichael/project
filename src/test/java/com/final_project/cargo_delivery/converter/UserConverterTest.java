package com.final_project.cargo_delivery.converter;

import com.final_project.cargo_delivery.entity.User;
import com.final_project.cargo_delivery.web.dto.UserCreateDto;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class UserConverterTest {

    private UserConverter userConverter = new UserConverter();

    @Test
    void convertUserCreateDtoAsUser() {
        //Given
        UserCreateDto userCreateDto = new UserCreateDto("firstName", "lastName", "email",
                1,"password");
        //When
        User resultUser = userConverter.convertUserCreateDtoAsUser(userCreateDto);

        //Then
        Assertions.assertEquals(userCreateDto.getEmail(), resultUser.getEmail());
        Assertions.assertEquals(userCreateDto.getFirstName(), resultUser.getFirstName());
        Assertions.assertEquals(userCreateDto.getLastName(), resultUser.getLastName());
        Assertions.assertEquals(userCreateDto.getPassword(), resultUser.getPassword());
        Assertions.assertEquals(userCreateDto.getRoleId(), resultUser.getRoleId());
    }

    @Test
    void convertUserAsUserViewDto() {
        //Given
        User user = new User();
        user.setUserId(1L);
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setRoleId(1);
        user.setPassword("password");

        //When
        UserViewDto userViewDto = userConverter.convertUserAsUserViewDto(user);

        //Then
        Assertions.assertEquals(user.getUserId(), userViewDto.getId());
        Assertions.assertEquals(user.getEmail(), userViewDto.getEmail());
        Assertions.assertEquals(user.getFirstName(), userViewDto.getFirstName());
        Assertions.assertEquals(user.getLastName(), userViewDto.getLastName());
        Assertions.assertEquals(user.getRoleId(), userViewDto.getRoleId());
        Assertions.assertEquals(user.getPassword(), userViewDto.getPassword());
    }

}