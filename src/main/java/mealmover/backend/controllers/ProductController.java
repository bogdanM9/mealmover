package mealmover.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.ProductCreateRequestDto;
import mealmover.backend.dtos.requests.ReviewCreateRequestDto;
import mealmover.backend.dtos.responses.ProductResponseDto;
import mealmover.backend.dtos.responses.ReviewResponseDto;
import mealmover.backend.services.ProductService;
import mealmover.backend.services.utils.MapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final MapperService mapperService;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(
        @RequestParam("image") MultipartFile image,
        @RequestParam("data") String data
    ) {
        System.out.println(data);
        ProductCreateRequestDto requestDto = this.mapperService.parseProductCreateData(data);
        ProductResponseDto responseDto = this.productService.create(image, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/top-rated-food")
    public ResponseEntity<List<ProductResponseDto>> getTopRatedFood(
            @RequestParam(name = "limit", required = false, defaultValue = "4") int limit
    ) {
        List<ProductResponseDto> responseDtos = this.productService.getTopRatedFood(limit);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/top-rated-drinks")
    public ResponseEntity<List<ProductResponseDto>> getTopRatedDrinks(
        @RequestParam(name = "limit", required = false, defaultValue = "4") int limit
    ) {
        List<ProductResponseDto> responseDtos = this.productService.getTopRatedDrinks(limit);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/top-rated-products")
    public ResponseEntity<List<ProductResponseDto>> getTopRatedProducts(
        @RequestParam(name = "limit", required = false, defaultValue = "4") int limit
    ) {
        List<ProductResponseDto> responseDtos = this.productService.getTopRatedProducts(limit);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping
    public ResponseEntity<Set<ProductResponseDto>> getAll() {
        Set<ProductResponseDto> responseDtos = this.productService.getAll();
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable("id") UUID id) {
        ProductResponseDto responseDtos = this.productService.getById(id);
        return ResponseEntity.ok(responseDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") UUID id) {
        this.productService.deleteById(id);
        return ResponseEntity.ok("Product deleted");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        this.productService.deleteAll();
        return ResponseEntity.ok("All products have been deleted");
    }
}