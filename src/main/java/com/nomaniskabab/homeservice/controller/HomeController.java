package com.nomaniskabab.homeservice.controller;

import com.nomaniskabab.homeservice.model.Banner;
import com.nomaniskabab.homeservice.model.Restaurant;
import com.nomaniskabab.homeservice.service.HomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/banners")
    public List<Banner> banners() {
        return homeService.getAllBanners();
    }

    @GetMapping("/restaurant")
    public List<Restaurant> restaurant() {
        return homeService.getRestaurants();
    }
}
