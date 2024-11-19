package br.com.challenge.alura.liter.models.transfers;

import br.com.challenge.alura.liter.models.entites.Author;
import br.com.challenge.alura.liter.models.entites.Language;

import java.util.Set;

public record BookStatisticResponse(
        Long id,
        String title,
        Author author,
        String imageUrl,
        Set<Language>languages,
        Long downloadCount,
        Long averageDownloadCount,
        Boolean isAboveAverage
) {
}
