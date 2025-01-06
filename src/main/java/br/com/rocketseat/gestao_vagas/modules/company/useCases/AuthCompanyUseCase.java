package br.com.rocketseat.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.rocketseat.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthCompanyUseCase {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value ( "${security.token.secret}" )
    private String secretKey;

    public AuthCompanyResponseDTO execute ( AuthCompanyDTO authCompanyDTO ) throws AuthenticationException {
        var company = this.companyRepository.findByUsername ( authCompanyDTO.getUsername ( ) ).orElseThrow ( ( ) -> new UsernameNotFoundException ( "Username/password incorrect" ) );
        var passwordMatches = this.passwordEncoder.matches ( authCompanyDTO.getPassword ( ) , company.getPassword ( ) );
        if ( ! passwordMatches ) {
            throw new AuthenticationException ( );
        }

        Algorithm algorithm = Algorithm.HMAC256 ( secretKey );
        var expiresIn = Instant.now ( ).plus ( Duration.ofMinutes ( 10 ) );

        var roles = List.of ( "COMPANY" );

        var token = JWT.create ( ).withIssuer ( "javagas" )
                .withExpiresAt ( expiresIn )
                .withSubject ( company.getId ( ).toString ( ) )
                .withClaim ( "roles" , roles )
                .sign ( algorithm );

        return AuthCompanyResponseDTO.builder ( )
                .access_token ( token )
                .expires_in ( expiresIn.toEpochMilli ( ) )
                .roles ( roles )
                .build ( );
    }
}