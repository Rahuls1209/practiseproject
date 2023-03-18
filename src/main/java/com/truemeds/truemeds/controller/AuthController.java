package com.truemeds.truemeds.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truemeds.truemeds.Repository.RefreshTokenRepository;
import com.truemeds.truemeds.Repository.RoleRepository;
import com.truemeds.truemeds.Repository.UserRepository;
import com.truemeds.truemeds.exceptions.TokenRefreshException;
import com.truemeds.truemeds.models.ERole;
import com.truemeds.truemeds.models.RefreshToken;
import com.truemeds.truemeds.models.Role;
import com.truemeds.truemeds.models.User;
import com.truemeds.truemeds.payload.Request.LoginRequest;
import com.truemeds.truemeds.payload.Request.LogoutRequest;
import com.truemeds.truemeds.payload.Request.RefreshTokenRequest;
import com.truemeds.truemeds.payload.Request.SignupRequest;
import com.truemeds.truemeds.payload.response.JwtResponse;
import com.truemeds.truemeds.payload.response.MessageResponse;
import com.truemeds.truemeds.payload.response.TokenRefreshResponse;
import com.truemeds.truemeds.security.jwt.JwtUtils;
import com.truemeds.truemeds.security.services.TokenRefreshService;
import com.truemeds.truemeds.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger log = LogManager.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	RefreshTokenRepository refreshTokenRepository;

	@Autowired
	PasswordEncoder encode;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	TokenRefreshService tokenRefreshService;

	@GetMapping("/all")
	public String getAll() {
		return "public Content";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userContent() {

		return "userContent";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {

		return "moderator Content";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignupRequest request) {

		log.info("-----------Sign up controller----------------------");

		// Username and Email Validation
		if (userRepository.existsByUsername(request.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error : Username Already Taken!"));
		}

		if (userRepository.existsByEmail(request.getEmail())) {

			return ResponseEntity.badRequest().body(new MessageResponse("Error : Email Already Exists!"));
		}

// New user Creation

		User user = new User(request.getUsername(), encode.encode(request.getPassword()), request.getEmail());

		Set<String> strRoles = request.getRole();
		Set<Role> roles = new HashSet();

		System.out.println("value of str roles" + strRoles);

		if (strRoles == null) {

			System.out.println("inside if condition");

			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role Not Found"));

			roles.add(userRole);

		} else {
			strRoles.forEach(role -> {

				switch (role) {

				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role Not Found"));
					roles.add(adminRole);
					break;

				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);

				}
			});

		}

		user.setRoles(roles);
		log.info("value of user before save" + user);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User Registred Successfully"));

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {

		log.info("userName" + request.getUserName() + "   " + "Passowrd" + request.getPassword());
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

		log.info("credential in login controller" + authentication.getCredentials());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		String jwt = jwtUtils.generateJwtToken(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		RefreshToken refreshToken = tokenRefreshService.createRefreshToken(userDetails.getId());

		return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
				userDetails.getUsername(), userDetails.getEmail(), roles));

	}

	@PostMapping("/refreshtoken") 
	public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request){
	  
		log.info("----------------------------------Request Recived in RefreshToken Controller---------------------------------------");
		    
		  	String refreshToken =request.getRefreshToken();
		  	log.info("-------------Value Of RefreshToken------------"+refreshToken);  	
		  	return tokenRefreshService.findByToken(refreshToken).
		  			map( tokenRefreshService :: verifyRefreshToken).
		  			map(RefreshToken :: getUsers).
		  			map(users -> {String token = jwtUtils.generateTokenFromUsername(users.getUserName());
		  			return ResponseEntity.ok(new TokenRefreshResponse(token, refreshToken));
		  				
		  			}).orElseThrow(() -> new TokenRefreshException(refreshToken,"Refresh token is not in database!"));
	  
	  }
	
	
	@PostMapping("/logout")
	  public ResponseEntity<?> logoutUser(@Valid @RequestBody LogoutRequest logOutRequest) {
		tokenRefreshService.deleteByUserId(logOutRequest.getUserId());
	    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
	  }
	

}
