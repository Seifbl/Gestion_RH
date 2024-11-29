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

import com.fl2.gd.Entities.DemandeAttestationTravail;
import com.fl2.gd.Services.DemandeAttestationTravailService;
import com.fl2.gd.Services.EmployeeService;
import com.fl2.gd.Services.JwtService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


@RestController
@RequestMapping("/demandes-attestation-travail")
@CrossOrigin("*")
public class DemandeAttestationTravailController {

	@Autowired
	 private JwtService jwtService;
	 @Autowired
	 private DemandeAttestationTravailService demandeAttestationTravailService;
	 @Autowired
	 private EmployeeService  employeeService;
	 
		@GetMapping("/getAllDemandesAvanceSalaire")
		public List<DemandeAttestationTravail> getAllDemandesAttestationTravail(@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.getAllDemandesAttestationTravail();
		}
		@GetMapping("/getDemandeAttestationTravailById/{id}")
		public DemandeAttestationTravail getDemandeAttestationTravailById(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.getDemandeAttestationTravailById(id);
		}
		@PostMapping("/addDemandeAttestationTravail/{employeeId}")
		public void addDemandeAttestationTravail(@PathVariable int employeeId, @RequestBody DemandeAttestationTravail demandeAttestationTravail, @RequestHeader(name = "Authorization") String authorizationHeader) {
			
			DemandeAttestationTravail newDemande = demandeAttestationTravail;
			newDemande.setEmployee(employeeService.getEmployeeById(employeeId));			
			demandeAttestationTravailService.saveDemandeAttestationTravail(newDemande);
		}
		
		@PutMapping("/editDemandeAttestationTravail")
		public void editDemandeAttestationTravail(@RequestBody DemandeAttestationTravail demandeAttestationTravail, @RequestHeader(name = "Authorization") String authorizationHeader) {
			demandeAttestationTravailService.saveDemandeAttestationTravail(demandeAttestationTravail);
		}
		
		@DeleteMapping("/deleteDemandeAttestationTravailById/{id}")
		public void deleteDemandeAttestationTravail(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader) {
			demandeAttestationTravailService.deleteDemandeAttestationTravail(id);
		}
		
		@GetMapping("/getAllRecentDemandesAttestationTravail")
		public List<DemandeAttestationTravail> getAllRecentDemandesAttestationTravail(@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.getAllRecentDemandesAttestationTravail();
		}
		@GetMapping("/checkIfEmployeeHasUntreatedRequest/{id}")
		public DemandeAttestationTravail checkIfEmployeeHasUntreatedRequest(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.checkIfEmployeeHasUntreatedRequest(id);
		}
		
		@GetMapping("/findDemandeAttestationTravailByEmployeeId/{employeeId}")
		public List<DemandeAttestationTravail> findDemandeAttestationTravailByEmployeeId(@PathVariable int employeeId, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.findDemandeAttestationTravailByEmployeeId(employeeId);
		}
		
		@GetMapping("/findDemandeAttestationTravailByDepartementId/{departementId}")
		public List<DemandeAttestationTravail> findDemandeAttestationTravailByDepartementId(@PathVariable int departementId, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.findDemandeAttestationTravailByDepartementId(departementId);
		}
		
		@GetMapping("/findDemandeAttestationTravailForChefDepartement/{departementId}")
		public List<DemandeAttestationTravail> findDemandeAttestationTravailForChefDepartement(@PathVariable int departementId, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.findDemandeAttestationTravailForChefDepartement(departementId);
		}
		
		@GetMapping("/findDemandeAttestationTravailForAdmin/{departementId}")
		public List<DemandeAttestationTravail> findDemandeAttestationTravailForAdmin(@PathVariable int departementId, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.findDemandeAttestationTravailForAdmin(departementId);
		}
		@GetMapping("/findAllRecentDemandesAttestationTravailForAdmin")
		public List<DemandeAttestationTravail> findAllRecentDemandesAttestationTravailForAdmin( @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.findAllRecentDemandesAttestationTravailForAdmin();
		}

		 //_______________________________________________________________________
		
		@GetMapping("/findFinalisedDemandeAttestationTravailByDepartementId/{departementId}")
		public List<DemandeAttestationTravail> findFinalisedDemandeAttestationTravailByDepartementId(@PathVariable int departementId, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.findFinalisedDemandeAttestationTravailByDepartementId(departementId);
		}
		@GetMapping("/findFinalisedDemandeAttestationTravailByEmployeeId/{employeeId}")
		public List<DemandeAttestationTravail> findFinalisedDemandeAttestationTravailByEmployeeId(@PathVariable int employeeId, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.findFinalisedDemandeAttestationTravailByEmployeeId(employeeId);
		}
		@GetMapping("/findFinalisedDemandeAttestationTravail")
		public List<DemandeAttestationTravail> findFinalisedDemandeAttestationTravail(@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.findFinalisedDemandeAttestationTravail();
		}
		@GetMapping("/findDemandeAttestationTravailByFilter/{id}/{date}")
		public List<DemandeAttestationTravail> findDemandeAttestationTravailByFilter(@PathVariable(required = false) int id,@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAttestationTravailService.findDemandeAttestationTravailByFilter(id, date);
		}
		
		 //_______________________________________________________________________

		@GetMapping("/generatePdf/{id}")
		public ResponseEntity<byte[]> generatePdf(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader) {
		    DemandeAttestationTravail demande = demandeAttestationTravailService.getDemandeAttestationTravailById(id);

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

		        Paragraph dateParagraph = new Paragraph("Date de livraison: " + formattedDate);
		        dateParagraph.setSpacingAfter(20f);
		        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
		        document.add(dateParagraph);

		        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
		        Paragraph title = new Paragraph("Attestation de Travail", titleFont);
		        title.setSpacingAfter(40f);
		        title.setAlignment(Element.ALIGN_CENTER);
		        document.add(title);

		        Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 20);
		        Paragraph content = new Paragraph();
		        content.setFont(contentFont);
		        content.add("Je soussigné Flen Fleni Directeur Général de la société IKSON atteste par la présente que " 
		                    + demande.getEmployee().getName() + " " + demande.getEmployee().getLastName() 
		                    + " titulaire de la carte d’identité N°" + demande.getEmployee().getCin() 
		                    + " fait partie des employés titulaires de notre société en qualité de " 
		                    + demande.getEmployee().getFunction() + ", et ce depuis le " 
		                    + sdf.format(demande.getEmployee().getJoinDate()) + " jusqu'à ce jour.\n\n");
		        content.add("La présente attestation est délivrée à la demande de l'intéressé(e) pour " 
		                    + demande.getDemandeReason() + ".");
		        content.setLeading(30f);
		        content.setSpacingAfter(40f);
		        document.add(content);
		       

		        Paragraph signature = new Paragraph("Directeur Général\nFlen Fleni", contentFont);
		        signature.setAlignment(Element.ALIGN_RIGHT);
		        signature.setSpacingAfter(20f);
		        document.add(signature);
		        Image cache = Image.getInstance("src/main/resources/Images/cache.png");
		        cache.scaleToFit(100, 50);
		        cache.setAlignment(Element.ALIGN_RIGHT);
		        document.add(cache);

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
	    
}
