import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormulariotemplatedrivenComponent } from './formulariotemplatedriven.component';

describe('FormulariotemplatedrivenComponent', () => {
  let component: FormulariotemplatedrivenComponent;
  let fixture: ComponentFixture<FormulariotemplatedrivenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormulariotemplatedrivenComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormulariotemplatedrivenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
