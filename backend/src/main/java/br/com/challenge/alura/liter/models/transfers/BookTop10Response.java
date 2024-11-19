package br.com.challenge.alura.liter.models.transfers;

import br.com.challenge.alura.liter.models.entites.Book;

import java.util.List;

public record BookTop10Response(
    Double averageTop10,
    Long maxAverageTop10,
    Long minAverageTop10,
    Long countElements,
    List<Book> books
) {
}
