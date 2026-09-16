package Service.ReservaIA;

import Recursos.CategoriaRecurso;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReservaExtractor {
    private final ReservaExtractorService aiService;

    public ReservaExtractor() {
        OpenAiChatModel aiModel = OpenAiChatModel.builder()
                .baseUrl("http://Langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();

        aiService = AiServices.create(ReservaExtractorService.class, aiModel);
    }

    public ReservaExtraccion extraerReserva(String frase, List<CategoriaRecurso> categorias) {
        String listaCategorias = categorias.stream()
                .map(CategoriaRecurso::getDescripcion)
                .collect(Collectors.joining(", "));

        return aiService.extraer(
                frase,
                listaCategorias,
                LocalDate.now().toString()
        );
    }
}