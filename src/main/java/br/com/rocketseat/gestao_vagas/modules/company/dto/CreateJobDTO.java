package br.com.rocketseat.gestao_vagas.modules.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateJobDTO {
    private String description;
    private String benefits;
    @NotBlank ( message = "Esse campo é obrigatório" )
    private String level;
}