import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloquearComponent } from './bloquear.component';

describe('BloquearComponent', () => {
  let component: BloquearComponent;
  let fixture: ComponentFixture<BloquearComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BloquearComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BloquearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
