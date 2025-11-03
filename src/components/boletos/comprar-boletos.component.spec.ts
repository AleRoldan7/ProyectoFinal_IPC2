import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComprarBoletosComponent } from './comprar-boletos.component';

describe('ComprarBoletosComponent', () => {
  let component: ComprarBoletosComponent;
  let fixture: ComponentFixture<ComprarBoletosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComprarBoletosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComprarBoletosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
