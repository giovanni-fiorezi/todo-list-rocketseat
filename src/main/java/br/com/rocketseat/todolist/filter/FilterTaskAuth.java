package br.com.rocketseat.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rocketseat.todolist.user.IUserRepository;
import br.com.rocketseat.todolist.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String servletPath = request.getServletPath();
        if(servletPath.startsWith("/tasks/")) {

            //Pegar a autenticação (usuário e senha)
            String authorization = request.getHeader("Authorization");
            String authEncoded = authorization.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            String authString = new String(authDecode);
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // Validar usuário
            UserModel byUsername = this.userRepository.findByUsername(username);

            if (byUsername == null) {
                response.sendError(401);
            } else {
                // Validar senha
                BCrypt.Result passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), byUsername.getPassword());
                if (passwordVerify.verified) {
                    request.setAttribute("idUser", byUsername.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
