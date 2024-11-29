import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListmoduleComponent } from './listmodule.component';

describe('ListmoduleComponent', () => {
  let component: ListmoduleComponent;
  let fixture: ComponentFixture<ListmoduleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListmoduleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListmoduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
