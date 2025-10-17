import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuarioComunComponent } from './usuario-comun.component';

describe('UsuarioComunComponent', () => {
  let component: UsuarioComunComponent;
  let fixture: ComponentFixture<UsuarioComunComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsuarioComunComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsuarioComunComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
