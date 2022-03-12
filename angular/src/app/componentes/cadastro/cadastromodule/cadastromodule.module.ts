import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { CadastroComponent } from '../cadastro.component';
import { CadastroService } from '../cadastro.service';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import {MatNativeDateModule} from '@angular/material/core';


@NgModule({
 imports:      [ CommonModule, MatDatepickerModule, MatInputModule, MatNativeDateModule],
 declarations: [ CadastroComponent ],
 exports:      [ CadastroComponent ],
 providers: [ CadastroService]
})

export class CadastromoduleModule { }
