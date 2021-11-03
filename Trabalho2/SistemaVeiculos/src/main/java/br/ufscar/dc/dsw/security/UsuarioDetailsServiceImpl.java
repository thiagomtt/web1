package br.ufscar.dc.dsw.security;

import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {
        Usuario user = usuarioDAO.findByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new UsuarioDetails(user);
    }
}
