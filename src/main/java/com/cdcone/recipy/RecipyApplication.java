package com.cdcone.recipy;
import java.util.HashSet;
import java.util.Set;

import com.cdcone.recipy.recipe.dto.request.RecipeAddRequestDto;
import com.cdcone.recipy.user.dto.request.SignUpRequestDto;
import com.cdcone.recipy.user.entity.RoleEntity;
import com.cdcone.recipy.user.repository.RoleDao;
import com.cdcone.recipy.user.service.UserService;
import com.cdcone.recipy.recipe.service.RecipeService;
import com.cdcone.recipy.recipe.service.TagService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class RecipyApplication {
	public static boolean generateDummyData = false;


	public static void main(String[] args) {
		SpringApplication.run(RecipyApplication.class, args);
	}

	@Bean
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
			userService.addUser(new SignUpRequestDto(
					"user1@gmail.com",
					"user1",
					"123456",
					"User 1"));

			userService.addUser(new SignUpRequestDto(
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
			recipeService.add(new RecipeAddRequestDto(
					1L,
					tags,
					"Es teh",
					"Enak diminum pada saat buka puasa",
					"Air, es batu, teh, gula",
					"Silahkan klik link videonya",
					"https://www.youtube.com/watch?v=biwLHUoPdPA&ab_channel=SajianSedap",
					false));

			recipeService.addViewer(1L);

			recipeService.add(new RecipeAddRequestDto(
					1L,
					tags,
					"ddd2",
					"Enak diminum pada saat buka puasa",
					"Air, es batu, teh, gula",
					"Silahkan klik link videonya",
					"https://www.youtube.com/watch?v=biwLHUoPdPA&ab_channel=SajianSedap",
					false));

			recipeService.add(new RecipeAddRequestDto(
					1L,
					tags,
					"asd3",
					"Enak diminum pada saat buka puasa",
					"Air, es batu, teh, gula",
					"Silahkan klik link videonya",
					"https://www.youtube.com/watch?v=biwLHUoPdPA&ab_channel=SajianSedap",
					false));

			recipeService.add(new RecipeAddRequestDto(
					1L,
					tags,
					"asdg4",
					"Enak diminum pada saat buka puasa",
					"Air, es batu, teh, gula",
					"Silahkan klik link videonya",
					"https://www.youtube.com/watch?v=biwLHUoPdPA&ab_channel=SajianSedap",
					false));

			System.out.println("\n\n---Feeding data complete---\n\n");
		};
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
