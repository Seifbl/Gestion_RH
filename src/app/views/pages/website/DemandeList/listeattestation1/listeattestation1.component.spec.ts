import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Listeattestation1Component } from './listeattestation1.component';

describe('Listeattestation1Component', () => {
  let component: Listeattestation1Component;
  let fixture: ComponentFixture<Listeattestation1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Listeattestation1Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Listeattestation1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
