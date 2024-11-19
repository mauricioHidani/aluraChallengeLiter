package br.com.challenge.alura.liter.models.transfers;

public enum LanguageRequest {
    EN("en", "inglês", "ingles", "english", "en"),
    PT("pt", "português", "portugues", "portuguese", "pt"),
    FR("fr", "francês", "frances", "french", "fr"),
    IT("it", "italiano", "italian", "it"),
    GE("al", "alemão", "alemao", "german", "ge")
    ;

    private String target;
    private String[] languages;

    LanguageRequest(String target, String... languages) {
        this.languages = languages;
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public String[] getLanguages() {
        return languages;
    }
}
