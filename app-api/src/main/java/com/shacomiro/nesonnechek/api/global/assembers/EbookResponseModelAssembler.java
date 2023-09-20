package com.shacomiro.nesonnechek.api.global.assembers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.shacomiro.nesonnechek.api.ebook.api.EbookRestApi;
import com.shacomiro.nesonnechek.api.ebook.dto.model.EbookModel;
import com.shacomiro.nesonnechek.domain.rds.ebook.entity.Ebook;

@Component
public class EbookResponseModelAssembler extends RepresentationModelAssemblerSupport<Ebook, EbookModel> {

	public EbookResponseModelAssembler() {
		super(EbookRestApi.class, EbookModel.class);
	}

	@Override
	public EbookModel toModel(Ebook entity) {
		EbookModel ebookModel = instantiateModel(entity);

		ebookModel.add(linkTo(methodOn(EbookRestApi.class)
				.getEbook(null, entity.getUuid())).withSelfRel());
		if (!entity.isExpired()) {
			ebookModel.add(linkTo(methodOn(EbookRestApi.class)
					.downloadEbook(null, entity.getUuid())).withRel("download"));
		}

		ebookModel.setUuid(entity.getUuid());
		ebookModel.setName(entity.getName());
		ebookModel.setType(entity.getType().getValue());
		ebookModel.setExtension(entity.getType().getExtension());
		ebookModel.setDownloadCount(entity.getDownloadCount());
		ebookModel.setCreatedAt(entity.getCreatedAt());
		ebookModel.setExpiredAt(entity.getExpiredAt());
		ebookModel.setOwner(ObjectUtils.isEmpty(entity.getUser()) ? null : entity.getUser().getUsername());

		return ebookModel;
	}

	@Override
	public CollectionModel<EbookModel> toCollectionModel(Iterable<? extends Ebook> entities) {
		CollectionModel<EbookModel> ebookFileModels = super.toCollectionModel(entities);

		return ebookFileModels;
	}
}
