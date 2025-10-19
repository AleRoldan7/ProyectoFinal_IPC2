import { TestBed } from '@angular/core/testing';

import { SalaServices } from './sala-services';

describe('SalaServices', () => {
  let service: SalaServices;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SalaServices);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
