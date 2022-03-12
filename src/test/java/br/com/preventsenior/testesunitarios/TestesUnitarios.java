package br.com.preventsenior.testesunitarios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import br.com.preventsenior.teste.Aplicacao;
import br.com.preventsenior.teste.entidade.Log;
import br.com.preventsenior.teste.servico.LogService;

@SpringBootTest
public class TestesUnitarios {

	private static final String DEFAULT_QUOTE = "Problema ocorrido na requisição web";

	String baseUrl = "http://localhost:8080";

	ExitCodeGenerator eg = new ExitCodeGenerator() {
		@Override
		public int getExitCode() {
			// no errors
			return 0;
		}
	};

	@Test
	public void testeInserirLogPeloServico() {
		ApplicationContext ctx = SpringApplication.run(Aplicacao.class, new String[] {});
		try {
			LogService logService = ctx.getBean(LogService.class);

			Log log = new Log();
			log.setData(new Date());
			log.setIp("10.0.0.1");
			log.setRequest("GET /AAA");
			log.setStatus("200");
			log.setUserAgent("Teste inserir");

			log = logService.inserir(log);
			assertNotNull(log);
			assertNotNull(log.getId());
		} finally {
			SpringApplication.exit(ctx, eg);
		}
	}

	@Test
	public void testeBuscarLogPorIdPeloServico() {
		ApplicationContext ctx = SpringApplication.run(Aplicacao.class, new String[] {});
		try {
			LogService logService = ctx.getBean(LogService.class);

			Log log = new Log();
			log.setData(new Date());
			log.setIp("10.0.0.1");
			log.setRequest("GET /AAA");
			log.setStatus("200");
			log.setUserAgent("Teste inserir");

			log = logService.inserir(log);
			assertNotNull(log);
			assertNotNull(log.getId());

			log = logService.buscarPorId(log.getId());
			assertNotNull(log);
			assertNotNull(log.getId());
		} finally {
			SpringApplication.exit(ctx, eg);
		}

	}

	@Test
	public void testeExcluirLogPorIdPeloServico() {
		ApplicationContext ctx = SpringApplication.run(Aplicacao.class, new String[] {});
		try {
			LogService logService = ctx.getBean(LogService.class);

			Log log = new Log();
			log.setData(new Date());
			log.setIp("10.0.0.1");
			log.setRequest("GET /AAA");
			log.setStatus("200");
			log.setUserAgent("Teste inserir");

			log = logService.inserir(log);
			assertNotNull(log);
			assertNotNull(log.getId());

			log = logService.excluir(log);

			log = logService.buscarPorId(log.getId());
			assertNull(log);

		} finally {
			SpringApplication.exit(ctx, eg);
		}

	}

