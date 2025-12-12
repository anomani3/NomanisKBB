package com.nomaniskabab.cartservice.controller;

import com.nomaniskabab.cartservice.dto.CartRequest;
import com.nomaniskabab.cartservice.dto.MenuItemResponse;
import com.nomaniskabab.cartservice.dto.NotificationRequest;
import com.nomaniskabab.cartservice.entity.CartItem;
import com.nomaniskabab.cartservice.repository.CartRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartRepository repository;
    private final RestTemplate restTemplate;

    private final String MENU_SERVICE_URL = "http://localhost:8084/api/menus";
    private final String NOTIFICATION_URL = "http://localhost:8087/api/notification/send";

    // --------------------------- ADD TO CART ---------------------------
    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(@RequestBody CartRequest request) {

        Long userId = request.getUserId();
        Long menuItemId = request.getMenuItemId();
        int quantity = request.getQuantity();

        // Fetch Menu Item from Menu Service
        MenuItemResponse menu = restTemplate.getForObject(
                MENU_SERVICE_URL + "/" + menuItemId,
                MenuItemResponse.class
        );

        if (menu == null) {
            return ResponseEntity.badRequest().body(null);
        }

        CartItem cartItem = CartItem.builder()
                .userId(userId)
                .menuItemId(menuItemId)
                .name(menu.getName())
                .price(menu.getPrice())
                .quantity(quantity)
                .totalPrice(menu.getPrice() * quantity)
                .imageUrl(menu.getImageUrl())
                .build();

        CartItem savedItem = repository.save(cartItem);

        // 🔔 Send Notification (Item Added)
        restTemplate.postForObject(
                NOTIFICATION_URL,
                new NotificationRequest(
                        userId,
                        "Item '" + menu.getName() + "' added to cart successfully.",
                        "CART"
                ),
                Void.class
        );

        return ResponseEntity.ok(savedItem);
    }

    // --------------------------- GET CART ITEMS BY USER ---------------------------
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserCart(@PathVariable Long userId) {
        return ResponseEntity.ok(repository.findByUserId(userId));
    }

    // --------------------------- REMOVE FROM CART ---------------------------
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeItem(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.badRequest().body("Cart item not found");
        }

        repository.deleteById(id);

        // Optionally send notification
        // restTemplate.postForObject(NOTIFICATION_URL,
        //     new NotificationRequest(null, "Item removed from cart", "CART_REMOVE"), Void.class);

        return ResponseEntity.ok("Item removed successfully");
    }

    // --------------------------- CLEAR CART ---------------------------
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {

        repository.deleteByUserId(userId);

        // 🔔 Send Notification (Cart Cleared)
        restTemplate.postForObject(
                NOTIFICATION_URL,
                new NotificationRequest(
                        userId,
                        "Your cart has been cleared.",
                        "CART_CLEAR"
                ),
                Void.class
        );

        return ResponseEntity.ok("Cart cleared successfully");
    }
}
