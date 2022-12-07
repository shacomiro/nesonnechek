package com.shacomiro.makeabook.api.ebook.dto;

import static org.springframework.beans.BeanUtils.*;

import java.time.LocalDateTime;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.hateoas.RepresentationModel;

import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
// @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EbookModel extends RepresentationModel<EbookModel> {
	private String uuid;
	private String name;
	private String type;
	private String extension;
	private int downloadCount;
	private LocalDateTime createdAt;
	private LocalDateTime expiredAt;
	private String owner;

	public EbookModel(Ebook source) {
		copyProperties(source, this);

		this.owner = ObjectUtils.isEmpty(source.getUser()) ? "guest" : source.getUser().getUsername();
	}
}
