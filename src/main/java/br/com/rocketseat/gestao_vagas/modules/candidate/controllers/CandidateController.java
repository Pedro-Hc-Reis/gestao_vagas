package br.com.rocketseat.gestao_vagas.modules.candidate.controllers;

import br.com.rocketseat.gestao_vagas.exceptions.UserFoundException;
import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ( "/candidate" )
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @PostMapping ( "/" )
    public ResponseEntity<Object> create ( @Valid @RequestBody CandidateEntity candidateEntity ) {

        this.candidateRepository.findByUsernameOrEmail ( candidateEntity.getUsername ( ) ,
                candidateEntity.getEmail ( ) ).ifPresent ( user -> {
            throw new UserFoundException ( );
        } );

        return new ResponseEntity<> ( this.candidateRepository.save ( candidateEntity ) , HttpStatus.OK );
    }
}
