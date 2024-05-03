package br.com.alura.screammatch;

import br.com.alura.screammatch.model.DadosEpisode;
import br.com.alura.screammatch.model.DadosSeries;
import br.com.alura.screammatch.model.DadosTemporada;
import br.com.alura.screammatch.principal.Principal;
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
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreammatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreammatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();

//


		// http://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=25d2da1e
	}


}
