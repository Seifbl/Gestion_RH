import { Employee } from './employee.modal';

export class DemandeConge {
  demandeCongeId: number;
  employee: Employee = new Employee();
  typeConge: string;
  startDate: Date;
  endDate: Date;
  returnDate: Date;
  remplacant: Employee = new Employee();
  requestDate: Date;
  approvedByAdmin: boolean;
  approvedByCD: boolean;
  status: string;
  finalised: boolean;
}
