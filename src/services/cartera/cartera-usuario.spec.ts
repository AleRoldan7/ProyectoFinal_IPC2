import { TestBed } from '@angular/core/testing';

import { CarteraUsuario } from './cartera-usuario';

describe('CarteraUsuario', () => {
  let service: CarteraUsuario;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarteraUsuario);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
