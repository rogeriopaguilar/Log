import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CadastroService } from '../cadastro/cadastro.service';
import { Log } from '../cadastro/Log';



@Component({
  selector: 'app-consultalog',
  templateUrl: './consultalog.component.html',
  styleUrls: ['./consultalog.component.css']
})
export class ConsultalogComponent implements OnInit {


   ELEMENT_DATA: any[] = [];

    //{"id":293111,"ip":"192.168.234.82","request":"\"GET / HTTP/1.1\"","status":"200","userAgent":"\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"","dataStr":'',"data":"2021-01-01T03:00:40.554+0000"}
  

  displayedColumns: string[] = ['ip', 'request', 'status','userAgent', 'data', 'dataStr'];
  dataSource = this.ELEMENT_DATA;

  pagina = -1;
  qtdeTotalDePaginas = -1
  exibirProximoEUltimo = false;
  exibirAnteriorEPrimeiro = false;

  filtroIP : string = '';
  filtroDataInicial:Date = new Date();
  filtroDataFinal:Date = new Date();
  filtroRequest:string='';
  filtroStatus:string = '';
  filtroUserAgent:string='';
  
  constructor(private cadastroService:CadastroService, private router: Router,  private httpClient:HttpClient) { 
  }
 
  excluirLog(evt:any){
    console.log('Excluir log ' + evt.target.id);
    let id = evt.target.id
    this.cadastroService.apagarLog(id).subscribe((resposta)=>{
      console.log('atualizar após excluir');
      this.cadastroService.consultarLogsComPaginacao(this.pagina, "=",'').subscribe((resposta)=>{
          this.atualizarDadosDePaginacao(resposta);
      });
    });
  }

  alterarLog(evt:any){
    console.log('Alterar log ' + evt.target.id);
    let id = evt.target.id;
    this.router.navigateByUrl('/cadastro?id=' + id);
  }

 
  atualizarPaginaAtual() {

  }

  atualizarDadosDePaginacao(resposta:any){
    this.ELEMENT_DATA = resposta[0];
    this.dataSource = this.ELEMENT_DATA;

    this.pagina = resposta[1];
    this.qtdeTotalDePaginas = resposta[2];
    this.exibirProximoEUltimo = resposta[3];
    this.exibirAnteriorEPrimeiro = resposta[4];

  }

  moverParaPrimeiraPagina() {
    console.log('moverParaPrimeiraPagina');
    this.cadastroService.consultarLogComPaginacaoEFiltro(this.pagina, "<<",this.filtroIP).subscribe((resposta)=>{
        this.atualizarDadosDePaginacao(resposta);
    });
  }

  moverParaPaginaAnterior() {
    console.log('moverParaPaginaAnterior');

    this.cadastroService.consultarLogComPaginacaoEFiltro(this.pagina, "<",this.filtroIP).subscribe((resposta)=>{
      this.atualizarDadosDePaginacao(resposta);
    });

  }

  moverParaProximaPagina() {
    console.log('moverParaProximaPagina');

    this.cadastroService.consultarLogComPaginacaoEFiltro(this.pagina, ">",this.filtroIP).subscribe((resposta)=>{
      this.atualizarDadosDePaginacao(resposta);
    });

  }


  moverParaUltimaPagina() {
    console.log('moverParaUltimaPagina');
    this.cadastroService.consultarLogComPaginacaoEFiltro(this.pagina, ">>",this.filtroIP).subscribe((resposta)=>{
      this.atualizarDadosDePaginacao(resposta);

    });
    
  }

  ngOnInit(): void {
    this.cadastroService.consultarLogComPaginacaoEFiltro(this.pagina, "<",this.filtroIP).subscribe((resposta)=>{
      this.atualizarDadosDePaginacao(resposta);
    });
  }

  validateIPaddress(ipaddress:string) {
  
   if (/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/
   .test(ipaddress))
    {
      return (true)
    }
    alert("You have entered an invalid IP address!")
    return (false)
  }

  consultarLogComFiltro() {
    const httpOptions = {
      headers: new HttpHeaders({ 
        'Access-Control-Allow-Origin':'*' ,
      'Content-type': 'application/json'     })
    };    

    this.filtroIP = this.filtroIP.trim();
    
    if(!this.validateIPaddress(this.filtroIP) && this.filtroIP.length > 0){
      this.ELEMENT_DATA = [];
      this.dataSource = this.ELEMENT_DATA;
    
      this.pagina = -1;
      this.qtdeTotalDePaginas = -1
      this.exibirProximoEUltimo = false;
      this.exibirAnteriorEPrimeiro = false;
      return {} as Observable<any>;
    } 
    
    
    console.log("Consultando log... " + this.filtroIP);
      let obj : Log = {id:-1, ip:this.filtroIP, request:'', status:'', userAgent:'', data: '', dataStr:''};

    return this.cadastroService.consultarLogComPaginacaoEFiltro(1, "<<",this.filtroIP)
    .subscribe(resposta=>{console.log(resposta);
        this.atualizarDadosDePaginacao(resposta);
      });
      console.log('Consulta ok');
  }
  

}
