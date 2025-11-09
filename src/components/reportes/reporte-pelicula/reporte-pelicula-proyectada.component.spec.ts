import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportePeliculaProyectadaComponent } from './reporte-pelicula-proyectada.component';

describe('ReportePeliculaProyectadaComponent', () => {
  let component: ReportePeliculaProyectadaComponent;
  let fixture: ComponentFixture<ReportePeliculaProyectadaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReportePeliculaProyectadaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportePeliculaProyectadaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
