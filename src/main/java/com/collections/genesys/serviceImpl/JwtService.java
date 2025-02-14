package com.collections.genesys.serviceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

	private static final String PRIVATE_KEY_PATH = "src/main/resources/private.pem";
	private static final String PUBLIC_KEY_PATH = "src/main/resources/public.pem";

	// Load RSA Private Key from private.pem
	private PrivateKey loadPrivateKey() throws Exception {

		logger.info("Inside loadprivateKey of Jwtservice");
		byte[] keyBytes = Files.readAllBytes(Paths.get(PRIVATE_KEY_PATH));

		String privateKeyPEM = new String(keyBytes).replace("-----BEGIN PRIVATE KEY-----", "")
				.replace("-----END PRIVATE KEY-----", "").replaceAll("\\s", ""); // Remove newlines and spaces

		byte[] decodedKey = Base64.getDecoder().decode(privateKeyPEM);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
		return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
	}

	private PublicKey loadPublicKey() throws Exception {

		logger.info("Inside loadPublicKey of Jwtservice");
		byte[] keyBytes = Files.readAllBytes(Paths.get(PUBLIC_KEY_PATH));

		String publicKeyPEM = new String(keyBytes).replace("-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----", "").replaceAll("\\s", "");

		byte[] decodedKey = Base64.getDecoder().decode(publicKeyPEM);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
		return KeyFactory.getInstance("RSA").generatePublic(keySpec);
	}

	

	public String generateToken(String username) {
		logger.info("Inside generateToken of JwtService");

		try {
			PrivateKey privateKey = loadPrivateKey(); // Load your private RSA key

			// Generate UUID for jti (JWT ID)
			String jti = UUID.randomUUID().toString();

			return Jwts.builder().setHeaderParam("kid", "53SnUBrHgVKRtKBEescOoyGutJ66JPmfLPH_ec7C0Cc") // Key ID in
																										// header
					.setHeaderParam("typ", "JWT") // Set type as JWT in header

					//.setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
					//.setExpiration(new Date(System.currentTimeMillis() + 60000)) // 1 minute expiration
					.setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))// 5 min

					.setIssuedAt(new Date()) // iat: Issued at time
					.setId(jti) // jti: JWT ID
					.setIssuer("https://rhsso-uat.clouduat.emiratesnbd.com/auth/realms/enbd") // iss: Issuer
					.setAudience("account") // aud: Audience
					// .setSubject("93e00d14-0b4b-4e04-ae33-394b9dda29f3") // sub: Subject
					.setSubject(username) // sub: Subject

					.claim("typ", "Bearer") // typ: Bearer (in payload)
					.claim("azp", "53a59f21") // azp: Authorized party

					// realm_access roles
					.claim("realm_access",
							Map.of("roles", List.of("default-roles-enbd", "offline_access", "uma_authorization")))

					// resource_access roles
					.claim("resource_access",
							Map.of("account",
									Map.of("roles", List.of("manage-account", "manage-account-links", "view-profile"))))

					.claim("scope", "") // scope: Empty
					.claim("clientId", "53a59f21") // clientId
					.claim("clientHost", "10.172.6.83") // clientHost
					.claim("preferred_username", "service-account-53a59f21") // preferred_username
					.claim("clientAddress", "10.172.6.83") // clientAddress

					.signWith(privateKey, SignatureAlgorithm.RS256) // Sign with private key
					.compact();
		} catch (Exception e) {
			throw new RuntimeException("Error generating JWT", e);
		}
	}

	// Verify token
	public boolean verifyToken(String token) {
		try {
			PublicKey publicKey = loadPublicKey();

			var claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).getBody();

			System.out.println("JWT Claims: " + claims);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
