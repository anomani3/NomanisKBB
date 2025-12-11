package com.nomaniskabab.homeservice.repository;
import com.nomaniskabab.homeservice.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}

