import { Injectable } from '@angular/core';
import { Log } from '../cadastro/Log';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { ThisReceiver } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class CadastroService {

  constructor(private httpCLient:HttpClient) { }

  ELEMENT_DATA: Log[] = [];

  salvarLog(idAlteracao:number, ip:string, request: string,status:string,userAgent:string, data:string, dataStr:string):Observable<Log>{
    const httpOptions = {
      headers: new HttpHeaders({ 
        'Access-Control-Allow-Origin':'*' ,
      'Content-type': 'application/json'     })
    };    
    
    data = '';
    console.log("Salvar log...")
    if(idAlteracao == -1) {
      let obj : Log = {id:-1, ip, request, status, userAgent, data, dataStr};
      return this.httpCLient.post<Log>('api/log/inserir',obj, httpOptions);
    }

    let obj : Log = {id:idAlteracao, ip, request, status, userAgent, data, dataStr};
    return this.httpCLient.put<Log>('api/log/alterar',obj, httpOptions);

  }

  consultarLogs():Observable<Log[]>{
  
    const httpOptions = {
      headers: new HttpHeaders({ 
        'Access-Control-Allow-Origin':'*' ,
      'Content-type': 'application/json'     })
    };    

    console.log("Consultar todos os usuários...")
    return this.httpCLient.get<Log[]>('api/log',httpOptions);
  }
  

  consultarLogsComPaginacao(paginaAtual:number, strAcao:string, filtroIP: string):Observable<any[]>{
  
    const httpOptions = {
      headers: new HttpHeaders({ 
        'Access-Control-Allow-Origin':'*' ,
      'Content-type': 'application/json'     })
    };    

    console.log("Consultar com paginação...")
    return this.httpCLient.get<any[]>('api/logconsulta/' + paginaAtual +'/' + strAcao,httpOptions);
  }

  apagarLog(id:number):Observable<Log>{
    const httpOptions = {
      headers: new HttpHeaders({ 
        'Access-Control-Allow-Origin':'*' ,
      'Content-type': 'application/json'     })
    };    
    
    console.log("Remover log id:" + id);
  
    return this.httpCLient.delete<Log>('api/log/' + id, httpOptions);
  }

  consultarLog(id:number):Observable<Log>{
    const httpOptions = {
      headers: new HttpHeaders({ 
        'Access-Control-Allow-Origin':'*' ,
      'Content-type': 'application/json'     })
    };    
    
    console.log("Alteração log id:" + id);
  
    return this.httpCLient.get<Log>('api/log/' + id, httpOptions);
  }

  consultarLogComPaginacaoEFiltro(paginaAtual:number, strAcao:string, filtroIP:string) {
    const httpOptions = {
      headers: new HttpHeaders({ 
        'Access-Control-Allow-Origin':'*' ,
      'Content-type': 'application/json'     })
    };    
    
    console.log("Consultando log... " + filtroIP + '  Página: ' + paginaAtual);
    let obj : Log = {id:-1, ip:filtroIP, request:'', status:'', userAgent:'', data: '', dataStr:''};
    if(filtroIP.trim()){
      return this.httpCLient.get<any>('api/logconsultapaginacaoefiltro/' + filtroIP + '/' + paginaAtual +'/' + strAcao, httpOptions);
    } 

    return this.consultarLogsComPaginacao(paginaAtual,strAcao, '');
  }


}
