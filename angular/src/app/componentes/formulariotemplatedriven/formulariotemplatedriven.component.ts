import { Component, OnInit } from '@angular/core';
import { Hero } from './Hero';
@Component({
  selector: 'app-formulariotemplatedriven',
  templateUrl: './formulariotemplatedriven.component.html',
  styleUrls: ['./formulariotemplatedriven.component.css']
})
export class FormulariotemplatedrivenComponent implements OnInit {

  powers = ['Really Smart', 'Super Flexible',
  'Super Hot', 'Weather Changer'];

  submitted = false;
  model : Hero = new Hero(-1,'','','');

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit() { this.submitted=true; 
    console.log('Submitted');
    this.submitted = true;
  }

  novo() {
    this.model = new Hero(-1,'','','');
  }

}
