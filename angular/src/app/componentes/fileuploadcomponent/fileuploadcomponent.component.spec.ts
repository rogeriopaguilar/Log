import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileuploadcomponentComponent } from './fileuploadcomponent.component';

describe('FileuploadcomponentComponent', () => {
  let component: FileuploadcomponentComponent;
  let fixture: ComponentFixture<FileuploadcomponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FileuploadcomponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FileuploadcomponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
