package com.truemeds.truemeds.security.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truemeds.truemeds.Repository.RefreshTokenRepository;
import com.truemeds.truemeds.Repository.UserRepository;
import com.truemeds.truemeds.exceptions.TokenRefreshException;
import com.truemeds.truemeds.models.RefreshToken;
import com.truemeds.truemeds.security.jwt.JwtUtils;

@Service
public class TokenRefreshService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(TokenRefreshService.class);
	
	@Value("${truemeds.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;
	
	@Autowired
	RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	UserRepository userRepository; 
	
	
	public Optional<RefreshToken> findByToken(String token){
		
		logger.info("------------------------Request Recived in TokenRefreshService||findByToken-------------------------------");
		
		logger.info("Method findByToken"+refreshTokenRepository.findByToken(token));
		return refreshTokenRepository.findByToken(token);
		}
	
	
	
	public RefreshToken createRefreshToken(Long userId) {
		
		RefreshToken refreshToken =new RefreshToken();
		
		refreshToken.setUsers(userRepository.findById(userId).get());
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());
		
		refreshToken = refreshTokenRepository.save(refreshToken);
		
		return refreshToken;
	}
	
	
	
	public RefreshToken verifyRefreshToken(RefreshToken token) {
		
		if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");

		}
		return token;

	}
	
	 @Transactional
	  public int deleteByUserId(Long userId) {
	    return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
	  }
	

}
