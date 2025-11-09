import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SalasUsuarioComponent } from './salas-usuario.component';

describe('SalasUsuarioComponent', () => {
  let component: SalasUsuarioComponent;
  let fixture: ComponentFixture<SalasUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SalasUsuarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SalasUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
