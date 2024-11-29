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

import com.fl2.gd.Entities.DemandeConge;
import com.fl2.gd.Services.DemandeCongeService;
import com.fl2.gd.Services.EmployeeService;
import com.fl2.gd.Services.JwtService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/demandes-conge")
@CrossOrigin("*")
public class DemandeCongeController {

	 @Autowired
	 private JwtService jwtService;
	 @Autowired
	 private DemandeCongeService demandeCongeService;
	 @Autowired
	 private EmployeeService  employeeService;
	 
		@GetMapping("/getAllDemandesConges")
		public List<DemandeConge> getAllDemandesConge(@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.getAllDemandesConge();
		}
		@GetMapping("/getDemandeCongeById/{id}")
		public DemandeConge getDemandeCongeById(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.getDemandeCongeById(id);
		}
		@PostMapping("/addDemandeConge/{employeeId}")
		public void addDemandeConge(@PathVariable int employeeId,@RequestBody DemandeConge demandeConge, @RequestHeader(name = "Authorization") String authorizationHeader) {
			
			DemandeConge newDemande = demandeConge;
			newDemande.setRemplacant(null);
			newDemande.setEmployee(employeeService.getEmployeeById(employeeId));	
			demandeCongeService.saveDemandeConge(newDemande);
		}
		
		@PutMapping("/editDemandeConge")
		public void editDemandeConge(@RequestBody DemandeConge demandeConge, @RequestHeader(name = "Authorization") String authorizationHeader) {
			demandeCongeService.saveDemandeConge(demandeConge);
		}
		
		@DeleteMapping("/deleteDemandeCongeById/{id}")
		public void deleteDemandeConge(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader) {
			demandeCongeService.deleteDemandeConge(id);
		}
		
		@GetMapping("/getAllRecentDemandesConge")
		public List<DemandeConge> getAllRecentDemandesConge(@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.getAllRecentDemandesConge();
		}
		
		@GetMapping("/checkIfEmployeeHasUntreatedRequest/{id}")
		public DemandeConge checkIfEmployeeHasUntreatedRequest(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.checkIfEmployeeHasUntreatedRequest(id);
		}
		
		@GetMapping("/findDemandeCongeByEmployeeId/{employeeId}")
		public List<DemandeConge> findDemandeCongeByEmployeeId(@PathVariable int employeeId, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.findDemandeCongeByEmployeeId(employeeId);
		}
		
		@GetMapping("/findDemandeCongeByDepartementId/{departementId}")
		public List<DemandeConge> findDemandeCongeByDepartementId(@PathVariable int departementId, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.findDemandeCongeByDepartementId(departementId);
		}
		
		
		@GetMapping("/findDemandeCongeForChefDepartement/{departementId}")
		public List<DemandeConge> findDemandeCongeForChefDepartement(@PathVariable int departementId, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.findDemandeCongeForChefDepartement(departementId);
		}
		@GetMapping("/findDemandeCongeForAdmin/{departementId}")
		public List<DemandeConge> findDemandeCongeForAdmin(@PathVariable int departementId, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.findDemandeCongeForAdmin(departementId);
		}
		@GetMapping("/findAllRecentDemandesCongeForAdmin")
		public List<DemandeConge> findAllRecentDemandesCongeForAdmin(@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.findAllRecentDemandesCongeForAdmin();
		}
		
		 //_______________________________________________________________________
		
		@GetMapping("/findFinalisedDemandeCongeByDepartementId/{departementId}")
		public List<DemandeConge> findFinalisedDemandeCongeByDepartementId(@PathVariable int departementId, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.findFinalisedDemandeCongeByDepartementId(departementId);
		}
		@GetMapping("/findFinalisedDemandeCongeByEmployeeId/{employeeId}")
		public List<DemandeConge> findFinalisedDemandeCongeByEmployeeId(@PathVariable int employeeId, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.findFinalisedDemandeCongeByEmployeeId(employeeId);
		}
		@GetMapping("/findAllFinalisedDemandesConge")
		public List<DemandeConge> findAllFinalisedDemandesConge( @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.findAllFinalisedDemandesConge();
		}
		@GetMapping("/findDemandeCongeByFilter/{id}/{date}")
		public List<DemandeConge> findDemandeCongeByFilter(@PathVariable(required = false) int id,@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeCongeService.findDemandeCongeByFilter(id, date);
		}
		
		 //_______________________________________________________________________
		
