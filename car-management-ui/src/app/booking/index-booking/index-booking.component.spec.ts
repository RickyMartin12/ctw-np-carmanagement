import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexBookingComponent } from './index-booking.component';

describe('IndexBookingComponent', () => {
  let component: IndexBookingComponent;
  let fixture: ComponentFixture<IndexBookingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndexBookingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndexBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
