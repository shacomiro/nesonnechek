package com.shacomiro.makeabook.api.global.hateoas.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.shacomiro.makeabook.api.ebook.api.EbookRestApi;
import com.shacomiro.makeabook.api.ebook.dto.EbookFileModel;
import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFile;

@Component
public class EbookFileResponseModelAssembler extends RepresentationModelAssemblerSupport<EbookFile, EbookFileModel> {

	public EbookFileResponseModelAssembler() {
		super(EbookRestApi.class, EbookFileModel.class);
	}

	@Override
	public EbookFileModel toModel(EbookFile entity) {
		EbookFileModel ebookFileModel = instantiateModel(entity);

		ebookFileModel.add(linkTo(methodOn(EbookRestApi.class).getEbookFile(entity.getUuid())).withSelfRel());
		if (!entity.isExpired()) {
			ebookFileModel.add(linkTo(methodOn(EbookRestApi.class).downloadEbookFile(entity.getUuid()))
					.withRel("download-ebook"));
		}

		ebookFileModel.setEbookId(entity.getUuid());
		ebookFileModel.setEbookName(entity.getFilename());
		ebookFileModel.setEbookType(entity.getFileType());
		ebookFileModel.setDownloadUrl(entity.getDownloadUrl());
		ebookFileModel.setCreatedAt(entity.getCreatedAt());
		ebookFileModel.setExpiredAt(entity.getExpiredAt());
		ebookFileModel.setOwner(ObjectUtils.isEmpty(entity.getUser()) ? "guest" : entity.getUser().getUsername());

		return ebookFileModel;
	}

	@Override
	public CollectionModel<EbookFileModel> toCollectionModel(Iterable<? extends EbookFile> entities) {
		CollectionModel<EbookFileModel> ebookFileModels = super.toCollectionModel(entities);

		return ebookFileModels;
	}
}