import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs/operators'

@Component({
  selector: 'app-fileuploadcomponent',
  templateUrl: './fileuploadcomponent.component.html',
  styleUrls: ['./fileuploadcomponent.component.css']
})
export class FileuploadcomponentComponent implements OnInit {

  fileName = '';
  mensagem = 'Selecionar arquivo';
  constructor(private http: HttpClient) {}

  ngOnInit(): void {
  }

  onFileSelected(event:any) {

      const file:File = event.target.files[0];

      if (file) {

          this.fileName = file.name;

          const formData = new FormData();

          formData.append("file", file);

          this.mensagem = 'Aguarde...'
          const upload$ = this.http.post("api/log/upload", formData)
          .pipe(
            finalize(() => this.mensagem='Upload finalizado. O arquivo será processado em background.')
          );

          upload$.subscribe();

          
      }
  }

}
