import { Departement } from './departement.modal';
import { AppUser } from './user.modal';

export class Employee {
  employeeId: number;
  cin: string;
  name: string;
  lastName: String;
  function: String;
  departement: Departement = new Departement();
  appUser: AppUser;

  salary: number;
  takenTimeOffDays: number;
  timeOffDays: number;
  joinDate: Date;
  nextTimeOffDate: Date;

  onTimeOff: boolean;
  avanceSalaireTakenForThisMonth: boolean;
  ficheDePaieTakenForThisMonth: boolean;
  blockedFromDemandeCongeUntil: Date;

  absencesThisMonth: number;
  absentForToday: boolean;
}
