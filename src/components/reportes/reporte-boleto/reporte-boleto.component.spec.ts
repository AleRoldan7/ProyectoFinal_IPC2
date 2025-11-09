import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteBoletoComponent } from './reporte-boleto.component';

describe('ReporteBoletoComponent', () => {
  let component: ReporteBoletoComponent;
  let fixture: ComponentFixture<ReporteBoletoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteBoletoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteBoletoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
