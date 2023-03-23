package recipes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import recipes.datalayer.RecipeRepository;

@SpringBootApplication
public class RecipesApplication {
    public static void main(String[] args) {

        SpringApplication.run(RecipesApplication.class, args);
    }

//    @Component
//    public class Runner implements CommandLineRunner {
//        private final RecipeRepository repository;
//
//        public Runner(RecipeRepository repository) {
//            this.repository = repository;
//        }
//
//        @Override
//        public void run(String... args) {
//            // work with the repository here
//        }
//    }

}
