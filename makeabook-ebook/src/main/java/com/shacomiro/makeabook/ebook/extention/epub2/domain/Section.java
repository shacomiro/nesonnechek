package com.shacomiro.makeabook.ebook.extention.epub2.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Section {
    private String title;
    private List<String> paragraphList;

    public Section() {}

    @Builder
    public Section(String title, List<String> paragraphList) {
        this.title = title;
        this.paragraphList = paragraphList;
    }

    public void updateParagraphList(List<String> paragraphList) {
        this.paragraphList = paragraphList;
    }
}
