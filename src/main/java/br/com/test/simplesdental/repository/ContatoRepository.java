package br.com.test.simplesdental.repository;

import br.com.test.simplesdental.model.entity.ContatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoRepository extends JpaRepository<ContatoEntity, Integer> {
    @Query(value = "select * from contatos c " +
            " where c.nome ILIKE %:filter% " +
            " OR c.contato ILIKE  %:filter% " +
            " OR CAST(c.created_date AS VARCHAR) ILIKE  %:filter% " +
            " or CAST(c.id_profissional AS VARCHAR) ILIKE  %:filter% "
            , nativeQuery = true)
    List<ContatoEntity> findAllByFilter(@Param("filter") String filter);
    
}
