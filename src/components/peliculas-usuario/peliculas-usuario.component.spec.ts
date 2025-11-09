import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeliculasUsuarioComponent } from './peliculas-usuario.component';

describe('PeliculasUsuarioComponent', () => {
  let component: PeliculasUsuarioComponent;
  let fixture: ComponentFixture<PeliculasUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PeliculasUsuarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PeliculasUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
