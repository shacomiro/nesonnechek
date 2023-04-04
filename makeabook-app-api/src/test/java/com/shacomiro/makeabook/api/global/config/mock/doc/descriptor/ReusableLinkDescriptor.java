package com.shacomiro.makeabook.api.global.config.mock.doc.descriptor;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;

import java.util.List;

import org.springframework.restdocs.hypermedia.LinkDescriptor;

public class ReusableLinkDescriptor {
	public static class CommonModelLinkDescriptor {
		public static final LinkDescriptor SELF_LINK_DESCR = linkWithRel("self").description("Link to self");
		public static final LinkDescriptor DOCS_LINK_DESCR = linkWithRel("docs").description("Link to API documentation");

		public static final List<LinkDescriptor> PAGING_LINKS_DESCR_LIST = List.of(
				linkWithRel("first").optional().description("The first page of results"),
				linkWithRel("last").optional().description("The last page of results"),
				linkWithRel("next").optional().description("The next page of results"),
				linkWithRel("prev").optional().description("The previous page of results")
		);
	}

	public static class UserModelLinkDescriptor {
		public static final List<LinkDescriptor> USER_MODEL_LINKS_DESCR_LIST = List.of(
				linkWithRel("sign-in").optional().description("Link to sign in")
		);
	}

	public static class EbookModelLinkDescriptor {
		public static final List<LinkDescriptor> EBOOK_MODEL_LINKS_DESCR_LIST = List.of(
				linkWithRel("download").optional().description("Link to download ebook file")
		);
	}
}
