package br.com.test.simplesdental.repository;

import br.com.test.simplesdental.model.entity.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Integer> {

    @Query(value = "select * from profissionais p " +
            " where p.ativo = true " +
            "AND (CAST(p.nascimento AS VARCHAR) ILIKE %:filter% " +
            "or nome ILIKE %:filter% " +
            "or cargo ILIKE %:filter% " +
            "or CAST(p.id_profissional AS VARCHAR) ILIKE %:filter%)"
            , nativeQuery = true)
    List<ProfessionalEntity> findProfissionaisAtivoByFilter(@Param("filter") String filter);
    
}
