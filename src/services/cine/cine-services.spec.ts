import { TestBed } from '@angular/core/testing';

import { CineServices } from './cine-services';

describe('CineServices', () => {
  let service: CineServices;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CineServices);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
