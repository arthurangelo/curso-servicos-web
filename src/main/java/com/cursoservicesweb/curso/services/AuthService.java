package com.cursoservicesweb.curso.services;

import com.cursoservicesweb.curso.Security.JWTUtil;
import com.cursoservicesweb.curso.dto.CredentialsDTO;
import com.cursoservicesweb.curso.dto.TokenDTO;
import com.cursoservicesweb.curso.entities.Order;
import com.cursoservicesweb.curso.entities.User;
import com.cursoservicesweb.curso.repositories.UserRepository;
import com.cursoservicesweb.curso.services.exceptions.JWTAuthenticationException;
import com.cursoservicesweb.curso.services.exceptions.JWTAuthorizationException;
import com.cursoservicesweb.curso.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;


@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);

    @Transactional(readOnly = true)
    public TokenDTO authenticate(CredentialsDTO credentialsDTO) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    credentialsDTO.getEmail(), credentialsDTO.getPassword());
            authenticationManager.authenticate(authToken);

            String token = jwtUtil.generateToken(credentialsDTO.getEmail());

            return new TokenDTO(credentialsDTO.getEmail(), token);
        } catch (AuthenticationException e) {
            throw new JWTAuthenticationException("Bad Credentials");
        }
    }

    public User authenticad() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            return userRepository.findByEmail(userDetails.getUsername());
        } catch (Exception e) {
            throw new JWTAuthorizationException("Access denied");
        }
    }

    public void validateselfOfAdmin(Long id) {
        User user = authenticad();

        if (user == null || (!user.getId().equals(id)) && !user.hasRole("ROLE_ADMIN")) {
            throw new JWTAuthorizationException("Access denied");
        }
    }

    public void validateOwrOrderAdmin(Order order) {
        User user = authenticad();

        if (user == null || (!user.getId().equals(order.getClient().getId())) && !user.hasRole("ROLE_ADMIN")) {
            throw new JWTAuthorizationException("Access denied");
        }
    }

    public TokenDTO refresToken() {
        User user = authenticad();
        return new TokenDTO(user.getEmail(), jwtUtil.generateToken(user.getEmail()));
    }

    @Transactional
    public void sendNewPassword(String email){
        User user = userRepository.findByEmail(email);
        if(user == null){
           throw  new ResourceNotFoundException("Email not found");
        }

        String newPass = newPassword();
        user.setPassword(encoder.encode(newPass));

        userRepository.save(user);

        LOG.info("New password: " + newPass);
    }

    private String newPassword() {
        char[] vect = new char[10];

        for (int i = 0; i < 10; i++) {
            vect[i] = randomChar();
        }

        return new String(vect);
    }

    private char randomChar() {
        Random rand = new Random();
        int opt = rand.nextInt(3);
        if (opt == 0) {
            //digit
            return (char) (rand.nextInt(10) + 48);
        } else if (opt == 1) {
            //uppercase
            return (char) (rand.nextInt(26) + 65);

        } else {
            //lowercase
            return (char) (rand.nextInt(26) + 97);
        }
    }

}
