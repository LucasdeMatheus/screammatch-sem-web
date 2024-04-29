package br.com.alura.screammatch;

import br.com.alura.screammatch.model.DadosSeries;
import br.com.alura.screammatch.services.ConsumoApi;
import br.com.alura.screammatch.services.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class ScreammatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreammatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?i=tt3896198&apikey=25d2da1e");
		System.out.println(json);
		ConverteDados converter = new ConverteDados();
		DadosSeries dados = converter.obterDados(json, DadosSeries.class);
		System.out.println(dados);
	}


}
