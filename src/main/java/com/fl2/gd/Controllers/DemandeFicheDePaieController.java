package com.fl2.gd.Controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl2.gd.Entities.DemandeFicheDePaie;
import com.fl2.gd.Entities.Employee;
import com.fl2.gd.Services.DemandeFicheDePaieService;
import com.fl2.gd.Services.EmployeeService;
import com.fl2.gd.Services.JwtService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/demandes-fiche-de-paie")
@CrossOrigin("*")
public class DemandeFicheDePaieController {
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private DemandeFicheDePaieService demandeFicheDePaieService;
	 @Autowired
	 private EmployeeService  employeeService;

	@GetMapping("/getAllDemandesFicheDePaie")
	public List<DemandeFicheDePaie> getAllDemandesFicheDePaie(@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.getAllDemandesFicheDePaie();
	}
	@GetMapping("/getDemandeFicheDePaieById/{id}")
	public DemandeFicheDePaie getDemandeFicheDePaieById(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.getDemandeFicheDePaieById(id);
	}
	@PostMapping("/addDemandeFicheDePaie/{employeeId}")
	public void addDemandeFicheDePaie(@PathVariable int employeeId, @RequestBody DemandeFicheDePaie demandeFicheDePaie, @RequestHeader(name = "Authorization") String authorizationHeader) {
		DemandeFicheDePaie newDemande = demandeFicheDePaie;
		Employee requester = employeeService.getEmployeeById(employeeId);
		newDemande.setEmployee(requester);
		
		if(newDemande.isApprovedByAdmin()) {
		requester.setFicheDePaieTakenForThisMonth(true);
		employeeService.saveEmployee(requester);
		}
		demandeFicheDePaieService.saveDemandeFicheDePaie(newDemande);
	}
	
	@PutMapping("/editDemandeFicheDePaie")
	public void editDemandeFicheDePaie(@RequestBody DemandeFicheDePaie demandeFicheDePaie, @RequestHeader(name = "Authorization") String authorizationHeader) {
		demandeFicheDePaieService.saveDemandeFicheDePaie(demandeFicheDePaie);
	}
	
	@DeleteMapping("/deleteDemandeFicheDePaieById/{id}")
	public void deleteDemandeFicheDePaie(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader) {
		demandeFicheDePaieService.deleteDemandeFicheDePaie(id);
	}
	
	@GetMapping("/getAllRecentDemandesFicheDePaie")
	public List<DemandeFicheDePaie> getAllRecentDemandesFicheDePaie(@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.getAllRecentDemandesFicheDePaie();
	}
	
	@GetMapping("/checkIfEmployeeHasUntreatedRequest/{id}")
	public DemandeFicheDePaie checkIfEmployeeHasUntreatedRequest(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.checkIfEmployeeHasUntreatedRequest(id);
	}

	
	@GetMapping("/findDemandeFicheDePaieByEmployeeId/{employeeId}")
	public List<DemandeFicheDePaie> findDemandeFicheDePaieByEmployeeId(@PathVariable int employeeId,@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.findDemandeFicheDePaieByEmployeeId(employeeId);
	}

	@GetMapping("/findDemandeFicheDePaieByDepartementId/{departementId}")
	public List<DemandeFicheDePaie> findDemandeFicheDePaieByDepartementId(@PathVariable int departementId,@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.findDemandeFicheDePaieByDepartementId(departementId);
	}
	
	@GetMapping("/findDemandeFicheDePaieForChefDepartement/{departementId}")
	public List<DemandeFicheDePaie> findDemandeFicheDePaieForChefDepartement(@PathVariable int departementId,@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.findDemandeFicheDePaieForChefDepartement(departementId);
	}
	

	@GetMapping("/findDemandeFicheDePaieForAdmin/{departementId}")
	public List<DemandeFicheDePaie> findDemandeFicheDePaieForAdmin(@PathVariable int departementId,@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.findDemandeFicheDePaieForAdmin(departementId);
	}
	@GetMapping("/findAllRecentDemandesFicheDePaieForAdmin")
	public List<DemandeFicheDePaie> findAllRecentDemandesFicheDePaieForAdmin(@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.findAllRecentDemandesFicheDePaieForAdmin();
	}
	
