package com.cdcone.recipy;


import com.cdcone.recipy.dtoRequest.RecipeDtoAdd;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

	ObjectMapper om = new ObjectMapper();

    @Test
    void testFailSignIn() throws Exception {
        String signIn = "{\"username\":\"unavailable\",\"password\":\"unavailable\"}";
        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signIn))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Wrong username or password"))
                .andReturn();
    }

    @Test
    void testSuccessSignIn() throws Exception {
        String signIn = "{\"username\":\"user1\",\"password\":\"password\"}";
        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signIn))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Success"))
                .andReturn();
    }

    @Test
    void testFailSignUpIfEmptyField() throws Exception {
        String signUp = "{\"email\":\"\",\"username\":\"\",\"password\":\"\",\"fullName\":\"\"}";
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUp))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Please fill out all required fields."))
                .andReturn();
    }

    @Test
    void testFailSignUpIfUserAlreadyExist() throws Exception {
        String signUp = "{\"email\":\"user1@test.com\",\"username\":\"user1\",\"password\":\"password\",\"fullName\":\"test test\"}";
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUp))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Failed to create user. Username or email is already exists"))
                .andReturn();
    }

    @Test
    void testSuccessSignUp() throws Exception {
        String signUp = "{\"email\":\"test@test.com\",\"username\":\"test\",\"password\":\"password\",\"fullName\":\"test test\"}";
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUp))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"))
                .andReturn();
    }

    @Test
    void testFailSignUpIfPasswordLessThanEightCharacters() throws Exception {
        String signUp = "{\"email\":\"test2@test.com\",\"username\":\"test2\",\"password\":\"pass\",\"fullName\":\"test test\"}";
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUp))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password must be equal or more than 8 characters"))
                .andReturn();
    }

    @Test
    void testSuccessGetByUsername() throws Exception {
        String username = "user1";
        mockMvc.perform(get("/api/v1/user/" + username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("success: data retrieved"))
                .andReturn();
    }

    @Test
    void testFailGetByUsername() throws Exception {
        String username = "unavailablexxx";
        mockMvc.perform(get("/api/v1/user/" + username))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found."))
                .andReturn();
    }
    
    @Test
    void testSuccessGetRecipeByUsername() throws Exception {
    	String username = "user1";
    	mockMvc.perform(get("/api/v1/user/" + username + "/recipes"))
    	        .andExpect(status().isOk())
    	        .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.payload.totalPages").value(1))
                .andExpect(jsonPath("$.payload.data[0].title").value("string"))
    	        .andReturn();
    }
    
    @Test
    void testSuccessGetFollowerList() throws Exception {
    	Long userId = 1L;
    	mockMvc.perform(get("/api/v1/user/" + userId + "/followers"))
    	        .andExpect(status().isOk())
    	        .andExpect(jsonPath("$.message").value("success"))
    	        .andExpect(jsonPath("$.payload.*").isArray())
    	        .andExpect(jsonPath("$.payload.length()", is(4)))
    	        .andReturn();
    }
    
    @Test
    void testSuccessGetFollowingList() throws Exception {
    	Long userId = 10L;
    	mockMvc.perform(get("/api/v1/user/" + userId + "/follow-list"))
    	        .andExpect(status().isOk())
    	        .andExpect(jsonPath("$.message").value("succeed"))
    	        .andExpect(jsonPath("$.payload.*").isArray())
    	        .andExpect(jsonPath("$.payload.length()", is(1)))
    	        .andReturn();
    }
    
    @Test
    void testFailAddTagIfAlreadyExist() throws Exception {
    	String duplicateTag = "breakfast";
    	mockMvc.perform(post("/api/v1/tag")
                 	.content(duplicateTag))
    		.andExpect(status().isBadRequest())
    		.andExpect(jsonPath("$.message").value("failed: cannot save duplicate"))
    		.andReturn();
    }
    
    @Test
    void testSuccessAddTag() throws Exception {
    	String newTag = "eastern";
    	mockMvc.perform(post("/api/v1/tag")
    			.content(newTag))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.message").value("success: tag saved"))
    		.andExpect(jsonPath("$.payload.name").value(newTag))
    		.andReturn();
    }
    
    @Test
    void testFailEditTagIfAlreadyExist() throws Exception {
	Integer tagId = 4; // "western"
	String editTag = "{\"tagId\":" + tagId + ",\"tagReplace\":\"breakfast\"}";
	mockMvc.perform(put("/api/v1/tag")
			.contentType(MediaType.APPLICATION_JSON)
			.content(editTag))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").value("error: tag already exists"))
		.andReturn();
    }
    
    @Test
    void testFailEditTagIfSameName() throws Exception {
	Integer tagId = 4; // "western"
	String editTag = "{\"tagId\":" + tagId + ",\"tagReplace\":\"western\"}";
	mockMvc.perform(put("/api/v1/tag")
			.contentType(MediaType.APPLICATION_JSON)
			.content(editTag))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").value("error: tag must be different"))
		.andReturn();
    }
    
    @Test
    void testFailEditTagIfNotFound() throws Exception {
	Integer tagId = 99;
	String editTag = "{\"tagId\":" + tagId + ",\"tagReplace\":\"dinner\"}";
	mockMvc.perform(put("/api/v1/tag")
			.contentType(MediaType.APPLICATION_JSON)
			.content(editTag))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.message").value("error: tag not found"))
		.andReturn();
    }
    
    @Test
    void testSuccessEditTag() throws Exception {
	Integer tagId = 3; // "indonesia seafood"
	String editTag = "{\"tagId\":" + tagId + ",\"tagReplace\":\"dinner\"}";
	mockMvc.perform(put("/api/v1/tag")
			.contentType(MediaType.APPLICATION_JSON)
			.content(editTag))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.message").value("success: data updated"))
		.andExpect(jsonPath("$.payload.new").value("dinner"))
		.andReturn();
    }
    
    @Test
    void testFailDeleteTagIfNotFound() throws Exception {
	Integer tagId = 99;
	mockMvc.perform(delete("/api/v1/tag/" + tagId))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").value("error: Tag not found"))
		.andReturn();
    }
    
    @Test
    void testSuccessDeleteTag() throws Exception {
	Integer tagId = 2; // "light meal"
	mockMvc.perform(delete("/api/v1/tag/" + tagId))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.message").value("success: data deleted"))
		.andExpect(jsonPath("$.payload.name").value("light meal"))
		.andReturn();
    }
    
    @Test
    void testSuccessGetAllTags() throws Exception {
	mockMvc.perform(get("/api/v1/tag"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.message").value("success: data retrieved"))
		.andExpect(jsonPath("$.payload").isArray())
		.andReturn();
    }

	@Test
	void testSuccessAddRecipe() throws Exception {
		RecipeDtoAdd request = new RecipeDtoAdd();
		request.setTitle("Ayam Bakar");
		request.setOverview("Ayam Bakar Madiun");
		request.setTagIds(Set.of(1));
		request.setIngredients("[{\"name\":\"Ayam\",\"qty\":\"500gr\"},{\"name\":\"Kecap\",\"qty\":\"500ml\"}]");
		request.setContent("<p>This is content</p>");
		request.setDraft(false);
		request.setUserId(1L);

		String requestbody = om.writeValueAsString(request);

		MvcResult mr = mockMvc.perform(post("/api/v1/recipe/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestbody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data saved"))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testSuccessEditRecipe() throws Exception {
		int recipeId = 1;

		RecipeDtoAdd request = new RecipeDtoAdd();
		request.setTitle("Soto Goreng");
		request.setOverview("Soto Goreng Merdeka");
		request.setTagIds(Set.of(3));
		request.setIngredients("[{\"name\":\"Ayam\",\"qty\":\"500gr\"},{\"name\":\"Kol\",\"qty\":\"500gr\"}]");
		request.setContent("<p>This is content</p>");
		request.setUserId(1L);

		String requestbody = om.writeValueAsString(request);

		MvcResult mr = mockMvc.perform(put("/api/v1/recipe/"+ recipeId + "/edit")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestbody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data updated"))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testSuccessGetPublishedRecipes() throws Exception {
		MvcResult mr = mockMvc.perform(get("/api/v1/recipe/search")
						.queryParam("title", "Bubur")
						.queryParam("author", "")
						.queryParam("page", "0"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data retrieved"))
				.andExpect(content().string(containsString("Bubur Ayam")))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testSuccessAddViewer() throws Exception {
		MvcResult mr = mockMvc.perform(put("/api/v1/recipe/addview")
						.queryParam("recipeId", "2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data updated"))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());

	}






}
