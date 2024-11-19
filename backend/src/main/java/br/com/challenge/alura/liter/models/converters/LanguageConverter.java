package br.com.challenge.alura.liter.models.converters;

import br.com.challenge.alura.liter.models.entites.Language;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LanguageConverter {

    public Set<Language> convert(List<String> languages) {
        return languages.stream().map(Language::new).collect(Collectors.toSet());
    }

}
