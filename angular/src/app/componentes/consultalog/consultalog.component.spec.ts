import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultalogComponent } from './consultalog.component';

describe('ConsultalogComponent', () => {
  let component: ConsultalogComponent;
  let fixture: ComponentFixture<ConsultalogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsultalogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultalogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
