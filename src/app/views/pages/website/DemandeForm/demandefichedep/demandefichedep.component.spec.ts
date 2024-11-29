import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandefichedepComponent } from './demandefichedep.component';

describe('DemandefichedepComponent', () => {
  let component: DemandefichedepComponent;
  let fixture: ComponentFixture<DemandefichedepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DemandefichedepComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemandefichedepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
