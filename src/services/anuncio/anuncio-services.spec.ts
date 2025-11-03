import { TestBed } from '@angular/core/testing';

import { AnuncioServices } from './anuncio-services';

describe('AnuncioServices', () => {
  let service: AnuncioServices;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnuncioServices);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
