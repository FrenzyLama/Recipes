package recipes.applicationlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.datalayer.RecipeEntity;
import recipes.datalayer.RecipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    public List<RecipeDTO> getRecipesByCategory(String category) {
        List<RecipeDTO> dtoList = new ArrayList<>();
        List<RecipeEntity> entityList = recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
        entityList.forEach(entity ->
                dtoList.add(recipeMapper.mapToDTO(entity))
        );
        return dtoList;
    }

    public List<RecipeDTO> getRecipesByName(String name) {
        List<RecipeDTO> dtoList = new ArrayList<>();
        List<RecipeEntity> entityList = recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
        entityList.forEach(entity ->
                dtoList.add(recipeMapper.mapToDTO(entity))
        );
        return dtoList;
    }

    public void updateRecipe(long recipeId, String username, RecipeDTO dtoToUpdate) {
        Optional<RecipeEntity> existEntity = recipeRepository.findById(recipeId);
        if (existEntity.isPresent()) {
            RecipeEntity existRecipe = existEntity.get();
            if (!Objects.equals(existRecipe.getUser(), username)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            RecipeEntity entityForUpdate = recipeMapper.mapToEntity(dtoToUpdate);
            RecipeEntity updatedEntity = updateFieldsOfEntity(existRecipe, entityForUpdate);
            updatedEntity.setId(recipeId);
            updatedEntity.setUser(username);
            recipeRepository.save(updatedEntity);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Long saveRecipe(RecipeDTO dtoToSave, String username) {
        RecipeEntity recipeEntity = recipeMapper.mapToEntity(dtoToSave);
        recipeEntity.setUser(username);
        return recipeRepository.save(recipeEntity).getId();
    }

    public RecipeDTO getRecipeById(long id) {
        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(id);
        if (recipeEntity.isPresent()) {
            return recipeMapper.mapToDTO(recipeEntity.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteRecipe(long recipeId, String username) {
        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(recipeId);
        if (recipeEntity.isPresent()) {
            RecipeEntity recipe = recipeEntity.get();
            if (!Objects.equals(recipe.getUser(), username)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            recipeEntity.ifPresent(recipeRepository::delete);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private RecipeEntity updateFieldsOfEntity(RecipeEntity entityFromDB, RecipeEntity update) {
        RecipeEntity entity = new RecipeEntity();
        // Name
        if (update.getName() == null) {
            entity.setName(entityFromDB.getName());
        } else {
            entity.setName(update.getName());
        }
        // Category
        if (update.getCategory() == null) {
            entity.setCategory(entityFromDB.getCategory());
        } else {
            entity.setCategory(update.getCategory());
        }
        // Description
        if (update.getDescription() == null) {
            entity.setDescription(entityFromDB.getDescription());
        } else {
            entity.setDescription(update.getDescription());
        }
        // Ingredients
        if (update.getIngredients() == null) {
            entity.setIngredients(entityFromDB.getIngredients());
        } else {
            entity.setIngredients(update.getIngredients());
        }
        // Directions
        if (update.getDirections() == null) {
            entity.setDirections(entityFromDB.getDirections());
        } else {
            entity.setDirections(update.getDirections());
        }

        return entity;
    }
}
