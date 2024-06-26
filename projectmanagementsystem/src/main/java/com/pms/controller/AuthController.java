//package com.pms.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.authentication.AuthenticationConverterServerWebExchangeMatcher;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.pms.config.JwtProvider;
//import com.pms.model.User;
//import com.pms.repository.UserRepository;
//import com.pms.request.LoginRequest;
//import com.pms.response.AuthResponse;
//import com.pms.service.CustomUserDetailsImpl;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//	
//	@Autowired
//	private UserRepository userRepository;
//	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	
//	//customer
//	@Autowired
//	private CustomUserDetailsImpl customerUserDetails;
//	
//	//first method is for create user -> for signup of the user
//	@PostMapping("/signup")
//	public ResponseEntity<User> createUserHandler(@RequestBody User user) throws Exception{
//		
//		User isUserExist = userRepository.findByEmail(user.getEmail());
//		
//		if(isUserExist!=null) {
//			throw new Exception("Email already exist with another account");
//		}
//		User createdUser = new User();
//		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
//		createdUser.setEmail(user.getEmail());
//		createdUser.setFirstName(user.getFirstName());
//		createdUser.setLastName(user.getLastName());
//		
//		User savedUser = userRepository.save(createdUser);
//		
//		//after creating user we need to create the authentication
//		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
//		
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		//now we generate the token
//		String jwt = JwtProvider.generateToken(authentication);
//		
//		AuthResponse res = new AuthResponse();
//		res.setJwt(jwt);
//		res.setMessage("Signup success");
//		
//		return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
//		
//	}
//
//	//for login of the user //we create login request in request package
//	@PostMapping("/signin")
//    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = JwtProvider.generateToken(authentication);
//        AuthResponse res = new AuthResponse(jwt, "Signin success");
//
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }
//
//	
//	private Authentication authenticate(String username, String password) {
//		
//		UserDetails userDetails = customerUserDetails.loadUserByUsername(username);
//		if(userDetails==null) {
//			throw new BadCredentialsException("Invalid UserName");
//		}
//		//match password with hashpassword
//		if(!passwordEncoder.matches(password,userDetails.getPassword())) {
//			throw new BadCredentialsException("Invalid password");
//		}
//		
//		return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
//		
//	}
//	    @ExceptionHandler(IllegalArgumentException.class)
//	    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
//	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//	    }
//
//	    @ExceptionHandler(BadCredentialsException.class)
//	    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
//	        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
//	    }
//}
package com.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pms.config.JwtProvider;
import com.pms.model.User;
import com.pms.repository.UserRepository;
import com.pms.request.LoginRequest;
import com.pms.response.AuthResponse;
import com.pms.service.CustomUserDetailsImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsImpl customUserDetails;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists with another account");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(jwt, "Signup success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(jwt, "Signin success");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    //SecretKeyFor throws IllegalArgumentException in the SecretKey
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}

