package mealmover.backend.controllers;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.ProductCreateRequestDto;
import mealmover.backend.dtos.responses.ProductResponseDto;
import mealmover.backend.services.ProductService;
import mealmover.backend.services.utils.MapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        ProductCreateRequestDto requestDto = this.mapperService.parseProductData(data);
        ProductResponseDto responseDto = this.productService.create(image, requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
