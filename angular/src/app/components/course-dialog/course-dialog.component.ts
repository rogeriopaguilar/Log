import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-course-dialog',
  templateUrl: './course-dialog.component.html',
  styleUrls: ['./course-dialog.component.css']
})
export class CourseDialogComponent implements OnInit {

  form: FormGroup =  {} as FormGroup;
  description:String='';


  constructor(private fb:FormBuilder,
              private dialogRef: MatDialogRef<CourseDialogComponent>,
              @Inject(MAT_DIALOG_DATA) data:any) { 

                this.description=data.description;
              }

  ngOnInit(): void {

    this.form=this.fb.group({description:[this.description, []], nome:['', [Validators.required]]});
  }

  save() {
    this.dialogRef.close(this.form.value);
  }

  close(){
    this.dialogRef.close();
  }
}
