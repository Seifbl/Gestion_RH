import { Employee } from './employee.modal';

export class DemandeAttestationTravail {
  demandeAttestationTravailId: number;
  employee: Employee = new Employee();
  generalDirectorName: string;
  companyName: string;
  requestDate: Date;
  demandeReason: String;
  approvedByAdmin: boolean;
  approvedByCD: boolean;
  status: string;
  finalised: boolean;
}