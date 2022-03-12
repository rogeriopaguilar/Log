package br.com.preventsenior.teste.controle;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.preventsenior.teste.LeitorLinhaArquivo;
import br.com.preventsenior.teste.entidade.Log;
import br.com.preventsenior.teste.servico.LogService;

@RestController
public class LogControle {

		@Autowired(required=true)
		private LogService service;
		
		@Autowired(required=true)
		private LeitorLinhaArquivo leitorLinhas;
		
		
		@GetMapping("/log")
		public List<Log> listarTodos() {
			return service.listar();
		}
		

		@GetMapping("/logconsulta/{paginaAtual}/{strAcao}")
		public Object[] listarComPaginacao(@PathVariable Integer paginaAtual, @PathVariable String strAcao) {

			if("<<".equals(strAcao)) {
	        	paginaAtual = 1;
	        }
	        if("<".equals(strAcao)) {
	        	paginaAtual -= 1;
	        	if(paginaAtual == 0) {
	        		paginaAtual = 1;
	        	}
	        }
	        if(">".equals(strAcao)) {
	        	paginaAtual += 1;
	        }
	        if(">>".equals(strAcao)) {
	        	paginaAtual = Integer.MAX_VALUE;
	        }

			
			Object[] obj = service.listar(paginaAtual, 10);
			return obj;
		}
		
		
		@GetMapping("/log/{id}")
		public Log buscarPorId(@PathVariable Long id) {
			return service.buscarPorId(id);
		}

		
		@DeleteMapping("/log/{id}")
		public Log apagar(@PathVariable Long id) {
			Log log = new Log();
			log.setId(id);
		    return service.excluir(log);
		}		
	
		@PostMapping("/log/inserir")
		public Log inserir(@RequestBody Log novoLog) {
			novoLog.setId(null);		
		    return service.inserir(novoLog);
	    }
		
		@PutMapping("/log/alterar")
		public void alterar(@RequestBody Log log) {
		    service.alterar(log);
	    }
		
		@PostMapping("/log/upload")
		public String handleFileUpload(@RequestParam("file") MultipartFile file,
				RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
			System.out.println("*********************UPLOAD " + leitorLinhas);
			String nomeArquivo = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + System.currentTimeMillis() + ".log";
			file.transferTo(new File(nomeArquivo));
			new Thread() {
				public void run() {
					try {
						leitorLinhas.lerLinhaDoAquivo(nomeArquivo);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			return "redirect:/";
		}
				
}
