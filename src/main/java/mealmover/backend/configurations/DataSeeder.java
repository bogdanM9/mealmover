package mealmover.backend.configurations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.requests.RoleCreateRequestDto;
import mealmover.backend.enums.Role;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.models.*;
import mealmover.backend.records.*;
import mealmover.backend.repositories.*;
import mealmover.backend.services.CategoryService;
import mealmover.backend.services.ProductService;
import mealmover.backend.services.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final RoleService roleService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Value("${application.data.seeding.enabled:true}")
    private boolean seedingEnabled;

    @Override
    public void run(String... args) {
        if (!seedingEnabled) {
            log.info("Data seeding is disabled. Skipping...");
            return;
        }

        log.info("Seeding data...");

        seedRoles();
        seedCategories();
        seedProducts();

        log.info("Data seeding completed.");
    }

    private void seedRoles() {
        log.info("Seeding roles...");

        RoleCreateRequestDto requestDto = RoleCreateRequestDto
            .builder()
            .name(Role.CLIENT.toCapitalize())
            .build();

        if(!this.roleService.existsByName(Role.CLIENT.toCapitalize())) {
            this.roleService.create(requestDto);
        }

        log.info("Roles seeding completed.");
    }

    private void seedCategories() {
        log.info("Seeding categories...");

        List<CategoryData> categoriesData = List.of(
            new CategoryData("Pizza", "pizza.png"),
            new CategoryData("Korean", "korean.png"),
            new CategoryData("Bakery", "bakery.png"),
            new CategoryData("Drinks", "drinks.png"),
            new CategoryData("Salads", "salads.png"),
            new CategoryData("Mexican", "mexican.png"),
            new CategoryData("Italian", "italian.png"),
            new CategoryData("Seafood", "seafood.png"),
            new CategoryData("American", "american.png"),
            new CategoryData("Japanese", "japanese.png"),
            new CategoryData("Desserts", "desserts.png")
        );

        categoriesData.forEach(this.categoryService::seed);

        log.info("Categories seeding completed.");
    }

    private void seedProducts(){
        log.info("Seeding products...");

        List<ProductData> productsData = List.of(
            // Pizza
            new ProductData(
                "Margherita Pizza",
                "pizza_margherita.jpg",
                "Pizza",
                List.of(
                    new SizeData("Small", 5.0f, 300),
                    new SizeData("Medium", 7.0f, 400),
                    new SizeData("Large", 9.0f, 550)
                ),
                List.of(
                    new IngredientData("Mozzarella", List.of(new AllergenData("Lactose"), new AllergenData("Histamine"))),
                    new IngredientData("Tomato Sauce", List.of(new AllergenData("Sulphites"))),
                    new IngredientData("Basil", List.of())
                ),
                List.of(new ExtraIngredientData("Extra Mozzarella", 1.5f, 50))
            ),
            new ProductData(
                "Pepperoni Pizza",
                "pizza_pepperoni.jpg",
                "Pizza",
                List.of(
                    new SizeData("Medium", 8.5f, 450),
                    new SizeData("Large", 11.0f, 600)
                ),
                List.of(
                    new IngredientData("Pepperoni", List.of(new AllergenData("Sodium Nitrite"), new AllergenData("Sulphites"))),
                    new IngredientData("Mozzarella", List.of(new AllergenData("Lactose"))),
                    new IngredientData("Tomato Sauce", List.of(new AllergenData("Sulphites")))
                ),
                List.of(new ExtraIngredientData("Jalapeños", 1.0f, 30))
            ),
            new ProductData(
                "Quattro Formaggi Pizza",
                "pizza_4_cheese.jpg",
                "Pizza",
                List.of(
                    new SizeData("Medium", 9.0f, 500),
                    new SizeData("Large", 12.0f, 650)
                ),
                List.of(
                    new IngredientData("Mozzarella", List.of(new AllergenData("Lactose"))),
                    new IngredientData("Gorgonzola", List.of(new AllergenData("Lactose"))),
                    new IngredientData("Parmesan", List.of(new AllergenData("Lactose"))),
                    new IngredientData("Emmental", List.of(new AllergenData("Lactose")))
                ),
                List.of(new ExtraIngredientData("Truffle Oil", 2.5f, 20))
            ),

            // Mexican
            new ProductData(
                "Beef Taco",
                "taco_beef.jpg",
                "Mexican",
                List.of(new SizeData("Piece", 3.5f, 1)),
                List.of(
                        new IngredientData("Beef", List.of()),
                        new IngredientData("Tortilla (wheat)", List.of(new AllergenData("Gluten"))),
                        new IngredientData("Lettuce", List.of())
                ),
                List.of(new ExtraIngredientData("Guacamole", 1.2f, 25))
            ),
            new ProductData(
                "Chicken Burrito",
                "burrito_chicken.jpg",
                "Mexican",
                List.of(new SizeData("Standard", 6.0f, 1)),
                List.of(
                    new IngredientData("Chicken", List.of()),
                    new IngredientData("Rice", List.of()),
                    new IngredientData("Black Beans", List.of()),
                    new IngredientData("Cheddar Cheese", List.of(new AllergenData("Lactose")))
                ),
                List.of(new ExtraIngredientData("Sour Cream", 1.0f, 30))
            ),
            new ProductData(
                "Cheese Quesadilla",
                "quesadilla_cheese.jpg",
                "Mexican",
                List.of(new SizeData("Standard", 5.5f, 1)),
                List.of(
                        new IngredientData("Tortilla (wheat)", List.of(new AllergenData("Gluten"))),
                        new IngredientData("Mozzarella", List.of(new AllergenData("Lactose"))),
                        new IngredientData("Cheddar", List.of(new AllergenData("Lactose")))
                ),
                List.of(new ExtraIngredientData("Pico de Gallo", 0.8f, 20))
            ),

            // Italian
            new ProductData(
                "Spaghetti Carbonara",
                "carbonara.jpg",
                "Italian",
                List.of(new SizeData("Regular", 8.0f, 400)),
                List.of(
                        new IngredientData("Spaghetti (wheat)", List.of(new AllergenData("Gluten"))),
                        new IngredientData("Egg", List.of(new AllergenData("Egg"))),
                        new IngredientData("Parmesan", List.of(new AllergenData("Lactose"))),
                        new IngredientData("Pancetta", List.of(new AllergenData("Sodium Nitrite")))
                ),
                List.of()
            ),
            new ProductData(
                "Lasagna Bolognese",
                "lasagna.jpg",
                "Italian",
                List.of(new SizeData("Portion", 9.5f, 450)),
                List.of(
                        new IngredientData("Lasagna Sheets (wheat)", List.of(new AllergenData("Gluten"))),
                        new IngredientData("Beef Ragù", List.of(new AllergenData("Sulphites"))),
                        new IngredientData("Bechamel Sauce", List.of(new AllergenData("Lactose"), new AllergenData("Egg")))
                ),
                List.of(new ExtraIngredientData("Parmesan extra", 1.5f, 30))
            ),
            new ProductData(
                "Penne Arrabbiata",
                "arrabbiata.jpg",
                "Italian",
                List.of(new SizeData("Regular", 7.0f, 400)),
                List.of(
                        new IngredientData("Penne (wheat)", List.of(new AllergenData("Gluten"))),
                        new IngredientData("Tomato Chili Sauce", List.of(new AllergenData("Sulphites"))),
                        new IngredientData("Parsley", List.of())
                ),
                List.of()
            ),

            // American
            new ProductData(
                "Cheeseburger",
                "cheeseburger.jpg",
                "American",
                List.of(new SizeData("Single", 6.0f, 1)),
                List.of(
                        new IngredientData("Beef Patty", List.of()),
                        new IngredientData("Cheddar Cheese", List.of(new AllergenData("Lactose"))),
                        new IngredientData("Wheat Bun", List.of(new AllergenData("Gluten"))),
                        new IngredientData("Pickles", List.of())
                ),
                List.of(new ExtraIngredientData("Bacon", 1.5f, 30))
            ),
            new ProductData(
                "Hot Dog",
                "hotdog.jpg",
                "American",
                List.of(new SizeData("Single", 4.5f, 1)),
                List.of(
                    new IngredientData("Sausage", List.of(new AllergenData("Sodium Nitrite"))),
                    new IngredientData("Wheat Bun", List.of(new AllergenData("Gluten"))),
                    new IngredientData("Onion", List.of())
                ),
                List.of(new ExtraIngredientData("Cheese sauce", 1.0f, 25))
            ),
            new ProductData(
                "BBQ Ribs",
                "bbq_ribs.jpg",
                "American",
                List.of(new SizeData("Half Rack", 12.0f, 500)),
                List.of(
                        new IngredientData("Pork Ribs", List.of()),
                        new IngredientData("BBQ Sauce", List.of(new AllergenData("Sulfites")))
                ),
                List.of(new ExtraIngredientData("Coleslaw", 1.5f, 100))
            ),

            // Japanese
            new ProductData(
                "Sushi Platter",
                "sushi_platter.jpg",
                "Japanese",
                List.of(new SizeData("Assorted", 14.0f, 10)),
                List.of(
                        new IngredientData("Rice", List.of()),
                        new IngredientData("Seaweed", List.of()),
                        new IngredientData("Salmon", List.of()),
                        new IngredientData("Tuna", List.of()),
                        new IngredientData("Soy Sauce", List.of(new AllergenData("Soy"), new AllergenData("Gluten")))
                ),
                List.of(new ExtraIngredientData("Wasabi", 0.5f, 5))
            ),
            new ProductData(
                "Ramen",
                "ramen.jpg",
                "Japanese",
                List.of(new SizeData("Regular", 10.0f, 500)),
                List.of(
                        new IngredientData("Wheat Noodles", List.of(new AllergenData("Gluten"))),
                        new IngredientData("Chicken Broth", List.of(new AllergenData("Soy"), new AllergenData("Sulphites"))),
                        new IngredientData("Egg", List.of(new AllergenData("Egg"))),
                        new IngredientData("Pork", List.of())
                ),
                List.of()
            ),
            new ProductData(
                    "Tempura",
                    "tempura.jpg",
                    "Japanese",
                    List.of(new SizeData("Portion", 9.0f, 200)),
                    List.of(
                            new IngredientData("Shrimp", List.of()),
                            new IngredientData("Vegetables", List.of()),
                            new IngredientData("Batter (wheat)", List.of(new AllergenData("Gluten"), new AllergenData("Egg")))
                    ),
                    List.of(new ExtraIngredientData("Tempura Sauce", 0.7f, 30))
            ),

            // Korean
            new ProductData(
                    "Bibimbap",
                    "bibimbap.jpg",
                    "Korean",
                    List.of(new SizeData("Bowl", 8.5f, 400)),
                    List.of(
                            new IngredientData("Rice", List.of()),
                            new IngredientData("Beef", List.of()),
                            new IngredientData("Vegetables", List.of()),
                            new IngredientData("Egg", List.of(new AllergenData("Egg"))),
                            new IngredientData("Gochujang", List.of())
                    ),
                    List.of()
            ),
            new ProductData(
                    "Kimchi",
                    "kimchi.jpg",
                    "Korean",
                    List.of(new SizeData("Portion", 4.0f, 150)),
                    List.of(
                            new IngredientData("Napa Cabbage", List.of()),
                            new IngredientData("Chili Pepper", List.of()),
                            new IngredientData("Garlic", List.of())
                    ),
                    List.of()
            ),
            new ProductData(
                    "Korean Fried Chicken",
                    "korean_fried_chicken.jpg",
                    "Korean",
                    List.of(new SizeData("Portion", 11.0f, 300)),
                    List.of(
                            new IngredientData("Chicken", List.of()),
                            new IngredientData("Batter (wheat)", List.of(new AllergenData("Gluten"))),
                            new IngredientData("Soy Sauce Glaze", List.of(new AllergenData("Soy"), new AllergenData("Sulphites")))
                    ),
                    List.of()
            ),

            // Bakery
            new ProductData(
                    "Croissant",
                    "croissant.jpg",
                    "Bakery",
                    List.of(new SizeData("Piece", 2.5f, 70)),
                    List.of(
                            new IngredientData("Wheat Flour", List.of(new AllergenData("Gluten"))),
                            new IngredientData("Butter", List.of(new AllergenData("Lactose")))
                    ),
                    List.of()
            ),
            new ProductData(
                "Pain au Chocolat",
                "pain_chocolat.jpg",
                "Bakery",
                List.of(new SizeData("Piece", 3.0f, 80)),
                List.of(
                    new IngredientData("Wheat Flour", List.of(new AllergenData("Gluten"))),
                    new IngredientData("Butter", List.of(new AllergenData("Lactose"))),
                    new IngredientData("Chocolate", List.of(new AllergenData("Soy")))
                ),
                List.of()
            ),
            new ProductData(
                    "Bagel with Cream Cheese",
                    "bagel_cream_cheese.jpg",
                    "Bakery",
                    List.of(new SizeData("Piece", 4.0f, 120)),
                    List.of(
                            new IngredientData("Wheat Flour", List.of(new AllergenData("Gluten"))),
                            new IngredientData("Cream Cheese", List.of(new AllergenData("Lactose")))
                    ),
                    List.of(new ExtraIngredientData("Smoked Salmon", 2.0f, 30))
            ),

            // Desserts
            new ProductData(
                "Tiramisu",
                "tiramisu.jpg",
                "Desserts",
                List.of(new SizeData("Slice", 4.5f, 120)),
                List.of(
                        new IngredientData("Mascarpone", List.of(new AllergenData("Lactose"))),
                        new IngredientData("Egg", List.of(new AllergenData("Egg"))),
                        new IngredientData("Coffee", List.of())
                ),
                List.of()
            ),
            new ProductData(
                "Cheesecake",
                "cheesecake.jpg",
                "Desserts",
                List.of(new SizeData("Slice", 5.0f, 130)),
                List.of(
                        new IngredientData("Cream Cheese", List.of(new AllergenData("Lactose"))),
                        new IngredientData("Egg", List.of(new AllergenData("Egg"))),
                        new IngredientData("Wheat Crust", List.of(new AllergenData("Gluten")))
                ),
                List.of()
            ),
            new ProductData(
                "Panna Cotta",
                "panna_cotta.jpg",
                "Desserts",
                List.of(new SizeData("Portion", 4.0f, 110)),
                List.of(
                        new IngredientData("Cream", List.of(new AllergenData("Lactose"))),
                        new IngredientData("Gelatin", List.of())
                ),
                List.of()
            ),
            // Drinks
            new ProductData(
                "Coca-Cola 330ml",
                "coca_cola.jpg",
                "Drinks",
                List.of(new SizeData("Standard", 2.0f, 330)),
                List.of(
                        new IngredientData("Carbonated Water", List.of()),
                        new IngredientData("Sugar", List.of()),
                        new IngredientData("Caffeine", List.of())
                ),
                List.of()
            ),
            new ProductData(
                "Sparkling Water",
                "sparkling_water.jpg",
                "Drinks",
                List.of(new SizeData("Standard", 1.5f, 500)),
                List.of(
                        new IngredientData("Carbonated Mineral Water", List.of())
                ),
                List.of()
            ),
            new ProductData(
                "Orange Juice",
                "orange_juice.jpg",
                "Drinks",
                List.of(new SizeData("Glass", 2.5f, 300)),
                List.of(
                        new IngredientData("100% Orange Juice", List.of(new AllergenData("Histamine")))
                ),
                List.of()
            ),
            new ProductData(
                "Espresso",
                "espresso.jpg",
                "Drinks",
                List.of(
                    new SizeData("Single", 1.8f, 30),
                    new SizeData("Double", 2.5f, 60)
                ),
                List.of(
                    new IngredientData("Arabica Coffee", List.of())
                ),
                List.of()
            ),

            // Salads
            new ProductData(
                    "Caesar Salad",
                    "caesar_salad.jpg",
                    "Salads",
                    List.of(new SizeData("Bowl", 7.0f, 300)),
                    List.of(
                        new IngredientData("Romaine Lettuce", List.of()),
                        new IngredientData("Grilled Chicken", List.of()),
                        new IngredientData("Parmesan", List.of(new AllergenData("Lactose"))),
                        new IngredientData("Croutons", List.of(new AllergenData("Gluten"))),
                        new IngredientData("Caesar Dressing", List.of(new AllergenData("Egg"), new AllergenData("Fish")))
                    ),
                    List.of(new ExtraIngredientData("Bacon", 1.2f, 30))
            ),
            new ProductData(
                    "Greek Salad",
                    "greek_salad.jpg",
                    "Salads",
                    List.of(new SizeData("Bowl", 6.5f, 280)),
                    List.of(
                        new IngredientData("Tomatoes", List.of()),
                        new IngredientData("Cucumber", List.of()),
                        new IngredientData("Feta Cheese", List.of(new AllergenData("Lactose"))),
                        new IngredientData("Olives", List.of()),
                        new IngredientData("Onion", List.of())
                    ),
                    List.of(new ExtraIngredientData("Olive Oil", 0.5f, 15))
            ),
            new ProductData(
                "Quinoa Avocado Salad",
                "quinoa_avocado_salad.jpg",
                "Salads",
                List.of(new SizeData("Bowl", 7.5f, 320)),
                List.of(
                    new IngredientData("Quinoa", List.of()),
                    new IngredientData("Avocado", List.of()),
                    new IngredientData("Cherry Tomatoes", List.of()),
                    new IngredientData("Spinach", List.of())
                ),
                List.of(new ExtraIngredientData("Goat Cheese", 1.0f, 30))
            ),

            // Seafood
            new ProductData(
                    "Grilled Salmon",
                    "grilled_salmon.jpg",
                    "Seafood",
                    List.of(new SizeData("Portion", 13.0f, 200)),
                    List.of(
                        new IngredientData("Salmon", List.of(new AllergenData("Fish"))),
                        new IngredientData("Lemon", List.of()),
                        new IngredientData("Olive Oil", List.of())
                    ),
                    List.of(new ExtraIngredientData("Garlic Butter", 1.0f, 20))
            ),
            new ProductData(
                "Fried Calamari",
                "fried_calamari.jpg",
                "Seafood",
                List.of(new SizeData("Portion", 11.0f, 180)),
                List.of(
                    new IngredientData("Squid", List.of(new AllergenData("Molluscs"))),
                    new IngredientData("Flour Batter", List.of(new AllergenData("Gluten"), new AllergenData("Egg"))),
                    new IngredientData("Lemon", List.of())
                ),
                List.of(new ExtraIngredientData("Aioli Sauce", 0.8f, 25))
            ),
            new ProductData(
                "Shrimp Cocktail",
                "shrimp_cocktail.jpg",
                "Seafood",
                List.of(new SizeData("Glass", 12.0f, 150)),
                List.of(
                    new IngredientData("Shrimp", List.of(new AllergenData("Crustaceans"))),
                    new IngredientData("Cocktail Sauce", List.of(new AllergenData("Egg"), new AllergenData("Mustard")))
                ),
                List.of(new ExtraIngredientData("Extra Shrimp", 2.0f, 40))
            )
        );

        productsData.forEach(this.productService::seed);
    }
}