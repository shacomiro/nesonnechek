package com.shacomiro.makeabook.api.ebook.dto;

import static org.springframework.beans.BeanUtils.*;

import java.time.LocalDateTime;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.hateoas.RepresentationModel;

import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
// @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EbookFileModel extends RepresentationModel<EbookFileModel> {
	private String ebookId;
	private String ebookName;
	private String ebookType;
	private String downloadUrl;
	private LocalDateTime createdAt;
	private LocalDateTime expiredAt;
	private String owner;

	public EbookFileModel(EbookFile source) {
		copyProperties(source, this);

		this.ebookId = source.getUuid();
		this.ebookName = source.getFilename();
		this.ebookType = source.getFileType();
		this.owner = ObjectUtils.isEmpty(source.getUser()) ? "guest" : source.getUser().getUsername();
	}
}
