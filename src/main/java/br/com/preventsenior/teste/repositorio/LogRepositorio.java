package br.com.preventsenior.teste.repositorio;


import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import br.com.preventsenior.teste.Aplicacao;
import br.com.preventsenior.teste.entidade.Log;

@Repository
public class LogRepositorio {

	@PersistenceContext
	private EntityManager em;

	static ExitCodeGenerator eg = new ExitCodeGenerator() {
		@Override
		public int getExitCode() {
			// no errors
			return 0;
		}
	};
	
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Aplicacao.class, new String[] {});
		try {
			LogRepositorio logRepositorio = ctx.getBean(LogRepositorio.class);
			
			Scanner scanner = new Scanner(System.in);
			int paginaAtual = 1;

			while(true) {
		        System.out.print("Digite o que quer fazer: ");
		        String strAcao = scanner.next();	
		        if("<<".equals(strAcao)) {
		        	paginaAtual = 1;
		        }
		        if("<".equals(strAcao)) {
		        	paginaAtual -= 1;
		        }
		        if(">".equals(strAcao)) {
		        	paginaAtual += 1;
		        }
		        if(">>".equals(strAcao)) {
		        	paginaAtual = Integer.MAX_VALUE;
		        }

				Object[] objConsulta = logRepositorio.listar(paginaAtual, 2);
				List<Log> listaLog = (List<Log>) objConsulta[0];
				for(Log log : listaLog) {
					System.out.println(log);
				}
				paginaAtual = (Integer) objConsulta[1];
				System.out.println("Página atual: " + objConsulta[1]);
				System.out.println("Total de Páginas: " + objConsulta[2]);
				System.out.println("Exibir botão última página: " + objConsulta[3]);
				System.out.println("Exibir botão primeira página: " + objConsulta[4]);
			}
		} finally {
			SpringApplication.exit(ctx, eg);
		}

	}
	
	public Object[] listar(int pagina, int quantiDadeRegistrosPorPagina) {

		Number n = (Number) em.createQuery("select count(p) from Log p").getSingleResult();

		if(pagina <=0) {
			pagina = 1;
		}

		
		int qtdeTotalDePaginas = (n.intValue() / quantiDadeRegistrosPorPagina);
		if (n.intValue() % quantiDadeRegistrosPorPagina != 0) {
			qtdeTotalDePaginas++;
		}

		
		if (pagina > qtdeTotalDePaginas) {
			pagina = qtdeTotalDePaginas;
		}

		return new Object[] { em.createQuery("select p from Log p order by data desc").setMaxResults(quantiDadeRegistrosPorPagina)
				.setFirstResult((pagina - 1) * quantiDadeRegistrosPorPagina).getResultList(), 
				pagina, qtdeTotalDePaginas, pagina < qtdeTotalDePaginas, pagina > 1 };

	}

	public List<Log> listar() {
		return em.createQuery("select p from Log p").getResultList();
	}

	public Log buscarPorId(Long id) {
		return em.find(Log.class, id);
	}

	public Log inserir(Log log) {
		log = em.merge(log);
		em.flush();
		return log;
	}

	public Log excluir(Log log) {
		log = em.find(Log.class, log.getId());
		if (log != null) {
			em.remove(log);
		}
		return log;
	}

	public void alterar(Log log) {
		Log logTmp = em.find(Log.class, log.getId());
		if (logTmp != null) {
			log = em.merge(log);
		}
	}
}
