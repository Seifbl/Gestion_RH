import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandeattestationComponent } from './demandeattestation.component';

describe('DemandeattestationComponent', () => {
  let component: DemandeattestationComponent;
  let fixture: ComponentFixture<DemandeattestationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DemandeattestationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemandeattestationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
