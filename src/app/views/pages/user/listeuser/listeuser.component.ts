import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalDataSource } from 'ng2-smart-table';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { UserService } from 'src/app/shared/services/user.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-listeuser',
  templateUrl: './listeuser.component.html',
  styleUrls: ['./listeuser.component.scss'],
})
export class ListeuserComponent implements OnInit {
  settings: any;
  source: LocalDataSource;
  employees: any;
  isAuthorized: Boolean;
  userType?: String;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private employeeService: EmployeeService,
    private changeDetectorRef: ChangeDetectorRef,
    private userService: UserService
  ) {
    this.getall();
  }

  ngOnInit(): void {
    this.userService.getUserProperties().subscribe(
      (response) => {
        this.userType = response.authorities[0].authority;
        this.initializeSettings();
        if (this.userType === 'ADMIN') {
          this.isAuthorized = true;
          this.employeeService.getAllEmployees().subscribe(
            (res: any) => {
              this.employees = res;
              this.source = this.employees;
              this.changeDetectorRef.detectChanges();
              console.log(this.employees[0]);
            },
            (err: any) => {
              console.error('Error fetching students', err);
            }
          );
        } else if (this.userType === 'CHEF DEPARTEMENT') {
          this.userService.getUserProperties().subscribe((res1) => {
            this.userService.getUserInfo(res1.userId).subscribe((res2) => {
              console.log(res2.employeeId);
              this.employeeService
                .getEmployeesByDepartementId(res2.departement.departementId)
                .subscribe(
                  (res: any) => {
                    this.employees = res;
                    this.source = this.employees;
                    this.changeDetectorRef.detectChanges();
                  },
                  (err: any) => {
                    console.error('Error fetching students', err);
                  }
                );
            });
          });
        }
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  onCustomAction(event: any) {
    switch (event.action) {
      case 'Account':
        this.goToUserProfile(event.data.employeeId);
        break;
      case 'delete':
        this.deleteUser(event.data);
        break;
      case 'update':
        this.updateUser(event.data.employeeId);
        break;
      case 'affect account':
        this.onAffectAccount(event.data);
        break;
      case 'absence':
        this.onOpenAbsence(event.data);
        break;
      default:
        break;
    }
  }

  goToUserProfile(employeeId: any) {
    this.router.navigate(['users/profil', employeeId]);
  }

  deleteUser(employee: any) {
    Swal.fire({
      title: `Are you sure you want to delete ${employee.name} ${employee.lastName}?`,
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.employeeService.deleteEmployee(employee.employeeId).subscribe(
          (res) => {
            Swal.fire({
              title: 'Deleted!',
              text: 'Employee has been deleted.',
              icon: 'success',
            });
            this.employeeService.getAllEmployees().subscribe(
              (res: any) => {
                this.employees = res;
                this.source = this.employees;
                this.changeDetectorRef.detectChanges();
                console.log(this.employees[0]);
              },
              (err: any) => {
                console.error('Error fetching students', err);
              }
            );
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

  updateUser(employeeId: any) {
    this.router.navigate(['users/edit-profile', employeeId]);
  }

  hasAppUser(employee: any): boolean {
    return !!employee.appUser;
  }

  onAffectAccount(employee: any) {}

  onOpenAbsence(employee: any) {
    this.router.navigate(['users/user-absence', employee.employeeId]);
  }

  getall() {}

  ondetaille($event: any) {}

  initializeSettings(): void {
    this.settings = {
      columns: {
        name: {
          title: 'Nom',
        },
        lastName: {
          title: 'prenom',
        },
        cin: {
          title: 'cin',
        },
        function: {
          title: 'Fonction',
          // filter: {
          //   type: 'list',
          //   config: {
          //     selectText: 'Select...',
          //     list: [],
          //   },
          // },
        },
        salary: {
          title: 'salaire',
        },
        joinDate: {
          title: 'date de rejoint',
          type: 'html',
          valuePrepareFunction: (date: string | number | Date) => {
            if (date) {
              const formattedDate = new Date(date);
              const day = formattedDate.getDate();
              const month = formattedDate.getMonth() + 1;
              const year = formattedDate.getFullYear();
              return `${day < 10 ? '0' + day : day}-${
                month < 10 ? '0' + month : month
              }-${year}`;
            } else {
              return '';
            }
          },
        },
        departement: {
          title: 'Département',
          type: 'html',
          valuePrepareFunction: (departement: { departementName: any }) => {
            return departement ? departement.departementName : '';
          },
        },
      },
      attr: {
        class: 'table table-responsive',
      },
      actions: {
        columnTitle: '',
        custom: [
          {
            name: 'Account',
            title:
              '<a class="btn btn-icon"> <i class="text-success feather icon-navigation"></i></a>',
          },
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
          ...(this.userType === 'CHEF DEPARTEMENT'
            ? [
                {
                  name: 'absence',
                  title:
                    '<a class="btn btn-icon"> <i class="text-warning feather icon-calendar"></i> </a>',
                },
              ]
            : []),
          {
            name: 'affect account',
            title:
              '<a class="btn btn-icon"> <i class="text-primary feather icon-link-1"></i></a>',
          },
        ],
        add: false,
        edit: false,
        delete: false,
        position: 'right',
      },
    };
  }
}
