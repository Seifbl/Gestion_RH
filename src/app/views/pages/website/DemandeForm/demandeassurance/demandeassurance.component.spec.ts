import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandeassuranceComponent } from './demandeassurance.component';

describe('DemandeassuranceComponent', () => {
  let component: DemandeassuranceComponent;
  let fixture: ComponentFixture<DemandeassuranceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DemandeassuranceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemandeassuranceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
