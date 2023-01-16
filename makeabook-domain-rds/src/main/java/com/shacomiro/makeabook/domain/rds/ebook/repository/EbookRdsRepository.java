package com.shacomiro.makeabook.domain.rds.ebook.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;

public interface EbookRdsRepository extends JpaRepository<Ebook, Long> {
	Optional<Ebook> findByUuid(String uuid);

	Page<Ebook> findAllByUserId(Pageable pageable, Long userId);
}
