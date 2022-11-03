package com.shacomiro.makeabook.api.ebook.dto;

import static org.springframework.beans.BeanUtils.*;

import java.time.LocalDateTime;

import org.apache.commons.lang3.ObjectUtils;

import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class EbookResultResponse {
	private String ebookId;
	private String ebookName;
	private String ebookType;
	private String downloadUrl;
	private LocalDateTime createdAt;
	private LocalDateTime expiredAt;
	private String owner;

	public EbookResultResponse(EbookFile source) {
		copyProperties(source, this);

		this.ebookId = source.getUuid();
		this.ebookName = source.getFilename();
		this.ebookType = source.getFileType();
		this.owner = ObjectUtils.isEmpty(source.getUser()) ? "guest" : source.getUser().getUsername();
	}
}
