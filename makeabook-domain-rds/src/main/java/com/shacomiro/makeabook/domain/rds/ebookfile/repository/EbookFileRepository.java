package com.shacomiro.makeabook.domain.rds.ebookfile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFile;

@Repository
public interface EbookFileRepository extends JpaRepository<EbookFile, Long> {
	Optional<EbookFile> findByUuid(String uuid);
}
