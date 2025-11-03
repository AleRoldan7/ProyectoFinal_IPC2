import { TestBed } from '@angular/core/testing';

import { PeliculaServices } from './pelicula-services';

describe('PeliculaServices', () => {
  let service: PeliculaServices;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PeliculaServices);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
