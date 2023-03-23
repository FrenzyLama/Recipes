package recipes.datalayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<RecipeEntity, Long>{
    List<RecipeEntity> findByNameContainingIgnoreCaseOrderByDateDesc(String name);
    List<RecipeEntity> findByCategoryIgnoreCaseOrderByDateDesc(String category);
}
