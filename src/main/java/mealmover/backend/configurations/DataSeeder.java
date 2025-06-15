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
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final AllergenRepository allergenRepository;

    private final IngredientRepository ingredientRepository;

    private final ExtraIngredientRepository extraIngredientRepository;

    private final SizeRepository sizeRepository;

    private final ProductSizeRepository productSizeRepository;

    private final ProductService productService;



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
            new CategoryData("Pizza", "american.png"),
            new CategoryData("Mexican", "indian.png"),
            new CategoryData("Italian", "italian.png"),
            new CategoryData("American", "mexican.png"),
            new CategoryData("Japanese", "japanese.png"),
            new CategoryData("Korean", "korean.png"),
            new CategoryData("Bakery", "bakery.png"),
            new CategoryData("Desserts", "desserts.png"),
            new CategoryData("Drinks", "drinks.png"),
            new CategoryData("Salads", "salads.png"),
            new CategoryData("Seafood", "seafood.png")
        );

        categoriesData.forEach(this.categoryService::seed);

        log.info("Categories seeding completed.");
    }

    private void seedProducts(){
        log.info("Seeding products...");
        List<ProductData> productsData = List.of(
                new ProductData(
            "Pizza Margherita",
          "pizza_margherita.jpg",
          "Pizza",
                    List.of(
                            new SizeData("Mica", 100, 300),
                            new SizeData("Medie", 120, 400),
                            new SizeData("Mare", 150, 500)
                    ),
                    List.of(
                        new IngredientData(
                       "Mozzarella",
                             List.of(new AllergenData("Lactoza"), new AllergenData("Histamina"))
                         ),
                        new IngredientData(
                            "Rosii",
                            List.of(new AllergenData("Sulfiti"), new AllergenData("Fosfati"))
                        )
                    ),
                    List.of(
                        new ExtraIngredientData("Sos rosii dulce", 4, 30),
                        new ExtraIngredientData("Sos rosii iute", 5, 30)
                    )
                ),
                new ProductData(
                        "Coca-Cola",
                        "coca_cola.jpg",
                        "Drinks",
                        List.of(
                                new SizeData("Mica", 100, 300),
                                new SizeData("Medie", 120, 400),
                                new SizeData("Mare", 150, 500)
                        ),
                        List.of(
                                new IngredientData(
                                        "Apa",
                                        List.of(new AllergenData("Sulfiti"))
                                ),
                                new IngredientData(
                                        "Zahar",
                                        List.of(new AllergenData("Histamina"))
                                )
                        ),
                        List.of(
                                new ExtraIngredientData("Gheata", 2, 20)
                        )
                ),
                new ProductData(
                        "Taco Mexican",
                        "taco_mexican.jpg",
                        "Mexican",
                        List.of(
                                new SizeData("Mica", 100, 300),
                                new SizeData("Medie", 120, 400),
                                new SizeData("Mare", 150, 500)
                        ),
                        List.of(
                                new IngredientData(
                                        "Carne tocata",
                                        List.of(new AllergenData("Lactoza"))
                                ),
                                new IngredientData(
                                        "Legume proaspete",
                                        List.of(new AllergenData("Sulfiti"))
                                )
                        ),
                        List.of(
                                new ExtraIngredientData("Sos de avocado", 6, 30)
                        )
                )
        );

       productsData.forEach(this.productService::seed);
    }

