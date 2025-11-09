import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteSalaMasGustadaComponent } from './reporte-sala-mas-gustada.component';

describe('ReporteSalaMasGustadaComponent', () => {
  let component: ReporteSalaMasGustadaComponent;
  let fixture: ComponentFixture<ReporteSalaMasGustadaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteSalaMasGustadaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteSalaMasGustadaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
