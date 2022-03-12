import { Component, OnInit } from '@angular/core';
import { FormBuilder,  FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CadastroService } from './cadastro.service';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css']
})
export class CadastroComponent implements OnInit {

  cadastroService : CadastroService;

  logFormGroup : FormGroup = {} as FormGroup;

  exibirDadosGravados:boolean = false;

  idAlteracao:string = '';
  incluindo:boolean = false;
  alterando:boolean = false;


  
  constructor(cadastroServiceTMP:CadastroService, private router: Router, private route: ActivatedRoute) { 
    this.cadastroService = cadastroServiceTMP;
  }

  ngOnInit(): void {
      this.exibirDadosGravados = false;
      console.log("NGOninit - CADASTRO");
      this.route.queryParams.subscribe(params => {
          console.log('ID parâmetro: ' + params['id']);
          if(params['id']) {
            this.idAlteracao = params['id']
            this.idAlteracao = this.idAlteracao.replace('-alterar','');
            console.log('ID alteração: ' + this.idAlteracao);

          }
          if(!this.idAlteracao){
              this.incluindo = true;
              this.alterando = false;
          } else {
            this.incluindo = false;
            this.alterando = true;
            //carregar dados
          }
          console.log('Incluindo: ', this.incluindo);
          console.log('Alterando: ', this.alterando);

          if(this.incluindo) {
            this.criarFormGroup(null);
          } else {
            this.cadastroService.consultarLog(parseInt(this.idAlteracao,10))
              .subscribe( (resposta) => {

                            
                            this.criarFormGroup(resposta); //Trocar pelos valores

                          }
                        );
          }
    });
  }
 
  criarFormGroup(logAlteracao:any) {
    if( logAlteracao == null) {
      this.logFormGroup =  new FormBuilder().group({ip:['', [Validators.required]],
        request: ['', [Validators.required]],
        status: ['', [Validators.required]],
        userAgent: ['', [Validators.required]],
        data:   ['', [Validators.required]],
        dataStr:   ['', [Validators.required]]

      });
    } else {
      this.logFormGroup =  new FormBuilder().group({ip:[logAlteracao.ip, [Validators.required]],
        request: [logAlteracao.request, [Validators.required]],
        status: [logAlteracao.status, [Validators.required]],
        userAgent: [logAlteracao.userAgent, [Validators.required]],
        dataStr:   [logAlteracao.dataStr, [Validators.required]],
        data:   [logAlteracao.data, [Validators.required]]

    });
  }
}

  aoEnviar(){  

    console.log('Ao enviar CadastroComponent: ');

    this.cadastroService.salvarLog( this.idAlteracao != null && this.idAlteracao != '' ? parseInt(this.idAlteracao,10) : -1,
                                    this.logFormGroup.get('ip')?.value,
                                    this.logFormGroup.get('request')?.value, 
                                    this.logFormGroup.get('status')?.value,
                                    this.logFormGroup.get('userAgent')?.value,
                                    '',
                                    this.logFormGroup.get('dataStr')?.value
                                    )
    
                          .subscribe((resposta)=>{
                            console.log(resposta);
                            this.exibirDadosGravados = true;

                            //this.criarFormGroup();                          
                            //,this.router.navigate(['usuario/listar']);
                          });
    
    
  }

  esconderMsgDadosGravados(){
    console.log('esconderMsgDadosGravados');
    this.exibirDadosGravados = false;
  }  

}
