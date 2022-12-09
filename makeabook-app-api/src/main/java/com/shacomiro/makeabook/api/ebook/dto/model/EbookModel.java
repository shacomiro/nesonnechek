package com.shacomiro.makeabook.api.ebook.dto.model;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "ebook")
@Relation(collectionRelation = "ebooks")
@JsonInclude(Include.NON_NULL)
public class EbookModel extends RepresentationModel<EbookModel> {
	private String uuid;
	private String name;
	private String type;
	private String extension;
	private int downloadCount;
	private LocalDateTime createdAt;
	private LocalDateTime expiredAt;
	private String owner;
}
