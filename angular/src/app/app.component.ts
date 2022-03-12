import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { CadastroService } from './componentes/cadastro/cadastro.service';
import { Log } from './componentes/cadastro/Log';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import { CourseDialogComponent } from './components/course-dialog/course-dialog.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'testeuploadlog';

  constructor(private dialog: MatDialog){}

  openDialog(){
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose=true;
    dialogConfig.autoFocus = true;
    dialogConfig.data ={
      id:'1',
      title: 'Angular for Beginners',
      description: 'Teste Dialog Data'

    }
    //dialogConfig.position = {top:'0',left:'0'}

    const dialogRef = this.dialog.open(CourseDialogComponent, dialogConfig);
    
    dialogRef.afterClosed().subscribe((data) => console.log('Dialog output:', data));

  }


}
