package br.com.rocketseat.gestao_vagas.modules.company.dto;

import br.com.rocketseat.gestao_vagas.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@RunWith ( SpringRunner.class )
@SpringBootTest ( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class CreateJobControllerTest {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup ( ) {
        mvc = MockMvcBuilders.webAppContextSetup ( context )
                .apply ( SecurityMockMvcConfigurers.springSecurity ( ) )
                .build ( );
    }

    @Test
    public void should_be_able_to_create_a_new_job ( ) throws Exception {
        var createdJobDTO = CreateJobDTO.builder ( )
                .benefits ( "BENEFITS_TEST" )
                .description ( "DESCRIPTION_TEST" )
                .level ( "LEVEL_TEST" )
                .build ( );
        var result = mvc.perform ( MockMvcRequestBuilders.post ( "/company/job/" )
                        .contentType ( MediaType.APPLICATION_JSON )
                        .content ( TestUtils.objectToJson ( createdJobDTO ) )
                        .header ( "Authorization" , TestUtils.generateToken ( UUID.fromString ( "99c0cb3b-481f-4c35-8b3b-5791efde1d3c" ) , "JAVAGAS_@123#" ) )
                )
                .andExpect ( MockMvcResultMatchers.status ( ).isOk ( ) );
        System.out.println ( result );
    }
}