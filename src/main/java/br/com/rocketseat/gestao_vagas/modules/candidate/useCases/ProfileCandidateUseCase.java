package br.com.rocketseat.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.gestao_vagas.exceptions.UserNotFoundException;
import br.com.rocketseat.gestao_vagas.modules.candidate.repository.CandidateRepository;
import br.com.rocketseat.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute ( UUID idCandidate ) {
        var candidate = this.candidateRepository.findById ( idCandidate )
                .orElseThrow ( UserNotFoundException::new );
        return ProfileCandidateResponseDTO.builder ( )
                .description ( candidate.getDescription ( ) )
                .username ( candidate.getUsername ( ) )
                .email ( candidate.getEmail ( ) )
                .name ( candidate.getName ( ) )
                .id ( candidate.getId ( ) )
                .build ( );
    }
}