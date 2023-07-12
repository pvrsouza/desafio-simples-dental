package br.com.test.simplesdental.model.dto;

import br.com.test.simplesdental.enums.CargoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfissionalRequestDTO {

    @NotEmpty(message = "O nome do profissional é obrigatório.")
    @Schema(description = "Campo que indica o nome do profissional.", example = "João da Silva", required = true)
    @Size(min = 2, max = 100, message = "É necessário informar no mínimo 2 e no máximo 100 caracteres pra o nome")
    private String nome;

    @NotNull(message = "A data nascimento é obrigatória.")
    @Schema(description = "Campo que a data de nascimento do profissional.", example = "1980-02-02", required = true)
    @Past(message = "A data de nascimento tem que estar no passado.")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate nascimento;

    @Valid
    @NotNull(message = "O cargo é obrigatório.")
    private CargoEnum cargo;
}
