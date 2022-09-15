package com.hs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hs.entity.WikimediaData;

public interface WikimediaDataRepository extends JpaRepository<WikimediaData, Long> {
}
