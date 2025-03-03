package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.OrderProductExtraIngredientCreateRequestDto;
import mealmover.backend.dtos.requests.UpdateStatusRequestDto;
import mealmover.backend.dtos.requests.OrderCreateRequestDto;
import mealmover.backend.dtos.requests.OrderProductCreateRequestDto;
import mealmover.backend.enums.Status;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.OrderMapper;
import mealmover.backend.models.*;
import mealmover.backend.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper mapper;
    private final OrderRepository repository;
    private final OrderStatusHistoryService orderStatusHistoryService;
    private final ProductSizeService productSizeService;
    private final ClientService clientService;
    private final StatusService statusService;
    private final ExtraIngredientService extraIngredientService;
    private final AddressService addressService;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Transactional
    public void create(OrderCreateRequestDto orderCreateRequestDto) {
        logger.info("Creating order");
        System.out.println(orderCreateRequestDto.getAddressId());
        StatusModel statusModel = this.statusService.getOrCreate(Status.PENDING.name());

        ClientModel clientModel = orderCreateRequestDto.getAddressId() != null
                ? this.clientService.getModelById(orderCreateRequestDto.getClientId())
                : null;

        AddressModel addressModel = orderCreateRequestDto.getAddressId() != null
                ? this.addressService.getModelById(orderCreateRequestDto.getAddressId())
                : this.addressService.save(clientModel, orderCreateRequestDto.getAddress());

        OrderModel orderModel = this.mapper.toModel(orderCreateRequestDto);

        orderModel.setStatus(statusModel);
        orderModel.setClient(clientModel);
        orderModel.setAddress(addressModel);

        for (OrderProductCreateRequestDto orderProductCreateRequestDto : orderCreateRequestDto.getProducts()) {
                Set<OrderProductExtraIngredientModel> extraIngredientModelList = new HashSet<>();

            if(orderProductCreateRequestDto.getExtraIngredients() != null) {
                for (OrderProductExtraIngredientCreateRequestDto extraIngredient : orderProductCreateRequestDto.getExtraIngredients()) {
                    System.out.println(extraIngredient.getExtraIngredientId());

                    extraIngredientModelList.add(new OrderProductExtraIngredientModel(
                            extraIngredient.getQuantity(),
                            this.extraIngredientService.getModelById(extraIngredient.getExtraIngredientId())
                    ));

                }
            }

                OrderProductModel orderProductModel = new OrderProductModel(
                    orderProductCreateRequestDto.getQuantity(),
                    this.productSizeService.getModelById(orderProductCreateRequestDto.getProductSizeId()),
                        orderModel, extraIngredientModelList
                );
                orderModel.getOrderProducts().add(orderProductModel);

        }

        this.repository.save(orderModel);
    }

    private OrderModel getModelById(UUID orderId) {
        return this.repository.findById(orderId)
            .orElseThrow(() -> {
                logger.error("Order with id {} not found", orderId);
                return new NotFoundException("Order not found");
            });
    }

    public void updateStatus(UUID orderId, UpdateStatusRequestDto requestDto) {
        logger.info("Updating order status");

        OrderModel orderModel = this.getModelById(orderId);

        StatusModel statusModel = this.statusService.getModelById(requestDto.getStatusId());

        orderModel.setStatus(statusModel);

        OrderStatusHistoryModel orderStatusHistoryModel = new OrderStatusHistoryModel(orderModel, statusModel);

        this.orderStatusHistoryService.save(orderStatusHistoryModel);

        this.repository.save(orderModel);
    }
}