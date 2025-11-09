import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteSalaComentarioComponent } from './reporte-sala-comentario.component';

describe('ReporteSalaComentarioComponent', () => {
  let component: ReporteSalaComentarioComponent;
  let fixture: ComponentFixture<ReporteSalaComentarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteSalaComentarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteSalaComentarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
