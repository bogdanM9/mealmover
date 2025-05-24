package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.requests.OrderProductExtraIngredientCreateRequestDto;
import mealmover.backend.dtos.requests.UpdateStatusRequestDto;
import mealmover.backend.dtos.requests.OrderCreateRequestDto;
import mealmover.backend.dtos.requests.OrderProductCreateRequestDto;
import mealmover.backend.enums.Status;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.OrderMapper;
import mealmover.backend.models.*;
import mealmover.backend.repositories.OrderRepository;
import mealmover.backend.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final StatusService statusService;
    private final AddressService addressService;
    private final SecurityService securityService;
    private final OrderRepository orderRepository;
    private final ProductSizeService productSizeService;
    private final ExtraIngredientService extraIngredientService;
    private final OrderStatusHistoryService orderStatusHistoryService;

    @Transactional
    public void create(OrderCreateRequestDto orderCreateRequestDto) {
        log.info("Creating order");

        StatusModel statusModel = this.statusService.getOrCreate(Status.PENDING.name());

        ClientModel clientModel = (ClientModel) this.securityService.getModelCurrentUser();

        AddressModel addressModel = this.addressService.getModelById(orderCreateRequestDto.getAddressId());

        if(addressModel.getClient() != clientModel) {
            throw new NotFoundException("Address not found to this client");
        }

        OrderModel orderModel = this.orderMapper.toModel(orderCreateRequestDto);

        orderModel.setStatus(statusModel);
        orderModel.setClient(clientModel);
        orderModel.setAddress(addressModel);

        for (OrderProductCreateRequestDto orderProductCreateRequestDto : orderCreateRequestDto.getProducts()) {
            Set<OrderProductExtraIngredientModel> extraIngredientModels = new HashSet<>();

            if(orderProductCreateRequestDto.getExtraIngredients() != null) {
                for (OrderProductExtraIngredientCreateRequestDto extraIngredient : orderProductCreateRequestDto.getExtraIngredients()) {
                    System.out.println(extraIngredient.getExtraIngredientId());

                    extraIngredientModels.add(new OrderProductExtraIngredientModel(
                        extraIngredient.getQuantity(),
                        this.extraIngredientService.getModelById(extraIngredient.getExtraIngredientId())
                    ));

                }
            }

            OrderProductModel orderProductModel = new OrderProductModel(
                orderProductCreateRequestDto.getQuantity(),
                this.productSizeService.getModelById(orderProductCreateRequestDto.getProductSizeId()),
                orderModel,
                extraIngredientModels
            );
            orderModel.getOrderProducts().add(orderProductModel);
        }

        this.orderRepository.save(orderModel);
    }

    private OrderModel getModelById(UUID orderId) {
        return this.orderRepository.findById(orderId)
            .orElseThrow(() -> {
                log.error("Order with id {} not found", orderId);
                return new NotFoundException("Order not found");
            });
    }

    public void updateStatus(UUID orderId, UpdateStatusRequestDto requestDto) {
        log.info("Updating order status");

        OrderModel orderModel = this.getModelById(orderId);

        StatusModel statusModel = this.statusService.getModelById(requestDto.getStatusId());

        orderModel.setStatus(statusModel);

        OrderStatusHistoryModel orderStatusHistoryModel = new OrderStatusHistoryModel(orderModel, statusModel);

        this.orderStatusHistoryService.save(orderStatusHistoryModel);

        this.orderRepository.save(orderModel);
    }
}