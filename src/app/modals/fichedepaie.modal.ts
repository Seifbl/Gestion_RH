import { Employee } from "./employee.modal";

export class DemandeFicheDePaie {
    demandeFicheDePaieId: number;
    employee: Employee;
    requestDate: Date;
    approvedByAdmin: boolean;
    approvedByCD: boolean;
    status: string;
    finalised: boolean;
}