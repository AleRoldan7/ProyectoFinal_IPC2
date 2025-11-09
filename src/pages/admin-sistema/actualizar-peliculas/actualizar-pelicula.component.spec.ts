import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualizarPeliculaComponent } from './actualizar-pelicula.component';

describe('ActualizarPeliculaComponent', () => {
  let component: ActualizarPeliculaComponent;
  let fixture: ComponentFixture<ActualizarPeliculaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActualizarPeliculaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActualizarPeliculaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
