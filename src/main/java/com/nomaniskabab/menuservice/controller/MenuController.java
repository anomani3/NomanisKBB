package com.nomaniskabab.menuservice.controller;

import com.nomaniskabab.menuservice.entity.MenuItem;
import com.nomaniskabab.menuservice.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService service;

    @PostMapping
    public MenuItem addMenu(@RequestBody MenuItem item) {
        return service.save(item);
    }

    @GetMapping
    public List<MenuItem> getAllMenus() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MenuItem getMenuById(@PathVariable Long id) {
        return service.getById(id);
    }
}
