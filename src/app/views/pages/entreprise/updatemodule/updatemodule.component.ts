import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { TokenStorageService } from '../../../../core/guard/token-storage.service';
import { DepartementService } from 'src/app/shared/services/department.service';
import { Departement } from 'src/app/modals/departement.modal';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-updatemodule',
  templateUrl: './updatemodule.component.html',
  styleUrls: ['./updatemodule.component.scss'],
})
export class UpdatemoduleComponent implements OnInit {
  public editDepartementForm: FormGroup = new FormGroup({
    name: new FormControl(''),
  });


  departement: Departement = new Departement();
  departementId: any;
  submitted: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private departementService: DepartementService,
    private fb: FormBuilder,
  ) {
  
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const stId = +params.get('departementId')!;
      this.departementId = stId;
      this.departementService
      .getDepartement(this.departementId)
      .subscribe((res) => {
        this.departement = res;
      });
    });
    this.editDepartementForm = this.fb.group({
      name: ['', Validators.required],
    });

  }

  onSubmit() {
    this.submitted = true;
    if (this.editDepartementForm.invalid) {
      return;
    }

    if (this.submitted) {
      this.departementService.updateDepartement(this.departement).subscribe(
        (response) => {
          console.log(response);
          this.router.navigate(['company/list-modules'])
        },
        (error) => {
          console.log(error);
        }
      );
    }


  }
}
