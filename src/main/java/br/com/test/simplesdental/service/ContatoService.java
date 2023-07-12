package br.com.test.simplesdental.service;

import br.com.test.simplesdental.exceptions.ApiErros;
import br.com.test.simplesdental.exceptions.ApiException;
import br.com.test.simplesdental.exceptions.NotFoundException;
import br.com.test.simplesdental.model.dto.ContatoRequestDTO;
import br.com.test.simplesdental.model.entity.ContatoEntity;
import br.com.test.simplesdental.model.entity.ProfessionalEntity;
import br.com.test.simplesdental.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContatoService {

    private final ContatoRepository contatoRepository;
    private final ProfissionalService professionalService;

    public List<ContatoEntity> listarContatos(String filter) {
        return this.contatoRepository.findAllByFilter(filter);
    }

    public ContatoEntity getById(String id) throws ApiException {
        return this.contatoRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new NotFoundException(ApiErros.API0001));
    }

    public ContatoEntity create(ContatoRequestDTO contato) {

        ProfessionalEntity professionalEntity = professionalService.getById(contato.getIdProfissional().toString());

        ContatoEntity professional = ContatoEntity.builder()
                .nome(contato.getNome())
                .contato(contato.getContato())
                .professional(professionalEntity)
                .createdDate(LocalDateTime.now())
                .build();

        return this.contatoRepository.save(professional);
    }

    public ContatoEntity update(ContatoRequestDTO contato, String id) {
        ContatoEntity contatoDb = this.contatoRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new NotFoundException(ApiErros.API0001));

        ProfessionalEntity professionalEntity = professionalService.getById(contato.getIdProfissional().toString());

        contatoDb.setNome(contato.getNome());
        contatoDb.setContato(contato.getContato());
        contatoDb.setProfessional(professionalEntity);

        return this.contatoRepository.saveAndFlush(contatoDb);

    }

    public void delete(String id) {
        ContatoEntity professionalDb = this.contatoRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new NotFoundException(ApiErros.API0001));

        this.contatoRepository.delete(professionalDb);
    }
}
