package br.com.preventsenior.teste.servico;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.preventsenior.teste.entidade.Log;
import br.com.preventsenior.teste.repositorio.LogRepositorio;

@Service
@Transactional
public class LogService {

	final SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Autowired
	private LogRepositorio logRepositorio;

	public Object[] listar(int pagina, int quantiDadeRegistrosPorPagina) {
		return logRepositorio.listar(pagina, quantiDadeRegistrosPorPagina);
	}

	public List<Log> listar() {
		return logRepositorio.listar();
	}

	public Log buscarPorId(Long id) {
		Log log = logRepositorio.buscarPorId(id);
		if(log != null) {
			log.setDataStr(formatoData.format(log.getData()));
		}
		return log;
	}

	public Log inserir(Log log) {
		ajustarData(log);
		return this.logRepositorio.inserir(log);
	}
	
	public Log excluir(Log log) {
		return this.logRepositorio.excluir(log);
	}
	
	public void alterar(Log log) {
		ajustarData(log);
		this.logRepositorio.alterar(log);
	}

	private void ajustarData(Log log) {
		if(log.getDataStr() != null) {
			try {
				log.setData(formatoData.parse(log.getDataStr()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	
} 
