package br.com.test.simplesdental.service;

import br.com.test.simplesdental.exceptions.ApiErros;
import br.com.test.simplesdental.exceptions.ApiException;
import br.com.test.simplesdental.exceptions.NotFoundException;
import br.com.test.simplesdental.model.dto.ProfissionalRequestDTO;
import br.com.test.simplesdental.model.entity.ProfessionalEntity;
import br.com.test.simplesdental.repository.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfessionalRepository professionalRepository;

    public List<ProfessionalEntity> listarProfissionais(String filter) {
        return this.professionalRepository.findProfissionaisAtivoByFilter(filter);
    }

    public ProfessionalEntity getById(String id) throws ApiException {
        return this.professionalRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new NotFoundException(ApiErros.API0001));
    }

    public ProfessionalEntity create(ProfissionalRequestDTO profissional) {
        ProfessionalEntity professional = ProfessionalEntity.builder()
                .nascimento(profissional.getNascimento())
                .cargo(profissional.getCargo())
                .nome(profissional.getNome())
                .createdDate(LocalDateTime.now())
                .build();
        return this.professionalRepository.save(professional);
    }

    public ProfessionalEntity update(ProfissionalRequestDTO profissional, String id) {
        ProfessionalEntity professionalDb = this.professionalRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new NotFoundException(ApiErros.API0001));

        professionalDb.setCargo(profissional.getCargo());
        professionalDb.setNome(profissional.getNome());
        professionalDb.setNascimento(profissional.getNascimento());

        return this.professionalRepository.saveAndFlush(professionalDb);

    }

    public ProfessionalEntity delete(String id) {
        ProfessionalEntity professionalDb = this.professionalRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new NotFoundException(ApiErros.API0001));

        professionalDb.setAtivo(Boolean.FALSE);

        return this.professionalRepository.saveAndFlush(professionalDb);
    }
}
