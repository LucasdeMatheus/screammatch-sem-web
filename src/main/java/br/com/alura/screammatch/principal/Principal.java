package br.com.alura.screammatch.principal;

import br.com.alura.screammatch.model.DadosEpisode;
import br.com.alura.screammatch.model.DadosSeries;
import br.com.alura.screammatch.model.DadosTemporada;
import br.com.alura.screammatch.model.Episodio;
import br.com.alura.screammatch.services.ConsumoApi;
import br.com.alura.screammatch.services.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConverteDados converter = new ConverteDados();

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=25d2da1e";
    private ConsumoApi consumoApi = new ConsumoApi();

    public void exibeMenu(){
        System.out.println("Digite o nome da série: ");
        var nomeSerie = sc.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSeries dados = converter.obterDados(json, DadosSeries.class);
        System.out.println(dados);
        //"http://www.omdbapi.com/?t= &season=1&episode=2&apikey=25d2da1e"
        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<= dados.totalTemporadas(); i++){
			json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
			DadosTemporada dadosTemporada = converter.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

//        for (int i = 0; i < dados.totalTemporadas(); i++) {
//            List<DadosEpisode> epsiodiosTemporada = temporadas.get(i).episodes();
//            for (int j = 0; j < epsiodiosTemporada.size(); j++) {
//                System.out.println(epsiodiosTemporada.get(j).titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodes().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisode> dadosEpisodes = temporadas.stream()
                .flatMap(t -> t.episodes().stream())
                .collect(Collectors.toList());
//               .toList() -> este e o de cima é a mesma coisa, a diferença é que o .toList não aceita alterações.

        System.out.println("\nTop 10 epsisódios");
        dadosEpisodes.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("primeiro Filtro(N/A)"))
                .sorted(Comparator.comparing(DadosEpisode::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenação "))
                .limit(10)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

//        System.out.println("Digite um trecho do Titulo do ep");
//        var trechoTitulo = sc.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo))
//                .findFirst();
//
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado");
//            System.out.println("temporada" + episodioBuscado.get().getTemporada());
//        }else{
//            System.out.println("Episodio não encontrado");
//        }
        //        System.out.println("a partir de que ano você deseja ver os episodios? ");
//        var ano = sc.nextInt();
//        sc.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                        "Episodio: " + e.getTitulo() +
//                        "Data lançamento: " + e.getDataLancamento().format(formatador)
//                ));
            Map<Integer, Double> avaliacaoPorTemporada = episodios.stream()
                    .filter(e -> e.getAvaliacao() > 0.0)
                    .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacaoPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage() +
                "Melhor ep: " + est.getMax() +
                "Pior ep: " + est.getMin() +
                "Quantidade: " + est.getCount());
    }
}
