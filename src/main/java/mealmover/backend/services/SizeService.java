package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.SizeCreateDto;
import mealmover.backend.dtos.requests.CreateSizeRequestDto;
import mealmover.backend.dtos.responses.SizeResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.SizeMapper;
import mealmover.backend.models.SizeModel;
import mealmover.backend.records.SizeData;
import mealmover.backend.repositories.SizeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class SizeService {
    private final SizeMapper mapper;
    private final SizeRepository repository;

    public SizeResponseDto create(CreateSizeRequestDto requestDto) {
        String name = requestDto.getName();

        log.info("Attempting to create an Size with name: {}", name);

        SizeModel sizeModel = this.mapper.toModel(requestDto);

        SizeModel savedSizeModel = this.repository.save(sizeModel);

        log.info("Successfully created Size with name: {}", name);

        return this.mapper.toDto(savedSizeModel);
    }

    public List<SizeResponseDto> getAll() {
        log.info("Getting all Sizes");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    public SizeResponseDto getById(UUID id) {
        log.info("Getting size by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException("Size not found by id: " + id));
    }


    public void deleteById(UUID id) {
        log.info("Getting Size by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException("Size not found by id: " + id);
        }

        this.repository.deleteById(id);

        log.info("Size with id {} deleted!", id);
    }

    public void deleteAll() {
        log.info("Deleting all Sizes..");
        this.repository.deleteAll();
        log.info("Sizes deleted!");
    }

    public SizeModel getModelById(UUID sizeId) {
        return this.repository
            .findById(sizeId)
            .orElseThrow(() -> new NotFoundException("Size not found by id: " + sizeId));
    }

    public SizeModel getOrCreate(SizeCreateDto sizeCreateDto) {
        String name = sizeCreateDto.getName();
        int weight = sizeCreateDto.getWeight();
        float price = sizeCreateDto.getPrice();

        log.info("Attempting to get or create size with name: {}", name);

        SizeModel sizeModel = this.mapper.toModel(sizeCreateDto);

        return this.repository
            .findByNameAndWeightAndPrice(name, weight, price)
            .orElseGet(() -> this.repository.save(sizeModel));
    }

    public SizeModel getOrCreate(SizeData sizeData) {
        String name = sizeData.name();
        int weight = sizeData.weight();
        float price = sizeData.price();

        log.info("Attempting to get or create size with name: {}", name);

        return this.repository
            .findByNameAndWeightAndPrice(name, weight, price)
            .orElseGet(() -> this.repository.save(new SizeModel(name, weight, price)));
    }

    public void deleteOrphans() {
        List<SizeModel> orphans = this.repository.findAllOrphans();
        orphans.forEach(size -> {
            log.info("Deleting orphan size with id: {}", size.getId());
            this.repository.deleteById(size.getId());
        });
    }

}
