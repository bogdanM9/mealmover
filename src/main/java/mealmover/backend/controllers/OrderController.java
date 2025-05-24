package mealmover.backend.controllers;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.OrderCreateRequestDto;
import mealmover.backend.dtos.requests.UpdateStatusRequestDto;
import mealmover.backend.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public void create(@RequestBody OrderCreateRequestDto order) {
        this.orderService.create(order);
    }

    @PatchMapping("/{id}/update_status")
    public void updateStatus(@PathVariable UUID id, @RequestBody UpdateStatusRequestDto status) {
        this.orderService.updateStatus(id, status);
    }
}
