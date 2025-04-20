package io.github.LorranFernandes.libraryapi.security;

import io.github.LorranFernandes.libraryapi.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    public Usuario obterUsuarioLogado(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth instanceof CustomAuthentication customAuth){
            return customAuth.getUsuario();
        }

        return null;
    }
}
