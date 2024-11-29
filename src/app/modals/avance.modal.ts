import { Employee } from "./employee.modal";

export class DemandeAvanceSalaire {
    demandeAvanceSalaireId: number;
    employee: Employee = new Employee();
    montant: number;
    requestDate: Date;
    approvedByAdmin: boolean;
    approvedByCD: boolean;
    status: string;
    finalised: boolean;
  }