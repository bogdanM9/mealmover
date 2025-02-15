package mealmover.backend.services.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.ProductCreateRequestDto;
import mealmover.backend.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MapperService {
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(MapperService.class);

    public ProductCreateRequestDto parseProductData(String data) {
        try {
            logger.info("Attempt to parse product data");
            ProductCreateRequestDto requestDto = this.objectMapper.readValue(data, ProductCreateRequestDto.class);
            logger.info("Product data has been parsed");
            return requestDto;
        } catch(JsonProcessingException e) {
            throw new BadRequestException("Failed to parse product data: " + e.getMessage());
        }
    }
}