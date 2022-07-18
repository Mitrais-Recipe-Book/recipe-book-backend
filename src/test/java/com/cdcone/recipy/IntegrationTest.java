package com.cdcone.recipy;

import com.cdcone.recipy.recipe.dto.request.AddCommentRequestDto;
import com.cdcone.recipy.recipe.dto.request.RecipeAddRequestDto;
import com.cdcone.recipy.recipe.dto.request.RecipeFavoriteRequestDto;
import com.cdcone.recipy.recipe.dto.request.RecipeReactionRequestDto;
import com.cdcone.recipy.user.dto.request.ChangePasswordRequestDto;
import com.cdcone.recipy.user.dto.request.UpdateUserRequestDto;
import com.cdcone.recipy.util.ImageUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Set;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IntegrationTest {

	@Autowired
	MockMvc mockMvc;

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
				.andExpect(jsonPath("$.payload.data").isArray())
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
				.andExpect(jsonPath("$.message").value("failed: tag already exists"))
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
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("failed: tag  not found."))
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
		RecipeAddRequestDto request = new RecipeAddRequestDto();
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

		RecipeAddRequestDto request = new RecipeAddRequestDto();
		request.setTitle("Soto Goreng");
		request.setOverview("Soto Goreng Merdeka");
		request.setTagIds(Set.of(3));
		request.setIngredients("[{\"name\":\"Ayam\",\"qty\":\"500gr\"},{\"name\":\"Kol\",\"qty\":\"500gr\"}]");
		request.setContent("<p>This is content</p>");
		request.setUserId(1L);

		String requestbody = om.writeValueAsString(request);

		MvcResult mr = mockMvc.perform(put("/api/v1/recipe/" + recipeId + "/edit")
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
				.queryParam("title", "Roti")
				.queryParam("author", "")
				.queryParam("page", "0"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data retrieved"))
				.andExpect(content().string(containsString("Roti")))
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

	@Test
	void testSuccessGetPopularRecipe() throws Exception {
		MvcResult mr = mockMvc.perform(get("/api/v1/recipe/popular")
				.queryParam("limit", "5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data retrieved"))
				.andExpect(content().string(containsString("Bubur")))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testSuccessGetDiscoverRecipe() throws Exception {
		MvcResult mr = mockMvc.perform(get("/api/v1/recipe/discover")
				.queryParam("limit", "5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data retrieved"))
				.andExpect(content().string(containsString("Roti")))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testSuccessDeleteRecipe() throws Exception {
		MvcResult mr = mockMvc.perform(delete("/api/v1/recipe/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data deleted"))
				.andExpect(content().string(containsString("Ayam")))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testSuccessGetByIdRecipe() throws Exception {
		MvcResult mr = mockMvc.perform(get("/api/v1/recipe/3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data retrieved"))
				.andExpect(content().string(containsString("Roti")))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testSuccessSaveAndGetRecipePhoto() throws Exception {
		Resource r = new ClassPathResource("roti.jpg");
		MockMultipartFile multipartFile = new MockMultipartFile("photo", "roti.jpg", MediaType.IMAGE_JPEG_VALUE,
				r.getInputStream());

		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/v1/recipe/3/photo");
		builder.with(request -> {
			request.setMethod("PUT");
			return request;
		});

		HashMap<String, String> contentTypeParams = new HashMap<>();
		contentTypeParams.put("boundary", "265001916915724");
		MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

		MvcResult mr = mockMvc.perform(builder.file(multipartFile).contentType(mediaType))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: image updated"))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());

		mockMvc.perform(get("/api/v1/recipe/3/photo"))
				.andExpect(status().isOk())
				.andReturn();

	}

	@Test
	void testFailSaveUserPhotoIfUserNotFound() throws Exception {
		byte[] random = { 12, 12 };
		String username = "notfound123";
		mockMvc.perform(multipart("/api/v1/user/" + username + "/photo")
				.file("photo", random)
				.with(it -> {
					it.setMethod("PUT");
					return it;
				}))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("User not found."))
				.andReturn();
	}

	@Test
	void testFailSaveUserPhotoIfInvalidImage() throws Exception {
		byte[] random = { 12, 13 };
		String username = "user1";
		mockMvc.perform(multipart("/api/v1/user/" + username + "/photo")
				.file("photo", random)
				.with(it -> {
					it.setMethod("PUT");
					return it;
				}))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Uploaded file is not an image"))
				.andReturn();
	}

	@Test
	void testSuccessSaveUserPhoto() throws Exception {
		byte[] random = ImageUtil.randomImage();
		String username = "user1";
		mockMvc.perform(multipart("/api/v1/user/" + username + "/photo")
				.file("photo", random)
				.with(it -> {
					it.setMethod("PUT");
					return it;
				}))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Success"))
				.andReturn();
	}

	@Test
	void testSuccessGetRecipeReaction() throws Exception {
		String recipeId = "3";
		String username = "user1";

		MvcResult result = mockMvc.perform(get("/api/v1/recipe/" + recipeId + "/reaction")
				.queryParam("username", username))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data retrieved"))
				.andExpect(jsonPath("$.payload.reactionList[0].reaction").isNotEmpty())
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	void testSuccessSaveRecipeReaction() throws Exception {
		String recipeId = "3";
		RecipeReactionRequestDto requestDto = new RecipeReactionRequestDto();
		requestDto.setUsername("user1");
		requestDto.setReaction("DISLIKED");

		String requestbody = om.writeValueAsString(requestDto);

		MvcResult mr = mockMvc.perform(post("/api/v1/recipe/" + recipeId + "/reaction")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestbody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data saved"))
				.andExpect(jsonPath("$.payload.reaction").value("DISLIKED"))
				.andExpect(jsonPath("$.payload.recipeId").value(3))
				.andExpect(jsonPath("$.payload.userId").value(1))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testSuccessDeleteRecipeReaction() throws Exception {
		String recipeId = "1";
		RecipeReactionRequestDto requestDto = new RecipeReactionRequestDto();
		requestDto.setUsername("user1");
		requestDto.setReaction("LIKED");

		String requestbody = om.writeValueAsString(requestDto);

		MvcResult mr = mockMvc.perform(delete("/api/v1/recipe/" + recipeId + "/reaction/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestbody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data deleted"))
				.andExpect(jsonPath("$.payload.reaction").value("LIKED"))
				.andExpect(jsonPath("$.payload.recipeId").value(1))
				.andExpect(jsonPath("$.payload.userId").value(1))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testSuccessAddComment() throws Exception {
		String recipeId = "3";
		AddCommentRequestDto requestDto = new AddCommentRequestDto("user1", "Horay!");

		String requestbody = om.writeValueAsString(requestDto);

		MvcResult mr = mockMvc.perform(post("/api/v1/recipe/" + recipeId + "/comment/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestbody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: comment added"))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testFailedAddCommentRecipeNotFound() throws Exception {
		String recipeId = "10";
		AddCommentRequestDto requestDto = new AddCommentRequestDto("user1", "Horay!");

		String requestbody = om.writeValueAsString(requestDto);

		MvcResult mr = mockMvc.perform(post("/api/v1/recipe/" + recipeId + "/comment/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestbody))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("failed: cannot find recipe with id " + recipeId))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testFailedAddCommentUserNotFound() throws Exception {
		String recipeId = "1";
		AddCommentRequestDto requestDto = new AddCommentRequestDto("user50", "Horay!");

		String requestbody = om.writeValueAsString(requestDto);

		MvcResult mr = mockMvc.perform(post("/api/v1/recipe/" + recipeId + "/comment/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestbody))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message")
						.value("failed: user with username " + requestDto.getUsername() + " not found"))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testFailedAddCommentUnpublishedRecipe() throws Exception {
		String recipeId = "4";
		AddCommentRequestDto requestDto = new AddCommentRequestDto("user1", "Horay!");

		String requestbody = om.writeValueAsString(requestDto);

		MvcResult mr = mockMvc.perform(post("/api/v1/recipe/" + recipeId + "/comment/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestbody))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("failed: cannot comment on unpublished recipe"))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testFailedAddCommentNoCommentAttached() throws Exception {
		String recipeId = "2";
		AddCommentRequestDto requestDto = new AddCommentRequestDto("user1", "");

		String requestbody = om.writeValueAsString(requestDto);

		MvcResult mr = mockMvc.perform(post("/api/v1/recipe/" + recipeId + "/comment/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestbody))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("failed: cannot post null comment"))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testFailUpdateUserIfAlreadyExist() throws Exception {
		String username = "user1";
		UpdateUserRequestDto updateUserDto = new UpdateUserRequestDto("laptoppp", "laptophp@gmail.com");

		mockMvc.perform(put("/api/v1/user/" + username + "/profile")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(updateUserDto)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Failed to update user. Email is already exists"))
				.andReturn();
	}

	@Test
	void testFailUpdateUserIfNotFound() throws Exception {
		String username = "user1123";
		UpdateUserRequestDto updateUserDto = new UpdateUserRequestDto("user 123", "user1@mail.com");

		mockMvc.perform(put("/api/v1/user/" + username + "/profile")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(updateUserDto)))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Failed: user not found"))
				.andReturn();
	}

	@Test
	void testSuccessUpdateUser() throws Exception {
		String username = "testingggg";
		UpdateUserRequestDto updateUserDto = new UpdateUserRequestDto("user edit", "aaaaa@mail.com");

		mockMvc.perform(put("/api/v1/user/" + username + "/profile")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(updateUserDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Success: user updated"))
				.andReturn();
	}

	@Test
	void testSuccessSaveRecipeFavorite() throws Exception {
		String username = "user1";
		String recipeId = "4";

		RecipeFavoriteRequestDto requestDto = new RecipeFavoriteRequestDto(username);

		MvcResult mr = mockMvc.perform(post("/api/v1/recipe/" + recipeId + "/favorite")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(requestDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data saved"))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testSuccessDeleteRecipeFavorite() throws Exception {
		String username = "user1";
		String recipeId = "3";

		RecipeFavoriteRequestDto requestDto = new RecipeFavoriteRequestDto(username);

		MvcResult mr = mockMvc.perform(delete("/api/v1/recipe/" + recipeId + "/favorite")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(requestDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data deleted"))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testSuccessGetRecipeFavorite() throws Exception {
		String username = "user1";
		String recipeId = "4";

		RecipeFavoriteRequestDto requestDto = new RecipeFavoriteRequestDto(username);

		MvcResult mr = mockMvc.perform(get("/api/v1/recipe/" + recipeId + "/favorite")
				.queryParam("username", username))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data retrieved"))
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testFailAddTagViewIfNotFound() throws Exception {
		int tagId = 999;
		mockMvc.perform(put("/api/v1/tag/addview?tagId=" + tagId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("failed: tag not found"))
				.andReturn();
	}

	@Test
	void testSuccessAddTagView() throws Exception {
		int tagId = 1;
		mockMvc.perform(put("/api/v1/tag/addview?tagId=" + tagId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data updated"))
				.andReturn();
	}

	@Test
	void testSuccessGetUserRecipesFavoritePaginated() throws Exception {
		String username = "user1";

		MvcResult mr = mockMvc.perform(get("/api/v1/user/" + username + "/favorite-recipe")
				.queryParam("isPaginated", "true")
				.queryParam("page", "0")
				.queryParam("size", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data retrieved"))
				.andExpect(jsonPath("$.payload.data").isNotEmpty())
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());
	}

	@Test
	void testSuccessGetUserRecipesFavoriteNonPaginated() throws Exception {
		String username = "user1";

		MvcResult mr = mockMvc.perform(get("/api/v1/user/" + username + "/favorite-recipe")
				.queryParam("isPaginated", "false")
				.queryParam("page", "0")
				.queryParam("size", "10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data retrieved"))
				.andExpect(jsonPath("$.payload.data").isNotEmpty())
				.andReturn();

		System.out.println(mr.getResponse().getContentAsString());

	}

	@Test
	void successRequestCreator() throws Exception {
		String username = "grrr";

		mockMvc.perform(post("/api/v1/user/" + username + "/request-creator"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success"))
				.andReturn();
	}

	@Test
	void failedRequestCreator() throws Exception {
		String username = "noname";

		mockMvc.perform(post("/api/v1/user/" + username + "/request-creator"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("failed: " + username + " not found"))
				.andReturn();
	}

	@Test
	void successAssignRole() throws Exception {
		String username = "grrr";
		String rolename = "Admin";

		mockMvc.perform(post("/api/v1/user/" + username + "/assign-" + rolename))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success"))
				.andReturn();
	}

	@Test
	void failedAssignRoleUserNotFound() throws Exception {
		String username = "notfound";
		String rolename = "Admin";

		mockMvc.perform(post("/api/v1/user/" + username + "/assign-" + rolename))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("failed: " + username + " not found"))
				.andReturn();
	}

	@Test
	void failedAssignRoleRoleNotFound() throws Exception {
		String username = "grrr";
		String rolename = "notfound";

		mockMvc.perform(post("/api/v1/user/" + username + "/assign-" + rolename))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("failed: role " + rolename + " not found")).andReturn();
	}

	@Test
	void testSuccessGetTagsAndView() throws Exception {
		mockMvc.perform(get("/api/v1/tag/all"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("success: data retrieved"))
				.andExpect(jsonPath("$.payload").isArray())
				.andExpect(jsonPath("$.payload[0].name").value("breakfast"))
				.andExpect(jsonPath("$.payload[0].views").value(4))
				.andReturn();
	}

	@Test
	void testSuccessGetAllUsers() throws Exception {
		mockMvc.perform(get("/api/v1/user"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.payload.data").isArray())
				.andReturn();
	}

	@Test
	void changePassword_willReturnBadRequest_whenOldPasswordNotMatch() throws Exception {
		String username = "user1";
		String oldPassword = "wrong_old_password";
		String newPassword = "new_password";
		ChangePasswordRequestDto requestDto = new ChangePasswordRequestDto(oldPassword, newPassword, newPassword);

		mockMvc.perform(put("/api/v1/user/" + username + "/profile/change-password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsBytes(requestDto)))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	void changePassword_willReturnBadRequest_whenConfirmPasswordNotMatch() throws Exception {
		String username = "user1";
		String oldPassword = "user123";
		String newPassword = "new_password";
		String confirmPassword = "confirm_password";
		ChangePasswordRequestDto requestDto = new ChangePasswordRequestDto(oldPassword, newPassword, confirmPassword);

		mockMvc.perform(put("/api/v1/user/" + username + "/profile/change-password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsBytes(requestDto)))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	void changePassword_willReturnOk_whenPasswordMatch() throws Exception {
		String username = "user1";
		String oldPassword = "user123";
		String newPassword = "new_password";
		String confirmPassword = "new_password";
		ChangePasswordRequestDto requestDto = new ChangePasswordRequestDto(oldPassword, newPassword, confirmPassword);

		mockMvc.perform(put("/api/v1/user/" + username + "/profile/change-password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsBytes(requestDto)))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
}