		@GetMapping("/generateCongePdf/{id}")
		public ResponseEntity<byte[]> generateCongePdf(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader) {
		    DemandeConge demande = demandeCongeService.getDemandeCongeById(id);

		    if (demande == null) {
		        return ResponseEntity.notFound().build();
		    }

		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    try {
		        Document document = new Document();
		        PdfWriter.getInstance(document, outputStream);
		        document.open();
		        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		        String formattedDate = sdf.format(demande.getRequestDate());

		        // Add Company Logo
		        Image logo = Image.getInstance("src/main/resources/Images/logo.png");
		        logo.scaleToFit(50, 50);
		        logo.setAlignment(Element.ALIGN_CENTER);
		        document.add(logo);

		        // Add Title
		        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
		        Paragraph title = new Paragraph("DEMANDE DE CONGE", titleFont);
		        title.setAlignment(Element.ALIGN_CENTER);
		        document.add(title);

		        // Add Introductory Paragraph
		        Font introFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
		        Paragraph intro = new Paragraph("Le congé annuel doit être consommé entre le 01 Janvier et le 31 Décembre de chaque année. " +
		                "Si pour des raisons fonctionnelles le congé n’a pas été consommé durant l’année, les jours restant peuvent être reportées " +
		                "jusqu’au 31 mars de l’année suivante. Une demande de congé doit être soumise minimum 48 heures ouvrables avant la date de départ.", introFont);
		        intro.setAlignment(Element.ALIGN_CENTER);
		        intro.setSpacingBefore(20f);
		        document.add(intro);



			
		        Paragraph employeeTitle = new Paragraph("Le Salarié:", titleFont);
		        employeeTitle.setSpacingAfter(20f);
		        employeeTitle.setAlignment(Element.ALIGN_CENTER);
		        document.add(employeeTitle);

		        Paragraph employeeName = new Paragraph("Nom : " + demande.getEmployee().getName() + "    Prénom : " + demande.getEmployee().getLastName(), introFont);
		        employeeName.setSpacingAfter(20f);
		        employeeName.setAlignment(Element.ALIGN_LEFT);
		        document.add(employeeName);

		        Paragraph employeeDept = new Paragraph("Département : " + demande.getEmployee().getDepartement().getDepartementName(), introFont);
		        employeeDept.setSpacingAfter(20f);
		        employeeDept.setAlignment(Element.ALIGN_LEFT);
		        document.add(employeeDept);

		        Paragraph leaveTitle = new Paragraph("Nature du congé demandé:", titleFont);
		        leaveTitle.setSpacingAfter(20f);
		        leaveTitle.setAlignment(Element.ALIGN_CENTER);
		        document.add(leaveTitle);


		        Paragraph payeParag = new Paragraph("Type Congé(s)" + demande.getTypeConge() , introFont);
		        payeParag.setSpacingAfter(20f);
		        payeParag.setAlignment(Element.ALIGN_LEFT);
		        document.add(payeParag);



		        Paragraph congeDuration = new Paragraph("Congé du : " + sdf.format(demande.getStartDate()) + " au " + sdf.format(demande.getEndDate()), introFont);
		        congeDuration.setSpacingAfter(20f);
		        congeDuration.setAlignment(Element.ALIGN_LEFT);
		        document.add(congeDuration);

		        Paragraph returnDate = new Paragraph("Date de Reprise : " + sdf.format(demande.getReturnDate()), introFont);
		        returnDate.setSpacingAfter(20f);
		        returnDate.setAlignment(Element.ALIGN_LEFT);
		        document.add(returnDate);

		        Paragraph remplacement = new Paragraph("Remplacant : "+ demande.getRemplacant() != null ? demande.getRemplacant().getName() + " " + demande.getRemplacant().getLastName() : "", introFont);
		        remplacement.setSpacingAfter(20f);
		        remplacement.setAlignment(Element.ALIGN_LEFT);
		        document.add(remplacement);


		        Paragraph signatureDate = new Paragraph("Date : Tunis Le " + formattedDate, introFont);
		        signatureDate.setSpacingAfter(20f);
		        signatureDate.setAlignment(Element.ALIGN_LEFT);
		        document.add(signatureDate);


		        Paragraph signature = new Paragraph("Signature Du Salarié", introFont);
		        signature.setSpacingAfter(20f);
		        signature.setAlignment(Element.ALIGN_RIGHT);
		        document.add(signature);

		        Paragraph validationTitle = new Paragraph("VALIDATION : ", titleFont);
		        validationTitle.setSpacingAfter(20f);
		        validationTitle.setAlignment(Element.ALIGN_CENTER);
		        document.add(validationTitle);


		        Paragraph identity = new Paragraph("Identité", introFont);
		        identity.setSpacingAfter(20f);
		        identity.setAlignment(Element.ALIGN_LEFT);
		        document.add(identity);

		        Paragraph validSignature = new Paragraph("Signature", introFont);
		        validSignature.setSpacingAfter(20f);
		        validSignature.setAlignment(Element.ALIGN_LEFT);
		        document.add(validSignature);

		        Paragraph nPOne = new Paragraph("Le N + 1", introFont);
		        nPOne.setSpacingAfter(20f);
		        nPOne.setAlignment(Element.ALIGN_LEFT);
		        document.add(nPOne);

		        Paragraph serviceRh = new Paragraph("Le Service RH", introFont);
		        serviceRh.setSpacingAfter(20f);
		        serviceRh.setAlignment(Element.ALIGN_LEFT);
		        document.add(serviceRh);

		        Paragraph soldeCongeTitle = new Paragraph("Solde de Congé : (A remplir par les RH)", titleFont);
		        soldeCongeTitle.setSpacingAfter(20f);
		        soldeCongeTitle.setAlignment(Element.ALIGN_CENTER);
		        document.add(soldeCongeTitle);

		        Paragraph takenDays = new Paragraph("Nb de jours à prendre : " + demande.getEmployee().getTakenTimeOffDays(), introFont);
		        takenDays.setSpacingAfter(20f);
		        takenDays.setAlignment(Element.ALIGN_LEFT);
		        document.add(takenDays);

		        Paragraph remainingDays = new Paragraph("Solde de congé de départ : " + demande.getEmployee().getTimeOffDays(), introFont);
		        remainingDays.setSpacingAfter(20f);
		        remainingDays.setAlignment(Element.ALIGN_LEFT);
		        document.add(remainingDays);

		        document.close();
		    } catch (DocumentException | IOException e) {
		        e.printStackTrace();
		        return ResponseEntity.badRequest().body(null);
		    }

		    byte[] pdfBytes = outputStream.toByteArray();
		    return ResponseEntity.ok()
		            .header("Content-Disposition", "attachment; filename=demande_conge.pdf")
		            .contentType(MediaType.APPLICATION_PDF)
		            .body(pdfBytes);
		}
}
