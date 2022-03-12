# Teste Aplicação de Log
 
 ## Ambiente
	 Ubuntu 20.04
	 jdk 1.8
	 Angular CLI 12.1.4
	 Angular 12.1.4
	 Node 16.5.0
	 npm 7.9.1
	 Postgres 12
	 maven
	 
 ## Executar aplicação angular (está no diretório doc)
 	 - Descompactar o arquivo zip que está na pasta angular
	 - posicionar no diretório onde o projeto angular está após descompactar
	 - carregar as dependências do projeto com o comando npm start
	 - digitar ng serve e aguardar o servidor subir
	 - a aplicação poderá ser acessada em http://localhost:4200 
	 - existe um proxy configurado para não ocorrer o problema relacionado ao cors, portanto
	   a porta do servidor bacn-end não deve ser modificada
	   
 ## Banco de dados
	  - deve existir um usuário chamado postgres com a senha postgres com direitos para criação/remoção de objetos de banco de dados, pois o hibernate irá criar a tabela e sequence necessárias na primeira vez que a aplicação for executada
	  - deve ser criada uma base de dados chamada log
	  - não foi definido um esquema separado; o esquema public é utilizado como padrão
	  
 ## Gerar a aplicação back-end
	 - Executar o comando no diretório onde está o arquivo pom.xml:        	
	    mvn clean package -Dmaven.test.skip=true
	 - Ao final será criado um arquivo .jar no diretório target
	 
## Executar a aplicação back-end

	 - Posicionar no diretório target, onde está o jar gerado no item anterior
	 - Executar o comando java -jar Log-0.0.1-SNAPSHOT.jar
	 - A aplicação será inicializada e estará disponível em http://localhost:8080/
	 - As rotas da aplicação estarão em br.com.preventsenior.teste.controle.LogControle e estão demonstradas no arquivo de evidências de teste que está na pasta doc
 
## Executar testes unitários

	- Caso a aplicação esteja rodando, sair da mesma, pois a porta utilizada nos testes é a mesma
	- executar o comando: mvn clean test


