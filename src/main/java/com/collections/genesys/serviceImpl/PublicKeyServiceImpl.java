package com.collections.genesys.serviceImpl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.collections.genesys.dto.PublicKeyResponseDto;
import com.collections.genesys.repository.ChannelRepository;
import com.collections.genesys.response.Key;
import com.collections.genesys.service.PublicKeyService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PublicKeyServiceImpl implements PublicKeyService {

	private static final Logger logger = LoggerFactory.getLogger(PublicKeyServiceImpl.class);

	private static final String PUBLIC_KEY_PATH = "src/main/resources/public.pem";

	private final ChannelRepository channelRepo;

	private final JwtService jwtService;

	RestTemplate restTemplate = new RestTemplate();

	public PublicKeyServiceImpl(ChannelRepository channelRepo, JwtService jwtService) {
		this.channelRepo = channelRepo;
		this.jwtService = jwtService;

	}

	@Override
	@CachePut(value = "publicKeyData", key = "#id", unless = "#result == null")
	public PublicKeyResponseDto fetchJWKSet(String id) {
		logger.debug("Starting to fetch JWK set for id: {}", id);
		PublicKeyResponseDto publicKeyResponse = new PublicKeyResponseDto();
		Key key = new Key();
		ArrayList<Key> arrayList = new ArrayList<>();
		ArrayList<String> arrayList2 = new ArrayList<>();
		try {
			final String publicKeyEndpoint = channelRepo.findRHSSOURLFromSettings();
			logger.info("Fetching public keys from RHSSO URL: {}", publicKeyEndpoint);

			/*
			 * uncomment this for SIT
			 * 
			 * PublicKeyResponseDto jwkSet = restTemplate.getForObject(publicKeyEndpoint,
			 * 
			 * PublicKeyResponseDto.class);
			 * 
			 * 
			 * System.out.println("jwkSet" + jwkSet);
			 * 
			 * if (jwkSet != null && jwkSet.getKeys() != null) { for (Key keys :
			 * jwkSet.getKeys()) { logger.debug("Processing key with kid: {}",
			 * keys.getKid()); // Add key to the list //keyList.add(key);
			 * arrayList.add(keys); }
			 * 
			 * publicKeyResponse.setKeys(arrayList);
			 * logger.debug("Public key response successfully constructed: {}",
			 * publicKeyResponse); } else {
			 * logger.warn("No keys were returned from RHSSO."); }
			 * 
			 * 
			 * END
			 */

			//Comment this for SIT deployment
			RSAPublicKey publicKey = (RSAPublicKey) loadPublicKey();

			// Extract Modulus (n) and Exponent (e)
			String modulusBase64Url = Base64.getUrlEncoder().withoutPadding()
					.encodeToString(publicKey.getModulus().toByteArray());
			String exponentBase64Url = Base64.getUrlEncoder().withoutPadding()
					.encodeToString(publicKey.getPublicExponent().toByteArray());

			// Mock response start

			// Create mock keys
			Key key1 = new Key();
			key1.setKid("53SnUBrHgVKRtKBEescOoyGutJ66JPmfLPH_ec7C0Cc");
			key1.setKty("RSA");
			key1.setAlg("RS256");
			key1.setUse("sig");
			key1.setN(modulusBase64Url);
			key1.setE(exponentBase64Url);

			Key key2 = new Key();
			key2.setKid("53SnUBrHgVKRtKBEescOoyGutJ66JPmfLPH_ec7C0Cc");
			key2.setKty("RSA");
			key2.setAlg("RS256");
			key2.setUse("sig");
			key2.setN(modulusBase64Url);
			key2.setE(exponentBase64Url);

			// Add keys to a list
			List<Key> keyList = new ArrayList<>();
			keyList.add(key1);
			keyList.add(key2);

			// Set mock keys in PublicKeyResponseDto

			publicKeyResponse.setKeys(keyList);
			
			//Comment end

			logger.debug("Public key response successfully constructed: {}", publicKeyResponse);
			System.out.println("publicKeyResponse" + publicKeyResponse);

			/*
			 * key.setKid("53SnUBrHgVKRtKBEescOoyGutJ66JPmfLPH_ec7C0Cc"); key.setKty("RSA");
			 * key.setAlg("RS256"); key.setUse("sig"); key.setN(
			 * "oehu-7XCkjR5h9Ng8sfaHEdNSkVMGr3WAK7PjyK__y1fY90cdHCI6DYZIL9HQz6Qnor5B3eDHgL7iX--k3qsyyCxRWcN1tzouIH0Qyh7I9PQvmYNjAE5Cfk8QN52ATrMo2XuVNwVqdg0fvgKDNdg-jH0VOxQYBSJbMcgMDwJXNCsQ6qMPmGUYR4dFJKkkPlwnNUjKvxQv_BuWjq5E5ysmjq5YWZAnuH8qgPPyo4FHq5U7GUa7zXsUL5Mzj2Iv9bgjLNSedVeHoI9l06xqZLAyfmXFk5ynZralz-E2V0cAHx2npzYyboPJ76a0qna7PrcK9HaPDsWsyt24s3_foSZqw"
			 * ); key.setE("AQAB"); key.setX5t("oifHV6L8CKX_I5zGVwJHrm-y8is");
			 * key.setX5("7bTbjZX6XlG4eD1C9uP6ZrilrINtZK7VcYIzl8-cayg");
			 * 
			 * arrayList2.add(
			 * "MIIClzCCAX8CBgFneiHExTANBgkqhkiG9w0BAQsFADAPMQ0wCwYDVQQDDARlbmJkMB4XDTE4MTIwNDE2NDk1MloXDTI4MTIwNDE2NTEzMlowDzENMAsGA1UEAwwEZW5iZDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKHobvu1wpI0eYfTYPLH2hxHTUpFTBq91gCuz48iv/8tX2PdHHRwiOg2GSC/R0M+kJ6K+Qd3gx4C+4l/vpN6rMsgsUVnDdbc6LiB9EMoeyPT0L5mDYwBOQn5PEDedgE6zKNl7lTcFanYNH74CgzXYPox9FTsUGAUiWzHIDA8CVzQrEOqjD5hlGEeHRSSpJD5cJzVIyr8UL/wblo6uROcrJo6uWFmQJ7h/KoDz8qOBR6uVOxlGu817FC+TM49iL/W4IyzUnnVXh6CPZdOsamSwMn5lxZOcp2a2pc/hNldHAB8dp6c2Mm6Dye+mtKp2uz63CvR2jw7FrMrduLN/36EmasCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAWHfuVLqPpVXDQzpJ1OT7QBtomtDCJ/+I7LQ1qF3OHiHC//q3N22FL8fB1SeBC/7Co4Sx2axIAJYVJdYlPSJDKlLqunR6rqLSmmKz+ilNlLayBrzvk/opl4wJrVB9dZydVGB6bt6V7R3EYKPypqPgxGc1HOdPcGSc7kKGQ6ZqqQJdNl8OLHkGdON2I9xOpc/RO2l/Nii4g7XPXhSihGic/gs7qByBoUmI3qp0GY08kDJJ+U1kikLcp4dMxRoweq/sMY+d0tL+sX7aOOvKqodtLNv+8YfzNNMs7y2BM7rOVmGuE4tfLtogKEJMm4GeUYGySy+ql8OmlS4qVKS/ULj5cw=="
			 * ); key.setX5c(arrayList2);
			 * 
			 * arrayList.add(key); publicKeyResponse.setKeys(arrayList);
			 */

			// mock end

			/* END */

		} catch (Exception e) {
			logger.error("Error occurred while fetching the JWK set", e);
		}

		logger.info("Returning public key response: {}", publicKeyResponse);
		return publicKeyResponse;
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
}
