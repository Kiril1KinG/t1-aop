package com.example.demo;

import com.example.demo.model.UserDto;
import com.example.demo.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@ComponentScan
public class T1AopApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(T1AopApplication.class);

		UserService service = context.getBean(UserService.class);

		service.createUser(new UserDto("name", "email@mail.ru", "+79265845678"));
		service.updateUser(1L, new UserDto(null, null, "+79345678901"));
		service.getUserById(1L);
		service.deleteUser(1L);
		service.deleteUser(1L);
	}

}
