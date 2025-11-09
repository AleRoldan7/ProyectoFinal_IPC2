import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteSistemaComponent } from './reporte-sistema.component';

describe('ReporteSistemaComponent', () => {
  let component: ReporteSistemaComponent;
  let fixture: ComponentFixture<ReporteSistemaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteSistemaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteSistemaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
