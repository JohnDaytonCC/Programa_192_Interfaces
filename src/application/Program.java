/* Programa para resolver a seguinte questão:
 * Uma locadora brasileira de carros cobra um valor por hora para locações de até 
 * 12 horas. Porém, se a duração da locação ultrapassar 12 horas, a locação será 
 * cobrada com base em um valor diário. Além do valor da locação, é acrescido no 
 * preço o valor do imposto conforme regras do país que, no caso do Brasil, é 20% 
 * para valores até 100.00, ou 15% para valores acima de 100.00. Fazer um 
 * programa que lê os dados da locação (modelo do carro, instante inicial e final da 
 * locação), bem como o valor por hora e o valor diário de locação. O programa 
 * deve então gerar a nota de pagamento (contendo valor da locação, valor do 
 * imposto e valor total do pagamento) e informar os dados na tela.
 */
package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxServices;
import model.services.RentalService;

public class Program {

	public static void main(String[] args) throws ParseException{
		
		Locale.setDefault(Locale.US);
		Scanner input = new Scanner(System.in);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:ss");
		
		System.out.println("Enter rental data");
		System.out.print("Car model: ");
		String carModel = input.nextLine();
		System.out.print("Pickup (dd/MM/yyyy hh:ss): ");
		Date start = sdf.parse(input.nextLine());
		System.out.print("Return (dd/MM/yyyy hh:ss): ");
		Date finish = sdf.parse(input.nextLine());
		
		CarRental cr = new CarRental(start, finish, new Vehicle(carModel));
		
		System.out.print("Enter price per hour: ");
		double pricePerHour = input.nextDouble();
		System.out.print("Enter price per day: ");
		double pricePerDay = input.nextDouble();
		
		RentalService rentalService = new RentalService(pricePerDay, pricePerHour, new BrazilTaxServices());
		
		rentalService.processInvoice(cr);
		
		System.out.println("INVOICE: ");
		System.out.println("Basic payment: " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
		System.out.println("Tax: " + String.format("%.2f", cr.getInvoice().getTax()));
		System.out.println("Total payment: " + String.format("%.2f", cr.getInvoice().getTotalPayment()));
		
		input.close();
	}

}
