package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerDay;
	private Double pricePerHour;
	
	private BrazilTaxServices taxService;

	public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxServices brazilTaxServices) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = brazilTaxServices;
	}
	
	public void processInvoice(CarRental carRental) {
		
		// O comando .getTime() pega o valor em milisegundos da data.
		long t1 = carRental.getStart().getTime();
		long t2 = carRental.getFinish().getTime();
		
		// Comando para fazer a diferença dos tempos em ms converter para h.
		double hours = (double)(t2 - t1) / 1000 / 60 / 60;
		
		double basicPayment;
		
		if(hours <= 12.0) basicPayment = Math.ceil(hours) * pricePerHour;
		else basicPayment = Math.ceil(hours / 24) * pricePerDay;
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}

}
