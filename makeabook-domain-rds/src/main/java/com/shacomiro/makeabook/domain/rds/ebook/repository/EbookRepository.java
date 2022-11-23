package com.shacomiro.makeabook.domain.rds.ebook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;

@Repository
public interface EbookRepository extends JpaRepository<Ebook, Long> {
	Optional<Ebook> findByUuid(String uuid);
}
