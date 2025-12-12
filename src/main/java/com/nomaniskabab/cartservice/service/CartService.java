package com.nomaniskabab.cartservice.service;

import com.nomaniskabab.cartservice.dto.MenuItemResponse;
import com.nomaniskabab.cartservice.entity.CartItem;
import com.nomaniskabab.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository repository;
    private final RestTemplate restTemplate;   // ← IMPORTANT

    public CartItem addToCart(Long userId, Long menuItemId, int quantity) {

        // Call Menu Service to fetch full details
        String url = "http://localhost:8084/api/menus/" + menuItemId;

        MenuItemResponse menuItem = restTemplate.getForObject(url, MenuItemResponse.class);

        if (menuItem == null) {
            throw new RuntimeException("Menu item not found in menu service");
        }

        CartItem item = CartItem.builder()
                .userId(userId)
                .menuItemId(menuItemId)
                .name(menuItem.getName())
                .price(menuItem.getPrice())
                .quantity(quantity)
                .imageUrl(menuItem.getImageUrl())
                .build();

        return repository.save(item);
    }

    public List<CartItem> getUserCart(Long userId) {
        return repository.findByUserId(userId);
    }

    public void removeItem(Long cartId) {
        repository.deleteById(cartId);
    }

    public void clearCart(Long userId) {
        repository.deleteByUserId(userId);
    }
}
