import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FileuploadcomponentComponent } from './componentes/fileuploadcomponent/fileuploadcomponent.component';

import { CadastroComponent } from './componentes/cadastro/cadastro.component';
import { ConsultalogComponent } from './componentes/consultalog/consultalog.component';
import { FormulariotemplatedrivenComponent } from './componentes/formulariotemplatedriven/formulariotemplatedriven.component'

const routes: Routes = [
  {path:'upload', component: FileuploadcomponentComponent},
  {path:'cadastro', component: CadastroComponent},
  {path:'formtemplatedriven', component: FormulariotemplatedrivenComponent},
  {path:'consultalog', component: ConsultalogComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
