import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCineComponent } from './admin-cine.component';

describe('AdminCineComponent', () => {
  let component: AdminCineComponent;
  let fixture: ComponentFixture<AdminCineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminCineComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
