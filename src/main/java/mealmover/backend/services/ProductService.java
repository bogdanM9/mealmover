package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.requests.ProductCreateRequestDto;
import mealmover.backend.dtos.responses.ProductResponseDto;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.ProductMapper;
import mealmover.backend.models.*;
import mealmover.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final SizeService sizeService;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final IngredientService ingredientService;
    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;
    private final ExtraIngredientService extraIngredientService;

    @Transactional
    public ProductResponseDto create(MultipartFile image, ProductCreateRequestDto productCreateRequestDto) {
        String name = productCreateRequestDto.getName();

        String imageUri = this.fileStorageService.storeImage(image, false);

        if (this.productRepository.existsByName(name)) {
            log.error("Product with name {} already exists", name);
            throw new ConflictException("There is already a product with this name");
        }

        CategoryModel categoryModel = this.categoryService.getModelById(productCreateRequestDto.getCategoryId());

        ProductModel productModel = this.productMapper.toModel(productCreateRequestDto);

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

        ProductModel savedProduct = this.productRepository.save(productModel);
        System.out.println(savedProduct);

        return this.productMapper.toDto(savedProduct);
    }


//    public ReviewResponseDto addReview(ReviewCreateRequestDto reviewCreateRequestDto) {
//        if (this.reviewService.existsByClientIdAndProductId(reviewCreateRequestDto.getClientId(), reviewCreateRequestDto.getProductId())) {
//            log.error("Client with id {} already reviewed product with id {}", reviewCreateRequestDto.getClientId(), reviewCreateRequestDto.getProductId());
//            throw new ConflictException("You already reviewed this product");
//        }
//
//        ClientModel client = this.clientService.getModelById(reviewCreateRequestDto.getClientId());
//
//        ProductModel product = this.repository
//            .findById(reviewCreateRequestDto.getProductId())
//            .orElseThrow(() -> new NotFoundException(this.messages.notfoundById()));
//
//        return this.reviewService.create(reviewCreateRequestDto, client, product);
//    }


    public ProductResponseDto getById(UUID id) {
        ProductModel productModel = this.productRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("There is no product with this id"));
        return this.productMapper.toDto(productModel);
    }

    public ProductModel getModelById(UUID id) {
        return this.productRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("There is no product with this id"));
    }

    @Transactional(readOnly = true)
    public Set<ProductResponseDto> getAll() {
        List<ProductModel> models = this.productRepository.findAll();

        return models.stream().map(model -> {
            ProductResponseDto dto = this.productMapper.toDto(model);

            if(model.getReviews().isEmpty()) {
                dto.setRating(0f);
                return dto;
            }

            float rating = model.getReviews()
                .stream()
                .map(ReviewModel::getRating)
                .reduce(0f, Float::sum) / model.getReviews().size();

            dto.setRating(rating);

            return dto;
        }).collect(Collectors.toSet());
    }

    public void deleteById(UUID id) {
        if (!this.productRepository.existsById(id)) {
            throw new NotFoundException("There is no product with this id");
        }
        this.productRepository.deleteById(id);
        this.sizeService.deleteOrphans();
    }

    public void deleteAll() {
        this.productRepository.deleteAll();
        this.sizeService.deleteOrphans();
    }

    public SizeModel getSizeModelById(UUID productSizeId) {
        return this.sizeService.getModelById(productSizeId);
    }
}