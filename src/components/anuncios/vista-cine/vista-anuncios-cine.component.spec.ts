import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VistaAnunciosCineComponent } from './vista-anuncios-cine.component';

describe('VistaAnunciosCineComponent', () => {
  let component: VistaAnunciosCineComponent;
  let fixture: ComponentFixture<VistaAnunciosCineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VistaAnunciosCineComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VistaAnunciosCineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
