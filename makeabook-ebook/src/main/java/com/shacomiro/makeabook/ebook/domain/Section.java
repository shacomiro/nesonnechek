package com.shacomiro.makeabook.ebook.domain;

import java.util.ArrayList;
import java.util.List;

public class Section {
	private String title;
	private List<String> paragraphList;

	public Section() {
		this.title = "";
		this.paragraphList = new ArrayList<>();
	}

	public Section(String title) {
		this.title = title;
		this.paragraphList = new ArrayList<>();
	}

	public Section(String title, List<String> paragraphList) {
		this.title = title;
		this.paragraphList = paragraphList;
	}

	public void updateParagraphList(List<String> paragraphList) {
		this.paragraphList = paragraphList;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getParagraphList() {
		return paragraphList;
	}

	@Override
	public String toString() {
		return "Section{" +
				"title='" + title + '\'' +
				", paragraphList=" + paragraphList +
				'}';
	}
}
