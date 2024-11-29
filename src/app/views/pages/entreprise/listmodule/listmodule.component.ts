import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';
import { ActivatedRoute, Router } from '@angular/router';

import Swal from 'sweetalert2';
import { DepartementService } from 'src/app/shared/services/department.service';
import { UserService } from 'src/app/shared/services/user.service';
import { Departement } from 'src/app/modals/departement.modal';

@Component({
  selector: 'app-listmodule',
  templateUrl: './listmodule.component.html',
  styleUrls: ['./listmodule.component.scss'],
})
export class ListmoduleComponent implements OnInit {
  source: LocalDataSource = new LocalDataSource();
  departements: Departement[];
  isAuthorized: boolean = false;
  userType: string;

  settings = {
    columns: {
      departementName: {
        title: 'Name',
      },
    },
    attr: {
      class: 'table table-responsive',
    },
    actions: {
      columnTitle: '',
      custom: [
        {
          name: 'delete',
          title:
            '<a class="btn btn-icon"> <i class="text-danger feather icon-trash"></i></a>',
        },
        {
          name: 'update',
          title:
            '<a class="btn btn-icon"> <i class="text-primary feather icon-edit"></i></a>',
        },
      ],
      add: false,
      edit: false,
      delete: false,
      position: 'right',
    },
  };

  constructor(
    private changeDetectorRef: ChangeDetectorRef,
    private userService: UserService,
    private departementService: DepartementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userService.getUserProperties().subscribe(
      (response) => {
        this.userType = response.authorities[0].authority;
        if (this.userType === 'ADMIN') {
          this.isAuthorized = true;
          this.loadDepartements();
        }
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  loadDepartements(): void {
    this.departementService.getAllDepartements().subscribe(
      (res: Departement[]) => {
        this.departements = res;
        this.source.load(this.departements); // Load data into LocalDataSource
        this.changeDetectorRef.detectChanges();
      },
      (err: any) => {
        console.error('Error fetching departements', err);
      }
    );
  }

  onCustomAction(event: any): void {
    switch (event.action) {
      case 'delete':
        this.deleteDepartement(event.data);
        break;
      case 'update':
        this.updateDepartement(event.data.departementId);
        break;
      default:
        break;
    }
  }

  deleteDepartement(departement: Departement): void {
    Swal.fire({
      title: `Are you sure you want to delete ${departement.departementName}?`,
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.departementService
          .deleteDepartement(departement.departementId)
          .subscribe(
            () => {
              Swal.fire({
                title: 'Deleted!',
                text: 'Departement has been deleted.',
                icon: 'success',
              });
              this.loadDepartements(); // Reload data after deletion
            },
            (err) => {
              Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Something went wrong!',
              });
              console.log(err);
            }
          );
      }
    });
  }

  updateDepartement(departementId: any): void {
    this.router.navigate(['company/update-module', departementId]);
  }
}