	//_______________________________________________________________________
	
	@GetMapping("/findFinalisedDemandeFicheDePaieByEmployeeId/{employeeId}")
	public List<DemandeFicheDePaie> findFinalisedDemandeFicheDePaieByEmployeeId(@PathVariable int employeeId,@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.findFinalisedDemandeFicheDePaieByEmployeeId(employeeId);
	}
	@GetMapping("/findFinalisedDemandeFicheDePaieByDepartementId/{departementId}")
	public List<DemandeFicheDePaie> findFinalisedDemandeFicheDePaieByDepartementId(@PathVariable int departementId,@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.findFinalisedDemandeFicheDePaieByDepartementId(departementId);
	}
	@GetMapping("/findAllFinalisedDemandesFicheDePaie")
	public List<DemandeFicheDePaie> findAllFinalisedDemandesFicheDePaie(@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.findAllFinalisedDemandesFicheDePaie();
	}
	@GetMapping("/findDemandeFicheDePaieByFilter/{id}/{date}")
	public List<DemandeFicheDePaie> findDemandeFicheDePaieByFilter(@PathVariable(required = false) int id ,@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeFicheDePaieService.findDemandeFicheDePaieByFilter(id, date);
	}
	
	
	@GetMapping("/generateFdpPdf/{id}")
	public ResponseEntity<byte[]> generatePdf(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader) {
	    DemandeFicheDePaie demande = demandeFicheDePaieService.getDemandeFicheDePaieById(id);

	    if (demande == null) {
	        return ResponseEntity.notFound().build();
	    }

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = sdf.format(new Date());

            Image logo = Image.getInstance("src/main/resources/Images/logo.png");
            logo.scaleToFit(100, 50);
            logo.setAlignment(Element.ALIGN_LEFT);
            document.add(logo);

            // Add Company Information
            Font companyFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Paragraph companyInfo = new Paragraph("IKSON\n13 Campus Manouba\n4400 Manouba, Tunis\n", companyFont);
            companyInfo.setSpacingAfter(10f);
            document.add(companyInfo);

            // Add Payslip Title
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph("Fiche de Paie", titleFont);
            title.setAlignment(Element.ALIGN_RIGHT);
            document.add(title);

            // Add Payslip Period
            Font periodFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            Paragraph period = new Paragraph("Période du : 01/05/2024 au 31/05/2024\nPaiement le : 31/05/2024", periodFont);
            period.setAlignment(Element.ALIGN_RIGHT);
            period.setSpacingAfter(20f);
            document.add(period);

            // Add Employee Information
            Employee employee = demande.getEmployee();
            Paragraph employeeInfo = new Paragraph(employee.getName() + " " + employee.getLastName() + "\n13 rue el kachachine,\n45000 TUNIS", periodFont);
            employeeInfo.setSpacingAfter(20f);
            document.add(employeeInfo);

            // Add Employee and Contract Details
            PdfPTable employeeTable = new PdfPTable(2);
            employeeTable.setWidthPercentage(100);
            employeeTable.setSpacingBefore(10);

            addTableCell(employeeTable, "Matricule : " + employee.getEmployeeId(), PdfPCell.ALIGN_LEFT, false);
            addTableCell(employeeTable, "N° Sécurité Sociale : " + employee.getCin(), PdfPCell.ALIGN_LEFT, false);
            addTableCell(employeeTable, "Entrée le : " + sdf.format(employee.getJoinDate()), PdfPCell.ALIGN_LEFT, false);
            addTableCell(employeeTable, "Emploi : " + employee.getFunction(), PdfPCell.ALIGN_LEFT, false);
            addTableCell(employeeTable, "Qualification : Non Cadre", PdfPCell.ALIGN_LEFT, false);
            addTableCell(employeeTable, "Coefficient : 100", PdfPCell.ALIGN_LEFT, false);
            addTableCell(employeeTable, "Plafond Sécurité Sociale : 3,428.00 DT", PdfPCell.ALIGN_LEFT, false);
            addTableCell(employeeTable, "Contrat : CDI", PdfPCell.ALIGN_LEFT, false);

            document.add(employeeTable);

            // Add Salary Details
            PdfPTable salaryTable = new PdfPTable(5);
            salaryTable.setWidthPercentage(100);
            salaryTable.setSpacingBefore(10);
            salaryTable.setWidths(new float[]{2, 1, 1, 1, 1});

            addTableCell(salaryTable, "Rubriques", PdfPCell.ALIGN_CENTER, true);
            addTableCell(salaryTable, "Base", PdfPCell.ALIGN_CENTER, true);
            addTableCell(salaryTable, "Cot. Salariales", PdfPCell.ALIGN_CENTER, true);
            addTableCell(salaryTable, "Taux Patronal", PdfPCell.ALIGN_CENTER, true);
            addTableCell(salaryTable, "Cot. Patronales", PdfPCell.ALIGN_CENTER, true);

            double salary = employee.getSalary();
            double tva = salary * 0.15;
            double ticketsResto = 100;
            double assurance = salary * 0.005;

            addSalaryRow(salaryTable, "SALAIRE DE BASE", salary, 0, 0, 0);
            addSalaryRow(salaryTable, "SALAIRE BRUT", salary, 0, 0, 0);
            addSalaryRow(salaryTable, "SANTÉ", 0, 0, 0, 0);
            addSalaryRow(salaryTable, "Sécurité sociale", salary, 0, 7, 119.00);
            addSalaryRow(salaryTable, "ACCIDENTS DU TRAVAIL/MALADIES ", 0, 0, 2.22, 37.74);
            addSalaryRow(salaryTable, "RETRAITE", 0, 0, 0, 0);
            addSalaryRow(salaryTable, "FAMILLE - SÉCURITÉ SOCIALE", 0, 0, 0, 55.65);
            addSalaryRow(salaryTable, "ASSURANCE CHOMAGE", 0, 0, 0, 0);
            addSalaryRow(salaryTable, "Assurance chômage tranche A", salary, 0, 4.05, 68.85);
            addSalaryRow(salaryTable, "Assurance chômage tranche B ", salary, 0, 0.15, 2.55);
            addSalaryRow(salaryTable, "AUTRES CONTRIBUTIONS DUES PAR L'EMPLOYEUR", 0, 0, 0, 27.98);
            addSalaryRow(salaryTable, "TOTAL DES REVENUS", salary, 0, 305.85, 67.73);
           

            document.add(salaryTable);

            // Add Net Pay
            PdfPTable netPayTable = new PdfPTable(1);
            netPayTable.setWidthPercentage(100);
            netPayTable.setSpacingBefore(10);

            addTableCell(netPayTable, "NET À PAYER AVANT IMPÔT SUR LE REVENU", PdfPCell.ALIGN_RIGHT, true);
            addTableCell(netPayTable, String.format("%.2f DT", salary - tva - assurance - ticketsResto), PdfPCell.ALIGN_RIGHT, true);

            document.add(netPayTable);

            document.close();
	    } catch (DocumentException | IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.badRequest().body(null);
	    }

	    byte[] pdfBytes = outputStream.toByteArray();
	    return ResponseEntity.ok()
	            .header("Content-Disposition", "attachment; filename=demande_attestation_travail.pdf")
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(pdfBytes);
	}
	
    private void addTableCell(PdfPTable table, String text, int alignment, boolean isHeader) {
        Font font = isHeader ? new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD) : new Font(Font.FontFamily.TIMES_ROMAN, 12);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(5);
        table.addCell(cell);
    }
    
    
    private  void addSalaryRow(PdfPTable table, String rubrique, double base, double cotSal, double tauxPatronal, double cotPat) {
        addTableCell(table, rubrique, PdfPCell.ALIGN_LEFT, false);
        addTableCell(table, String.format("%.2f DT", base), PdfPCell.ALIGN_RIGHT, false);
        addTableCell(table, String.format("%.2f", cotSal), PdfPCell.ALIGN_RIGHT, false);
        addTableCell(table, String.format("%.2f", tauxPatronal), PdfPCell.ALIGN_RIGHT, false);
        addTableCell(table, String.format("%.2f DT", cotPat), PdfPCell.ALIGN_RIGHT, false);
    }

    
}
