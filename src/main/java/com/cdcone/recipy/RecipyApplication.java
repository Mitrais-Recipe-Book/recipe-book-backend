package com.cdcone.recipy;

import java.util.HashSet;
import java.util.Set;

import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.cdcone.recipy.dtoRequest.SignUpDto;
import com.cdcone.recipy.entity.RoleEntity;
import com.cdcone.recipy.repository.RoleDao;
import com.cdcone.recipy.service.UserService;
import com.cdcone.recipy.service.RecipeService;
import com.cdcone.recipy.service.TagService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class RecipyApplication {
	public static boolean generateDummyData = true;

	public static void main(String[] args) {
		SpringApplication.run(RecipyApplication.class, args);
	}

//	@Bean
	CommandLineRunner run(
			RecipeService recipeService,
			UserService userService,
			RoleDao roleService,
			TagService tagService) {
		return args -> {
			if (!generateDummyData) {
				return;
			}

			// Generate dummy data for database
			// Role data
			RoleEntity userRole = new RoleEntity();
			userRole.setName("User");
			roleService.save(userRole);

			// User data
			userService.addUser(new SignUpDto(
					"user1@gmail.com",
					"user1",
					"123456",
					"User 1"));

			userService.addUser(new SignUpDto(
					"user2@gmail.com",
					"user2",
					"123456",
					"User 2"));

			// Tag data
			tagService.saveTag("Breakfast");
			tagService.saveTag("Light Meal");

			// Recipe data
			Set<Integer> tags = new HashSet<Integer>();
			tags.add(1);
			recipeService.add(new RecipeDtoAdd(
					1L,
					tags,
					"Es teh",
					"Enak diminum pada saat buka puasa",
					"Air, es batu, teh, gula",
					"Silahkan klik link videonya",
					"https://www.youtube.com/watch?v=biwLHUoPdPA&ab_channel=SajianSedap",
					false));

			recipeService.addViewer(1L);

			System.out.println("\n\n---Feeding data complete---\n\n");
		};
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
