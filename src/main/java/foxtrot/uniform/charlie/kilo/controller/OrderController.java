package foxtrot.uniform.charlie.kilo.controller;

import foxtrot.uniform.charlie.kilo.model.Order;
import foxtrot.uniform.charlie.kilo.model.dto.OrderResponseDto;
import foxtrot.uniform.charlie.kilo.service.OrderService;
import foxtrot.uniform.charlie.kilo.service.ShoppingCartService;
import foxtrot.uniform.charlie.kilo.service.UserService;
import foxtrot.uniform.charlie.kilo.service.implementation.dto.MapperToDto;
import foxtrot.uniform.charlie.kilo.service.implementation.dto.OrderResponseMapperToDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final MapperToDto<OrderResponseDto, Order> responseMapper;

    @Autowired
    public OrderController(OrderService orderService, UserService userService,
                           ShoppingCartService shoppingCartService,
                           OrderResponseMapperToDto responseMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.responseMapper = responseMapper;
    }

    @GetMapping
    public List<OrderResponseDto> getHistory(@RequestParam Long userId) {
        return orderService.getOrdersHistory(userService.get(userId)).stream()
                .map(responseMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/complete")
    public void completeOrder(@RequestParam Long userId) {
        orderService.completeOrder(shoppingCartService.getByUser(userService.get(userId)));
    }
}
