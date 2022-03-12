package br.com.preventsenior.teste;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.preventsenior.teste.entidade.Log;
import br.com.preventsenior.teste.servico.LogService;

@Service
public class LeitorLinhaArquivo{

	private static String caminhoArquivo = "/home/duquepop/Documents/ProductManager/src/main/java/cadastro/produtos/access.log";
	
	@Autowired
	LogService log;	

	
	public static void main(String[] args) throws IOException, ParseException{
			new LeitorLinhaArquivo().lerLinhaDoAquivo(caminhoArquivo);
	}
	
	public List<Log> lerLinhaDoAquivo() throws IOException, ParseException {
		return lerLinhaDoAquivo(caminhoArquivo);
	}	
	
	public List<Log> lerLinhaDoAquivo(String caminhoArquivo) throws IOException, ParseException {
		BufferedReader reader =null;
		final SimpleDateFormat parserData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		List<Log> listaLog = new ArrayList<Log>();
		try {
			 reader = new BufferedReader(new FileReader(caminhoArquivo));
			 int contLinha = 0;
			 String linha = "";
			 String[] dadosLinha = null;
			 while(linha != null) {
				 contLinha++;
				 System.out.println("Processando linha: " + contLinha);
				 linha = reader.readLine();
				 if(linha != null) {
					 dadosLinha = linha.split("\\|");
					 Log log = new Log();
					 log.setData(parserData.parse(dadosLinha[0]));
					 log.setIp(dadosLinha[1]);
					 log.setRequest(dadosLinha[2]);
					 log.setStatus(dadosLinha[3]);
					 log.setUserAgent(dadosLinha[4]);
					 
					 listaLog.add(log);
					 this.log.inserir(log);
					 
				 }
			 }
		}finally {
			if(reader != null) {
				try{reader.close();}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listaLog;
	}

}
