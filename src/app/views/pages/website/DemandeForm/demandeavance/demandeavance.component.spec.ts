import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandeavanceComponent } from './demandeavance.component';

describe('DemandeavanceComponent', () => {
  let component: DemandeavanceComponent;
  let fixture: ComponentFixture<DemandeavanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DemandeavanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemandeavanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
