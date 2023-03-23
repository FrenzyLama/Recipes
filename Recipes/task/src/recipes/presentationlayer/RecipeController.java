package recipes.presentationlayer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.applicationlayer.RecipeDTO;
import recipes.applicationlayer.RecipeService;
import recipes.security.ReciepesUserDetails;
import recipes.security.RecipesUserDetailsService;
import recipes.security.UserEntity;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;


@RestController
@Validated
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    private RecipesUserDetailsService userDetailsService;

    @PostMapping("/api/recipe/new")
    public Id saveRecipe(@AuthenticationPrincipal UserDetails details,@RequestBody @Valid RecipeDTO recipe) {
        String username = details.getUsername();
        return new Id(recipeService.saveRecipe(recipe, username));
    }

    @GetMapping("/api/recipe/{id}")
    public @ResponseBody RecipeDTO getRecipe(@PathVariable long id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping(value = "/api/recipe/search", params = "category")
    public @ResponseBody List<RecipeDTO> searchRecipesByCategory(@RequestParam String category) {
        return recipeService.getRecipesByCategory(category);
    }

    @GetMapping(value = "/api/recipe/search", params = "name")
    public @ResponseBody List<RecipeDTO> searchRecipesByName(@RequestParam String name) {
       return recipeService.getRecipesByName(name);
    }

    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@AuthenticationPrincipal UserDetails details,@PathVariable long id) {
    String username = details.getUsername();
        recipeService.deleteRecipe(id, username);
    }

    @PutMapping("/api/recipe/{id}")
    public void updateRecipe(@AuthenticationPrincipal UserDetails details, @PathVariable long id, @RequestBody @Valid  RecipeDTO recipe) {
        String username = details.getUsername();
        recipeService.updateRecipe(id, username, recipe);
    }
}