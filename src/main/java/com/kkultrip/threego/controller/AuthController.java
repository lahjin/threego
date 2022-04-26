package com.kkultrip.threego.controller;

import com.kkultrip.threego.config.security.auth.UserDetailsImpl;
import com.kkultrip.threego.config.security.jwt.JwtUtils;
import com.kkultrip.threego.dto.JwtDto;
import com.kkultrip.threego.dto.LoginDto;
import com.kkultrip.threego.dto.MessageDto;
import com.kkultrip.threego.dto.SignUpDto;
import com.kkultrip.threego.model.ERole;
import com.kkultrip.threego.model.Role;
import com.kkultrip.threego.model.User;
import com.kkultrip.threego.repository.RoleRepository;
import com.kkultrip.threego.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder,
            JwtUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.createJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(JwtDto.builder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .nickname(userDetails.getNickname())
                .roles(roles)
                .build());
    }

    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return ResponseEntity.badRequest().body(new MessageDto("Error: username is already token"));
        }

        if(userRepository.existsByNickname(signUpDto.getNickname())){
            return ResponseEntity.badRequest().body(new MessageDto("Error: nickname is already token"));
        }

        User user = User.builder()
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .nickname(signUpDto.getNickname())
                .build();

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageDto("User register Success"));
    }
}
