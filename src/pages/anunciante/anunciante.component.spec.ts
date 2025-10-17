import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnuncianteComponent } from './anunciante.component';

describe('AnuncianteComponent', () => {
  let component: AnuncianteComponent;
  let fixture: ComponentFixture<AnuncianteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnuncianteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnuncianteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
