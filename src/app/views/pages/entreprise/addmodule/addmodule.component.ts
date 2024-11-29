import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { TokenStorageService } from '../../../../core/guard/token-storage.service';
import {
  FormGroup,
  FormControl,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { Departement } from 'src/app/modals/departement.modal';
import { DepartementService } from 'src/app/shared/services/department.service';

@Component({
  selector: 'app-addmodule',
  templateUrl: './addmodule.component.html',
  styleUrls: ['./addmodule.component.scss'],
})
export class AddmoduleComponent implements OnInit {
  submitted: boolean = false;
  departement: Departement = new Departement();

  public addDepartementForm: FormGroup = new FormGroup({
    name: new FormControl(''),
  });

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private departementService: DepartementService
  ) {}

  ngOnInit(): void {
    this.addDepartementForm = this.fb.group({
      name: ['', Validators.required],
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.addDepartementForm.invalid) {
      return;
    }

    if (this.submitted) {
      this.departementService.addDepartement(this.departement).subscribe(
        (response) => {
          console.log(response);
          this.router.navigate(['company/list-modules']);
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }
}
