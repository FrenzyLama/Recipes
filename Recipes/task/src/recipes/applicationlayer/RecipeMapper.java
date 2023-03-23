package recipes.applicationlayer;

import org.springframework.stereotype.Component;
import recipes.datalayer.RecipeEntity;

@Component
public class RecipeMapper {
    public RecipeDTO mapToDTO(RecipeEntity recipeEntity) {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName(recipeEntity.getName());
        recipeDTO.setDescription(recipeEntity.getDescription());
        recipeDTO.setIngredients(recipeEntity.getIngredients());
        recipeDTO.setDirections(recipeEntity.getDirections());
        recipeDTO.setCategory(recipeEntity.getCategory());
        recipeDTO.setDate(recipeEntity.getDate());
        return recipeDTO;
    }

    public RecipeEntity mapToEntity(RecipeDTO dto) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setName(dto.getName());
        recipeEntity.setDescription(dto.getDescription());
        recipeEntity.setIngredients(dto.getIngredients());
        recipeEntity.setDirections(dto.getDirections());
        recipeEntity.setCategory(dto.getCategory());
        return recipeEntity;
    }

}
