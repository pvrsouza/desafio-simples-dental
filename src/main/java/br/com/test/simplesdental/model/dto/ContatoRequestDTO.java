package br.com.test.simplesdental.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContatoRequestDTO {
    @NotEmpty(message = "O nome do contato é obrigatório.")
    @Schema(description = "Campo que indica o nome do contato.", example = "Telefone", required = true)
    @Size(min = 2, max = 100, message = "É necessário informar no mínimo 2 e no máximo 100 caracteres pra o nome")
    private String nome;

    @NotEmpty(message = "O campo do contato é obrigatório.")
    @Schema(description = "Campo valor do contato.", example = "(71) 12346546", required = true)
    @Size(min = 2, max = 50, message = "É necessário informar no mínimo 2 e no máximo 50 caracteres pra o contato")
    private String contato;

    @Schema(description = "Campo id do profissional.", example = "1", required = true)
    @NotNull(message = "O Id do contato é obrigatório.")
    private Integer idProfissional;
}
