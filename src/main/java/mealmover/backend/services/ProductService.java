package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.ProductCreateRequestDto;
import mealmover.backend.dtos.responses.ProductResponseDto;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.ProductMapper;
import mealmover.backend.models.*;
import mealmover.backend.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final SizeService sizeService;
    private final CategoryService categoryService;
    private final IngredientService ingredientService;
    private final FileStorageService fileStorageService;
    private final ExtraIngredientService extraIngredientService;
    private final ProductMapper mapper;
    private final ProductRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductResponseDto create(MultipartFile image, ProductCreateRequestDto productCreateRequestDto) {
        String name = productCreateRequestDto.getName();
        String imageUri = this.fileStorageService.storeImage(image, false);

        if (this.repository.existsByName(name)) {
            logger.error("Product with name {} already exists", name);
            throw new ConflictException("There is already a product with this name");
        }

        CategoryModel categoryModel = this.categoryService.getModelById(productCreateRequestDto.getCategoryId());

        ProductModel productModel = this.mapper.toModel(productCreateRequestDto);

        productModel.setImageUri(imageUri);
        productModel.setCategory(categoryModel);

        productModel.setIngredients(
            productModel.getIngredients()
                .stream()
                .map(ingredientService::getOrCreate)
                .collect(Collectors.toSet()
            )
        );

        productModel.setExtraIngredients(
            productModel.getExtraIngredients()
                .stream()
                .map(extraIngredientService::getOrCreate)
                .collect(Collectors.toSet()
            )
        );

        productModel.setProductSizes(
            productCreateRequestDto.getSizes()
                .stream()
                .map(sizeCreateDto -> {
                    SizeModel sizeModel = this.sizeService.getOrCreate(sizeCreateDto);
                    return new ProductSizeModel(sizeModel, productModel);
                })
                .collect(Collectors.toSet()
            )
        );

        ProductModel savedProduct = this.repository.save(productModel);
        System.out.println(savedProduct);

        return this.mapper.toDto(savedProduct);
    }


    public ProductResponseDto getById(UUID id) {
        ProductModel productModel = this.repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));

        return this.mapper.toDto(productModel);
    }

    public ProductModel getModelById(UUID id) {
        return this.repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product not found"));
    }


    public Set<ProductResponseDto> getAll() {
        return this.repository.findAll()
            .stream()
            .map(this.mapper::toDto)
            .collect(Collectors.toSet());
    }

    public void deleteById(UUID id) {
        if (!this.repository.existsById(id)) {
            throw new NotFoundException("Product not found");
        }
        this.repository.deleteById(id);
        this.sizeService.deleteOrphans();
    }

    public void deleteAll() {
        this.repository.deleteAll();
        this.sizeService.deleteOrphans();
    }
}