	@Test
	public void testeInserirPeloServicoEBuscarPeloEndpoint() {
		ApplicationContext ctx = SpringApplication.run(Aplicacao.class, new String[] {});
		try {

			HttpClient client = HttpClientBuilder.create().build();

			try {

				LogService logService = ctx.getBean(LogService.class);

				Log log = new Log();
				log.setData(new Date());
				log.setIp("10.0.0.1");
				log.setRequest("GET /AAA");
				log.setStatus("200");
				log.setUserAgent("Teste inserir");

				log = logService.inserir(log);
				assertNotNull(log);
				assertNotNull(log.getId());

				String id = "" + log.getId();

				HttpUriRequest request = RequestBuilder.get().setUri(this.baseUrl + "/log/" + id)
						.setHeader(HttpHeaders.ACCEPT, "application/json").build();

				HttpResponse httpResponse = client.execute(request);

				if (httpResponse.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							DEFAULT_QUOTE + " HTTP response code:" + httpResponse.getStatusLine().getStatusCode());
				}

				String text = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8)).lines()
								.collect(Collectors.joining("\n"));
				assertNotNull(text);
				assertTrue(text.indexOf(id) != -1);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} finally {
			SpringApplication.exit(ctx, eg);
		}

	}

	@Test
	public void testeInserirPeloServicoEApagarPeloEndpoint() {
		ApplicationContext ctx = SpringApplication.run(Aplicacao.class, new String[] {});
		try {

			HttpClient client = HttpClientBuilder.create().build();

			try {

				LogService logService = ctx.getBean(LogService.class);

				Log log = new Log();
				log.setData(new Date());
				log.setIp("10.0.0.1");
				log.setRequest("GET /AAA");
				log.setStatus("200");
				log.setUserAgent("Teste inserir");

				log = logService.inserir(log);
				assertNotNull(log);
				assertNotNull(log.getId());

				String id = "" + log.getId();

				HttpUriRequest request = RequestBuilder.delete().setUri(this.baseUrl + "/log/" + id)
						.setHeader(HttpHeaders.ACCEPT, "application/json").build();

				HttpResponse httpResponse = client.execute(request);

				if (httpResponse.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							DEFAULT_QUOTE + " HTTP response code:" + httpResponse.getStatusLine().getStatusCode());
				}

				String text = new BufferedReader( /*
													 * RandomQuoteResponse responseBody = new ObjectMapper()
													 * .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
													 * false) .readValue(EntityUtils.toString(httpResponse.getEntity(),
													 * "UTF-8"), RandomQuoteResponse.class);
													 * 
													 * return responseBody.getContents().getQuotes().get(0).getQuote();
													 * 
													 */

						new InputStreamReader(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8)).lines()
								.collect(Collectors.joining("\n"));
				assertNotNull(text);
				assertTrue(text.indexOf(id) != -1);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} finally {
			SpringApplication.exit(ctx, eg);
		}

	}

	@Test
	public void testeInserirPeloEndpoint() {
		ApplicationContext ctx = SpringApplication.run(Aplicacao.class, new String[] {});
		try {

			HttpClient client = HttpClientBuilder.create().build();
			try {

				String strEnvio = "{\"id\":-1,\"ip\":\"192.168.234.85\",\"request\":\"\\\"GET / HTTP/1.1\\\"\",\"status\":\"200\",\"userAgent\":\"\\\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\\\"\",\"data\":\"2021-01-01T03:00:11.763+0000\"}";

				HttpUriRequest request = RequestBuilder.post().setUri(this.baseUrl + "/log/inserir")

						.setHeader(HttpHeaders.ACCEPT, "application/json")
						.addHeader(HttpHeaders.CONTENT_TYPE, "application/json").setEntity(new StringEntity(strEnvio))
						.build();

				HttpResponse httpResponse = client.execute(request);

				if (httpResponse.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							DEFAULT_QUOTE + " HTTP response code:" + httpResponse.getStatusLine().getStatusCode());
				}

				String texto = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8)).lines()
								.collect(Collectors.joining("\n"));
				assertNotNull(texto);

				String id = texto.substring(6, texto.indexOf(","));

				LogService logService = ctx.getBean(LogService.class);

				Log log = logService.buscarPorId(Long.parseLong(id));
				assertNotNull(log);
				assertNotNull(log.getId());

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} finally {
			SpringApplication.exit(ctx, eg);
		}

	}

	@Test
	public void testeAlterarPeloEndpoint() {
		ApplicationContext ctx = SpringApplication.run(Aplicacao.class, new String[] {});
		try {

			HttpClient client = HttpClientBuilder.create().build();
			try {

				String strEnvio = "{\"id\":-1,\"ip\":\"192.168.234.85\",\"request\":\"\\\"GET / HTTP/1.1\\\"\",\"status\":\"200\",\"userAgent\":\"\\\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\\\"\",\"data\":\"2021-01-01T03:00:11.763+0000\"}";

				HttpUriRequest request = RequestBuilder.post().setUri(this.baseUrl + "/log/inserir")

						.setHeader(HttpHeaders.ACCEPT, "application/json")
						.addHeader(HttpHeaders.CONTENT_TYPE, "application/json").setEntity(new StringEntity(strEnvio))
						.build();

				HttpResponse httpResponse = client.execute(request);

				if (httpResponse.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							DEFAULT_QUOTE + " HTTP response code:" + httpResponse.getStatusLine().getStatusCode());
				}

				String texto = new BufferedReader(
						new InputStreamReader(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8)).lines()
								.collect(Collectors.joining("\n"));
				assertNotNull(texto);

				String id = texto.substring(6, texto.indexOf(","));

				LogService logService = ctx.getBean(LogService.class);

				Log log = logService.buscarPorId(Long.parseLong(id));
				assertNotNull(log);
				assertNotNull(log.getId());

				texto = texto.replace("200", "300");

				request = RequestBuilder.put().setUri(this.baseUrl + "/log/alterar")
						.setHeader(HttpHeaders.ACCEPT, "application/json")
						.addHeader(HttpHeaders.CONTENT_TYPE, "application/json").setEntity(new StringEntity(texto))
						.build();						
				
				httpResponse = client.execute(request);

				if (httpResponse.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							DEFAULT_QUOTE + " HTTP response code:" + httpResponse.getStatusLine().getStatusCode());
				}

				log = logService.buscarPorId(Long.parseLong(id));
				assertNotNull(log);
				assertNotNull(log.getId());
				assertEquals(""+log.getStatus(), "300");

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} finally {
			SpringApplication.exit(ctx, eg);
		}

	}

}
