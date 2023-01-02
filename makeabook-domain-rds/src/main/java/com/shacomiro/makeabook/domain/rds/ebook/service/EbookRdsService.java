package com.shacomiro.makeabook.domain.rds.ebook.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;
import com.shacomiro.makeabook.domain.rds.ebook.repository.EbookRdsRepository;

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

	public Optional<Ebook> findByUuid(String uuid) {
		return ebookRdsRepository.findByUuid(uuid);
	}
}
