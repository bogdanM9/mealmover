package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.constants.PendingClientConstants;
import mealmover.backend.dtos.responses.PendingClientResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.PendingClientMapper;
import mealmover.backend.repositories.PendingClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PendingClientService {
    private final PendingClientMapper pendingClientMapper;
    private final PendingClientRepository pendingClientRepository;

////    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(fixedRate = 10000 * 30)
//    public void deleteOldPendingClients() {
//        log.info("Deleting old pending clients..");
//
////        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
//        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(5);
//
//        int deletedCount = this.pendingClientRepository.deleteByCreatedAtBefore(cutoffTime);
//
//        log.info("Deleted {} old pending clients", deletedCount);
//    }

    public List<PendingClientResponseDto> getAll() {
        log.info("Getting all pending clients");
        return this.pendingClientRepository
            .findAll()
            .stream()
            .map(this.pendingClientMapper::toDto)
            .toList();
    }

    public PendingClientResponseDto getById(UUID id) {
        log.info("Getting pending client by id: {}", id);
        return this.pendingClientRepository
            .findById(id)
            .map(this.pendingClientMapper::toDto)
            .orElseThrow(() -> new NotFoundException(PendingClientConstants.NOT_FOUND_BY_ID));
    }
}
