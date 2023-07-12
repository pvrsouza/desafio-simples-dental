package br.com.test.simplesdental.controllers;


import br.com.test.simplesdental.exceptions.ApiException;
import br.com.test.simplesdental.model.dto.ErrorResponse;
import br.com.test.simplesdental.model.dto.ProfissionalRequestDTO;
import br.com.test.simplesdental.model.entity.ProfessionalEntity;
import br.com.test.simplesdental.service.FilterFieldsService;
import br.com.test.simplesdental.service.ProfissionalService;
import br.com.test.simplesdental.swagger.ExamplesDocs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/profissionais")
@RequiredArgsConstructor
public class ProfissionalController {

    private final ProfissionalService profissionalService;
    private final FilterFieldsService filterFieldsService;

    @Operation(summary = "Lista de profissionais com base nos critérios definidos nos filtros")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Encontrou os registros",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProfessionalEntity.class)))})
    })
    public ResponseEntity<MappingJacksonValue> listarProfissionais(
            @RequestParam(value = "filter")
            @Schema(example = ExamplesDocs.PROFISSIONAL_FILTER, description = ExamplesDocs.PROFISSIONAL_FILTER_DESC)
            final String filter,

            @RequestParam(value = "fields", required = false)
            @Schema(example = ExamplesDocs.PROFISSIONAL_FIELDS, description = ExamplesDocs.PROFISSIONAL_FIELDS_DESC)
            List<String> fields
    ) {
        List<ProfessionalEntity> professionalEntities = this.profissionalService.listarProfissionais(filter);

        MappingJacksonValue objetoComOsCamposFiltrados = this.filterFieldsService.getObjetoComOsCamposFiltrados(fields, professionalEntities, ProfessionalEntity.class);

        return new ResponseEntity<>(
                objetoComOsCamposFiltrados, HttpStatus.OK);
    }


    @Operation(summary = "Retorna todos os dados do profissional que possui o ID passado na URL")
    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Encontrou o registro",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProfessionalEntity.class)))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<ProfessionalEntity> listarProfissionais(
            @RequestParam(value = "id", required = false)
            @Schema(example = ExamplesDocs.PROFISSIONAL_ID, description = ExamplesDocs.PROFISSIONAL_ID_DESC)
            String id
    ) throws ApiException {
        return new ResponseEntity<>(this.profissionalService.getById(id), HttpStatus.OK);
    }


    @Operation(summary = "Cria o registro do profissional")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Criou o profissional",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProfessionalEntity.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erros de validação nos dados de entrada do profissional",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})

    })
    public ResponseEntity<ProfessionalEntity> create(@Valid @RequestBody ProfissionalRequestDTO profissional
    ) throws Exception {
        ProfessionalEntity professional = this.profissionalService.create(profissional);
        return new ResponseEntity<>(professional, HttpStatus.CREATED);
    }

    @Operation(summary = "Ataualiza os dados do profissional que possui o ID passado na URL")
    @PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Atualizou o profissional",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProfessionalEntity.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erros de validação nos dados de entrada do profissional",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})

    })
    public ResponseEntity<ProfessionalEntity> create(@Valid @RequestBody ProfissionalRequestDTO profissional,
                                                     @RequestParam(value = "id", required = false)
                                                     @Schema(example = ExamplesDocs.PROFISSIONAL_ID, description = ExamplesDocs.PROFISSIONAL_ID_DESC)
                                                     String id
    ) throws Exception {
        ProfessionalEntity professional = this.profissionalService.update(profissional,id);
        return new ResponseEntity<>(professional, HttpStatus.OK);
    }


    @Operation(summary = "Ataualiza os dados do profissional que possui o ID passado na URL")
    @DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exclusão lógica do profissional",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProfessionalEntity.class))})

    })
    public ResponseEntity<ProfessionalEntity> delete(@RequestParam(value = "id", required = false)
                                                     @Schema(example = ExamplesDocs.PROFISSIONAL_ID, description = ExamplesDocs.PROFISSIONAL_ID_DESC)
                                                     String id
    ) throws Exception {
        ProfessionalEntity professional = this.profissionalService.delete(id);
        return new ResponseEntity<>(professional, HttpStatus.OK);
    }

}
