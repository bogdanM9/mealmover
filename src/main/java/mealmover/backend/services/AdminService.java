package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.DriverCreateRequestDto;
import mealmover.backend.dtos.requests.OperatorCreateRequestDto;
import mealmover.backend.dtos.responses.DriverResponseDto;
import mealmover.backend.dtos.responses.OperatorResponseDto;
import mealmover.backend.repositories.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    private final DriverService driverService;

    private final OperatorService operatorService;

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    public DriverResponseDto addDriver(DriverCreateRequestDto driverCreateRequestDto) {
        return driverService.create(driverCreateRequestDto);
    }

    public List<DriverResponseDto> getDrivers() {
        return driverService.getAll();
    }

    public void deleteDriver(UUID driverId) {
        driverService.deleteById(driverId);
    }


    public OperatorResponseDto addOperator(OperatorCreateRequestDto operatorCreateRequestDto) {
        return operatorService.create(operatorCreateRequestDto);
    }

    public List<OperatorResponseDto> getOperators() {
        return operatorService.getAll();
    }

    public void deleteOperator(UUID operatorId) {
        operatorService.deleteById(operatorId);
    }

}
