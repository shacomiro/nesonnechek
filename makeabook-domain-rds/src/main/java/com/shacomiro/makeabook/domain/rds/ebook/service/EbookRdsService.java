package com.shacomiro.makeabook.domain.rds.ebook.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;
import com.shacomiro.makeabook.domain.rds.ebook.repository.EbookRdsRepository;
import com.shacomiro.makeabook.domain.rds.user.entity.User;

@Service
@Transactional
public class EbookRdsService {
	private final EbookRdsRepository ebookRdsRepository;

	public EbookRdsService(EbookRdsRepository ebookRdsRepository) {
		this.ebookRdsRepository = ebookRdsRepository;
	}

	public Ebook save(Ebook ebook) {
		return ebookRdsRepository.save(ebook);
	}

	public Optional<Ebook> findByUuidAndUser(String uuid, User user) {
		return ebookRdsRepository.findByUuidAndUser(uuid, user);
	}

	public Page<Ebook> findAllByUserId(Pageable pageable, Long userId) {
		return ebookRdsRepository.findAllByUserId(pageable, userId);
	}
}
