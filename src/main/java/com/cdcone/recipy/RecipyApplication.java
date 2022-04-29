package com.cdcone.recipy;

import com.cdcone.recipy.dto.RecipeDtoAdd;
import com.cdcone.recipy.entity.RoleEntity;
import com.cdcone.recipy.repository.RoleDao;
import com.cdcone.recipy.services.RecipeService;

import com.cdcone.recipy.util.JwtUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecipyApplication {
	private boolean generateDummyData = true;

	public static void main(String[] args) {
		SpringApplication.run(RecipyApplication.class, args);
	}

	@Bean
	CommandLineRunner run(
		RecipeService recipeService,
		RoleDao roleDao
	){
		return args -> {
			if (!generateDummyData){
				return;
			}

			// Generate dummy data for database
			recipeService.add(new RecipeDtoAdd(
				"Es teh",
				"Enak diminum pada saat buka puasa",
				"Air, es batu, teh, gula",
				"Silahkan klik link videonya",
				"https://www.youtube.com/watch?v=biwLHUoPdPA&ab_channel=SajianSedap",
				false,
				null
			));

			RoleEntity userRole = new RoleEntity();
			userRole.setName("User");
			roleDao.save(userRole);
		};
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	JwtUtil jwtUtil() {
		return new JwtUtil();
	}

}
