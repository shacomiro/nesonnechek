package com.shacomiro.makeabook.api.global.security.deserializer;

import java.io.IOException;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class SimpleGrantedAuthorityDeserializer extends StdDeserializer<SimpleGrantedAuthority> {

	public SimpleGrantedAuthorityDeserializer() {
		super(SimpleGrantedAuthority.class);
	}

	@Override
	public SimpleGrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws
			IOException, JacksonException {
		JsonNode tree = p.getCodec().readTree(p);

		return new SimpleGrantedAuthority(tree.get("authority").textValue());
	}
}
