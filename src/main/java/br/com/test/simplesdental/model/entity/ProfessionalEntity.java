package br.com.test.simplesdental.model.entity;

import br.com.test.simplesdental.enums.CargoEnum;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "profissionais")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonFilter("fieldFilter")
public class ProfessionalEntity {

    @Id
    @Column(name = "id_profissional", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_profissional;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "cargo", nullable = false, length = 13)
    private CargoEnum cargo;

    @Column(name = "nascimento", nullable = false)
    private LocalDate nascimento;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "id_profissional")
    private List<ContatoEntity> contatos;

}
