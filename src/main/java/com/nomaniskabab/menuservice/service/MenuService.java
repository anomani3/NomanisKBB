package com.nomaniskabab.menuservice.service;

import com.nomaniskabab.menuservice.entity.MenuItem;
import com.nomaniskabab.menuservice.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;

    public MenuItem save(MenuItem item) {
        return repository.save(item);
    }

    public List<MenuItem> getAll() {
        return repository.findAll();
    }

    public MenuItem getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<MenuItem> getByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category);
    }

    public MenuItem update(Long id, MenuItem item) {
        item.setId(id);
        return repository.save(item);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
