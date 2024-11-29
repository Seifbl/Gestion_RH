import { Employee } from "./employee.modal";

export class DemandeRemboursementAssurance {
    demandeRemboursementAssuranceId: number;
    employee: Employee;
    requestDocuments: any;
    documentName: string;
    requestDate: Date;
    approvedByAdmin: boolean;
    approvedByCD: boolean;
    status: string;
    finalised: boolean;
}