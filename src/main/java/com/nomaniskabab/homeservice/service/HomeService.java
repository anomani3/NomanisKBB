package com.nomaniskabab.homeservice.service;

import com.nomaniskabab.homeservice.model.Banner;
import com.nomaniskabab.homeservice.model.Restaurant;
import com.nomaniskabab.homeservice.repository.BannerRepository;
import com.nomaniskabab.homeservice.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private final BannerRepository bannerRepo;
    private final RestaurantRepository restaurantRepo;

    public HomeService(BannerRepository bannerRepo, RestaurantRepository restaurantRepo) {
        this.bannerRepo = bannerRepo;
        this.restaurantRepo = restaurantRepo;
    }

    public List<Banner> getAllBanners() {
        return bannerRepo.findAll();
    }

    public List<Restaurant> getRestaurants() {
        return restaurantRepo.findAll();
    }
}
