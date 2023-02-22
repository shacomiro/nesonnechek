package com.shacomiro.jwt.provider;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.shacomiro.jwt.policy.ClaimName;
import com.shacomiro.jwt.policy.TokenPurpose;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.PrematureJwtException;

class JwtProviderTest {
	private static final String SECRET_STRING = "secret_string_secret_string_secret_string_secret_string_secret_string_secret_string";
	private static final String ISSUER = "issuer";
	private static final String SUBJECT = "user@email.com";
	private static final Date NOW = new Date();
	private static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 30;
	private static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 2;
	private final JwtProvider jwtProvider;

	public JwtProviderTest() {
		this.jwtProvider = new JwtProvider(SECRET_STRING);
	}

	@Test
	@Order(1)
	@DisplayName("JWT 발급 및 파싱")
	void issueAndParseJwt() {
		//given
		Map<String, Object> claimsMap = new LinkedHashMap<>();
		claimsMap.put(ClaimName.ID.getName(), UUID.randomUUID().toString());
		claimsMap.put(ClaimName.ISSUER.getName(), ISSUER);
		claimsMap.put(ClaimName.SUBJECT.getName(), SUBJECT);
		claimsMap.put(ClaimName.PURPOSE.getName(), TokenPurpose.ACCESS.getValue());
		claimsMap.put(ClaimName.ISSUED_AT.getName(), jwtProvider.getSecondsFromDate(NOW));
		claimsMap.put(ClaimName.EXPIRATION.getName(),
				jwtProvider.getSecondsFromDate(new Date(NOW.getTime() + ACCESS_TOKEN_VALID_TIME)));

		//when
		Claims claims = jwtProvider.createClaims(claimsMap);
		String jwt = jwtProvider.buildToken(jwtProvider.initializeJwtBuilder(claims));
		Claims parsedClaims = jwtProvider.parseClaims(jwt);

		//then
		Assertions.assertEquals(claims.toString(), parsedClaims.toString());
	}

	@Test
	@Order(2)
	@DisplayName("JWT 발급 후 exp 만료로 인한 파싱 실패")
	void issueJwtAndParsingFailByEXP() {
		//given
		Map<String, Object> claimsMap = new LinkedHashMap<>();
		claimsMap.put(ClaimName.ID.getName(), UUID.randomUUID().toString());
		claimsMap.put(ClaimName.ISSUER.getName(), ISSUER);
		claimsMap.put(ClaimName.SUBJECT.getName(), SUBJECT);
		claimsMap.put(ClaimName.PURPOSE.getName(), TokenPurpose.ACCESS.getValue());
		claimsMap.put(ClaimName.ISSUED_AT.getName(),
				jwtProvider.getSecondsFromDate(new Date(NOW.getTime() - ACCESS_TOKEN_VALID_TIME)));
		claimsMap.put(ClaimName.EXPIRATION.getName(), jwtProvider.getSecondsFromDate(NOW));

		//when
		Claims claims = jwtProvider.createClaims(claimsMap);
		String jwt = jwtProvider.buildToken(jwtProvider.initializeJwtBuilder(claims));

		//then
		Assertions.assertThrows(ExpiredJwtException.class, () -> jwtProvider.parseClaims(jwt));
	}

	@Test
	@Order(3)
	@DisplayName("JWT 발급 후 nbf 제한으로 인한 파싱 실패")
	void issueJwtAndParsingFailByNBFLimit() {
		//given
		Map<String, Object> claimsMap = new LinkedHashMap<>();
		claimsMap.put(ClaimName.ID.getName(), UUID.randomUUID().toString());
		claimsMap.put(ClaimName.ISSUER.getName(), ISSUER);
		claimsMap.put(ClaimName.SUBJECT.getName(), SUBJECT);
		claimsMap.put(ClaimName.PURPOSE.getName(), TokenPurpose.REFRESH.getValue());
		claimsMap.put(ClaimName.ISSUED_AT.getName(), jwtProvider.getSecondsFromDate(NOW));
		claimsMap.put(ClaimName.EXPIRATION.getName(),
				jwtProvider.getSecondsFromDate(new Date(NOW.getTime() + REFRESH_TOKEN_VALID_TIME)));
		claimsMap.put(ClaimName.NOT_BEFORE.getName(),
				jwtProvider.getSecondsFromDate(new Date(NOW.getTime() + ACCESS_TOKEN_VALID_TIME)));

		//when
		Claims claims = jwtProvider.createClaims(claimsMap);
		String jwt = jwtProvider.buildToken(jwtProvider.initializeJwtBuilder(claims));

		//then
		Assertions.assertThrows(PrematureJwtException.class, () -> jwtProvider.parseClaims(jwt));
	}

	@Test
	@Order(4)
	@DisplayName("JWT 발급 후 iss 불일치로 인한 파싱 실패")
	void issueJwtAndParsingFailByWrongISS() {
		//given
		Map<String, Object> claimsMap = new LinkedHashMap<>();
		claimsMap.put(ClaimName.ID.getName(), UUID.randomUUID().toString());
		claimsMap.put(ClaimName.ISSUER.getName(), "wrong_issuer");
		claimsMap.put(ClaimName.SUBJECT.getName(), SUBJECT);
		claimsMap.put(ClaimName.PURPOSE.getName(), TokenPurpose.ACCESS.getValue());
		claimsMap.put(ClaimName.ISSUED_AT.getName(), jwtProvider.getSecondsFromDate(NOW));
		claimsMap.put(ClaimName.EXPIRATION.getName(),
				jwtProvider.getSecondsFromDate(new Date(NOW.getTime() + ACCESS_TOKEN_VALID_TIME)));
		Map<String, Object> requires = new HashMap<>();
		requires.put(ClaimName.ISSUER.getName(), ISSUER);

		//when
		Claims claims = jwtProvider.createClaims(claimsMap);
		String jwt = jwtProvider.buildToken(jwtProvider.initializeJwtBuilder(claims));

		//then
		Assertions.assertThrows(IncorrectClaimException.class, () -> jwtProvider.parseClaims(jwt, requires));
	}

	@Test
	@Order(5)
	@DisplayName("JWT 발급 후 특정 Claim 누락으로 인한 파싱 실패")
	void issueJwtAndParsingFailByMissingClaim() {
		//given
		Map<String, Object> claimsMap = new LinkedHashMap<>();
		claimsMap.put(ClaimName.ID.getName(), UUID.randomUUID().toString());
		claimsMap.put(ClaimName.SUBJECT.getName(), SUBJECT);
		claimsMap.put(ClaimName.PURPOSE.getName(), TokenPurpose.ACCESS.getValue());
		claimsMap.put(ClaimName.ISSUED_AT.getName(), jwtProvider.getSecondsFromDate(NOW));
		claimsMap.put(ClaimName.EXPIRATION.getName(),
				jwtProvider.getSecondsFromDate(new Date(NOW.getTime() + ACCESS_TOKEN_VALID_TIME)));
		Map<String, Object> requires = new HashMap<>();
		requires.put(ClaimName.ISSUER.getName(), ISSUER);

		//when
		Claims claims = jwtProvider.createClaims(claimsMap);
		String jwt = jwtProvider.buildToken(jwtProvider.initializeJwtBuilder(claims));

		//then
		Assertions.assertThrows(MissingClaimException.class, () -> jwtProvider.parseClaims(jwt, requires));
	}
}
