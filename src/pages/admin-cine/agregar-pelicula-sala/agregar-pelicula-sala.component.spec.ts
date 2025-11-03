import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgregarPeliculaSalaComponent } from './agregar-pelicula-sala.component';

describe('AgregarPeliculaSalaComponent', () => {
  let component: AgregarPeliculaSalaComponent;
  let fixture: ComponentFixture<AgregarPeliculaSalaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgregarPeliculaSalaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgregarPeliculaSalaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