//    public void seedProducts() {
//        log.info("Seeding products...");
//
//        CategoryModel pizzaCategory = categoryRepository.findById(UUID.fromString("e0d8d1f5-4a89-4a00-9d2e-ac062be90160")).orElseThrow();
//        CategoryModel drinksCategory = categoryRepository.findByName("Bauturi");
//        CategoryModel mexicanCategory = categoryRepository.findByName("Mexican");
//        CategoryModel americanCategory = categoryRepository.findByName("American");
//
//        List<IngredientModel> ingredients = List.of(
//                new IngredientModel("Unt", Set.of(
//                        new AllergenModel("Lactoza"),
//                        new AllergenModel("Histamina")
//                )),
//                new IngredientModel("Miere", Set.of(
//                        new AllergenModel("Oleuropeina"),
//                        new AllergenModel("Histamina"),
//                        new AllergenModel("Sulfiti")
//                ))
//        );
//
//        Set<ExtraIngredientModel> extraIngredients = Set.of(
//                new ExtraIngredientModel("Sos rosii dulce", 4, 30),
//                new ExtraIngredientModel("Sos rosii iute", 5, 30)
//        );
//
//        Set<ProductSizeModel> sizes = Set.of(
//                new ProductSizeModel("Mica", 100, 300),
//                new ProductSizeModel("Medie", 120, 400),
//                new ProductSizeModel("Mare", 150, 500)
//        );
//
//        for (int i = 1; i <= 5; i++) {
//            productRepository.save(new ProductModel("Pizza Margherita " + i, "uri", pizzaCategory, sizes, ingredients, extraIngredients));
//            productRepository.save(new ProductModel("Coca-Cola " + i, "uri", drinksCategory, sizes, ingredients, extraIngredients));
//            productRepository.save(new ProductModel("Taco Mexican " + i, "uri", mexicanCategory, sizes, ingredients, extraIngredients));
//            productRepository.save(new ProductModel("Burger American " + i, "uri", americanCategory, sizes, ingredients, extraIngredients));
//        }
//
//        log.info("Products seeding completed.");
//    }


//    public void seedProducts() {
//        log.info("Seeding products...");

        // Uncomment the following lines to seed products


//        CategoryModel pizza = categoryRepository.findByName("Pizza").orElseThrow();
//        CategoryModel bauturi = categoryRepository.findByName("Bauturi").orElseThrow();
//        CategoryModel mexican = categoryRepository.findByName("Mexican").orElseThrow();
//        CategoryModel american = categoryRepository.findByName("American").orElseThrow();
//
//
//        AllergenModel lactoza = allergenRepository.save(new AllergenModel("Lactoza"));
//        AllergenModel histamina = allergenRepository.save(new AllergenModel("Histamina"));
//        AllergenModel oleuropeina = allergenRepository.save(new AllergenModel("Oleuropeina"));
//        AllergenModel sulfiti = allergenRepository.save(new AllergenModel("Sulfiti"));
//
//
//        IngredientModel unt = ingredientRepository.save(new IngredientModel("Unt", Set.of(lactoza, histamina)));
//        IngredientModel miere = ingredientRepository.save(new IngredientModel("Miere", Set.of(oleuropeina, histamina, sulfiti)));
//
//        ExtraIngredientModel sosDulce = extraIngredientRepository.save(new ExtraIngredientModel("Sos rosii dulce", 4f, 30));
//        ExtraIngredientModel sosIute = extraIngredientRepository.save(new ExtraIngredientModel("Sos rosii iute", 5f, 30));
//
//
//        SizeModel mica = sizeRepository.findByName("Mica").orElseThrow();
//        SizeModel medie = sizeRepository.findByName("Medie").orElseThrow();
//        SizeModel mare = sizeRepository.findByName("Mare").orElseThrow();
//
//        for (int i = 1; i <= 5; i++) {
//            ProductModel pizzaProduct = new ProductModel("Pizza Margherita " + i, "img/pizza.jpg", pizza, null, Set.of(unt, miere), Set.of(sosDulce, sosIute));
//            productRepository.save(pizzaProduct);
//            productSizeRepository.save(new ProductSizeModel(mica, pizzaProduct));
//            productSizeRepository.save(new ProductSizeModel(medie, pizzaProduct));
//            productSizeRepository.save(new ProductSizeModel(mare, pizzaProduct));
//
//            ProductModel drink = new ProductModel("Suc Fructe " + i, "img/suc.jpg", bauturi, null, Set.of(unt), Set.of(sosDulce));
//            productRepository.save(drink);
//            productSizeRepository.save(new ProductSizeModel(mica, drink));
//
//            ProductModel mexicanProd = new ProductModel("Taco " + i, "img/taco.jpg", mexican, null, Set.of(miere), Set.of(sosIute));
//            productRepository.save(mexicanProd);
//            productSizeRepository.save(new ProductSizeModel(mica, mexicanProd));
//            productSizeRepository.save(new ProductSizeModel(medie, mexicanProd));
//
//            ProductModel burger = new ProductModel("Burger " + i, "img/burger.jpg", american, null, Set.of(unt, miere), Set.of(sosDulce));
//            productRepository.save(burger);
//            productSizeRepository.save(new ProductSizeModel(mare, burger));
//        }

//        log.info("Products seeding completed.");
//    }
    }