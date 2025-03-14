// package com.umss.be_gestor.service;

// import java.util.ArrayList;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.umss.be_gestor.model.Usuario;
// import com.umss.be_gestor.repository.IUsuarioRepository;

// @Service
// public class UserDetailsServiceImpl implements UserDetailsService {

//     private final IUsuarioRepository usuarioRepository;

//     public UserDetailsServiceImpl(IUsuarioRepository usuarioRepository) {
//         this.usuarioRepository = usuarioRepository;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         Usuario usuario = usuarioRepository.findByUsername(username)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//         return new org.springframework.security.core.userdetails.User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
//     }
// }
