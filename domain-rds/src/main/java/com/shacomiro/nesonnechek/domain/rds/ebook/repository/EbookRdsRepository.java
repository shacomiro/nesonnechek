package com.shacomiro.nesonnechek.domain.rds.ebook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shacomiro.nesonnechek.domain.rds.ebook.entity.Ebook;
import com.shacomiro.nesonnechek.domain.rds.user.entity.User;

public interface EbookRdsRepository extends JpaRepository<Ebook, Long> {
	Optional<Ebook> findByUuid(String uuid);

	Optional<Ebook> findByUuidAndUser(String uuid, User user);

	List<Ebook> findAllByUser(User user);

	Page<Ebook> findAllByUser(Pageable pageable, User user);

	void deleteAllByUser(User user);
}
