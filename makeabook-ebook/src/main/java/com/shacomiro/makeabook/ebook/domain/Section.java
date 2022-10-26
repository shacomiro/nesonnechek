package com.shacomiro.makeabook.ebook.domain;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Section {
	private String title;
	private List<String> paragraphList;

	public Section() {
	}

	@Builder
	public Section(String title, List<String> paragraphList) {
		this.title = title;
		this.paragraphList = paragraphList;
	}

	public void updateParagraphList(List<String> paragraphList) {
		this.paragraphList = paragraphList;
	}
}
