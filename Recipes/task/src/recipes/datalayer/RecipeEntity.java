package recipes.datalayer;

import com.sun.istack.NotNull;
import org.springframework.data.annotation.CreatedDate;
import recipes.security.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RECIPES")
public class RecipeEntity {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "category")
    private String category;

    @CreatedDate
    @Column(name = "date")
    private LocalDateTime date;

    @NotBlank
    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "recipe_user")
    private String user;

    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "directions", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient")
    private List<String> ingredients = new ArrayList<>();

    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "direction")
    private List<String> directions = new ArrayList<>();

    @PrePersist
    public void onInsert() {
        date = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        date = LocalDateTime.now();
    }

    public RecipeEntity() {
    }

    public RecipeEntity(String name, String category, LocalDateTime date, String description, String user, List<String> ingredients, List<String> directions) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.description = description;
        this.user = user;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
