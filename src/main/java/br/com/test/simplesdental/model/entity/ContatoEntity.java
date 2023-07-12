package br.com.test.simplesdental.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contatos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contato")
    private Integer id;

    @Column(name="nome", nullable = false, length = 100)
    private String nome;
    
    @Column(name="contato", nullable = false, length = 50)
    private String contato;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name="id_profissional", nullable=false)
    private ProfessionalEntity professional;

    @Column(name="created_date", nullable = false)
    private LocalDateTime createdDate;

}
