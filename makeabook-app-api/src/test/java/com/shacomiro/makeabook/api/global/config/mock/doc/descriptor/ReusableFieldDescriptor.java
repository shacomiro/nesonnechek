package com.shacomiro.makeabook.api.global.config.mock.doc.descriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import java.util.List;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class ReusableFieldDescriptor {
	public static class CommonFieldDescriptor {
		public static final FieldDescriptor LINKS_FIELD_DESCR = subsectionWithPath("_links").description("HATEOAS link")
				.type(JsonFieldType.OBJECT);
		public static final FieldDescriptor PAGE_FIELD_DESCR = subsectionWithPath("page").description("PAGE link")
				.type(JsonFieldType.OBJECT);
	}

	public static class UserFieldDescriptor {
		public static final List<FieldDescriptor> USER_MODEL_RES_FIELD_DESCR_LIST = List.of(
				fieldWithPath("email").description("User email").type(JsonFieldType.STRING),
				fieldWithPath("username").description("User name").type(JsonFieldType.STRING),
				fieldWithPath("loginCount").description("User login count number").type(JsonFieldType.NUMBER),
				fieldWithPath("lastLoginAt").description("Last login date").type(JsonFieldType.STRING),
				fieldWithPath("createdAt").description("Account creation date").type(JsonFieldType.STRING),
				subsectionWithPath("_links").description("HATEOAS link").type(JsonFieldType.OBJECT)
		);

		public static final List<FieldDescriptor> USER_COL_MODEL_RES_FIELD_DESCR_LIST = List.of(
				fieldWithPath("_embedded.users.[].email").description("User email").type(JsonFieldType.STRING),
				fieldWithPath("_embedded.users.[].username").description("User name").type(JsonFieldType.STRING),
				fieldWithPath("_embedded.users.[].loginCount").description("User login count number").type(JsonFieldType.NUMBER),
				fieldWithPath("_embedded.users.[].lastLoginAt").description("Last login date").type(JsonFieldType.STRING),
				fieldWithPath("_embedded.users.[].createdAt").description("User creation date").type(JsonFieldType.STRING),
				subsectionWithPath("_embedded.users.[]._links").description("HATEOAS link").type(JsonFieldType.OBJECT)
		);
	}

	public static class EbookFieldDescriptor {
		public static final List<FieldDescriptor> EBOOK_MODEL_RES_FIELD_DESCR_LIST = List.of(
				fieldWithPath("uuid").description("Ebook UUID").type(JsonFieldType.STRING),
				fieldWithPath("name").description("Ebook title").type(JsonFieldType.STRING),
				fieldWithPath("type").description("Ebook file type").type(JsonFieldType.STRING),
				fieldWithPath("extension").description("Ebook file extension").type(JsonFieldType.STRING),
				fieldWithPath("downloadCount").description("Ebook download count").type(JsonFieldType.NUMBER),
				fieldWithPath("createdAt").description("Created date of Ebook file").type(JsonFieldType.STRING),
				fieldWithPath("expiredAt").description("Expire date of Ebook file").type(JsonFieldType.STRING),
				fieldWithPath("owner").description("Ebook owner").type(JsonFieldType.STRING),
				subsectionWithPath("_links").description("HATEOAS link").type(JsonFieldType.OBJECT)
		);

		public static final List<FieldDescriptor> EBOOK_COL_MODEL_RES_FIELD_DESCR_LIST = List.of(
				fieldWithPath("_embedded.ebooks.[].uuid").description("Ebook UUID").type(JsonFieldType.STRING),
				fieldWithPath("_embedded.ebooks.[].name").description("Ebook title").type(JsonFieldType.STRING),
				fieldWithPath("_embedded.ebooks.[].type").description("Ebook file type").type(JsonFieldType.STRING),
				fieldWithPath("_embedded.ebooks.[].extension").description("Ebook file extension").type(JsonFieldType.STRING),
				fieldWithPath("_embedded.ebooks.[].downloadCount").description("Ebook download count").type(JsonFieldType.NUMBER),
				fieldWithPath("_embedded.ebooks.[].createdAt").description("Created date of Ebook file").type(JsonFieldType.STRING),
				fieldWithPath("_embedded.ebooks.[].expiredAt").description("Expire date of Ebook file").type(JsonFieldType.STRING),
				fieldWithPath("_embedded.ebooks.[].owner").description("Ebook owner").type(JsonFieldType.STRING),
				subsectionWithPath("_embedded.ebooks.[]._links").description("HATEOAS link").type(JsonFieldType.OBJECT)
		);
	}
}
