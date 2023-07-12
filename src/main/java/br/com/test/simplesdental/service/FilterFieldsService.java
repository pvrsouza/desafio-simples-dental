package br.com.test.simplesdental.service;

import br.com.test.simplesdental.json.FieldFilterMixin;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilterFieldsService {

    private final ObjectMapper objectMapper;

    public MappingJacksonValue getObjetoComOsCamposFiltrados(List<String> fields, List objects, Class clazz) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(objects);

        if (fields != null && !fields.isEmpty()) {
            Set<String> setFields = new HashSet<>(fields);

            SimpleFilterProvider filterProvider = new SimpleFilterProvider()
                    .addFilter("fieldFilter", SimpleBeanPropertyFilter.filterOutAllExcept(setFields));

            objectMapper.setFilterProvider(filterProvider);
            mappingJacksonValue.setFilters(filterProvider);
        } else {
            objectMapper.addMixIn(clazz, FieldFilterMixin.class);
        }
        return mappingJacksonValue;
    }


}
