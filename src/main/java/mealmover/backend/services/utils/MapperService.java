package mealmover.backend.services.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.requests.ProductCreateRequestDto;
import mealmover.backend.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MapperService {
    private final ObjectMapper objectMapper;

    public ProductCreateRequestDto parseProductCreateData(String data) {
        try {
            log.info("Attempt to parse product data");

            ProductCreateRequestDto requestDto = this.objectMapper.readValue(data, ProductCreateRequestDto.class);

            log.info("Product data has been parsed");

            return requestDto;
        } catch(JsonProcessingException e) {
            throw new BadRequestException("Failed to parse product create data: " + e.getMessage());
        }
    }
}