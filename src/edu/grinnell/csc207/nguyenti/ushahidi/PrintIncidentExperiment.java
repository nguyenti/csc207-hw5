package edu.grinnell.csc207.nguyenti.ushahidi;

import java.io.PrintWriter;
import java.util.Calendar;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiLocation;
import edu.grinnell.glimmer.ushahidi.UshahidiUtils;
import edu.grinnell.glimmer.ushahidi.UshahidiWebClient;

public class PrintIncidentExperiment {

	public static void main(String[] args) throws Exception {
		PrintWriter pen = new PrintWriter(System.out, true);
		
		Calendar lateDate = Calendar.getInstance();
		lateDate.set(2050, 12, 28);
		
		Calendar earlyDateFarmers = Calendar.getInstance();
		earlyDateFarmers.set(2024, 1, 1);
		
		Calendar earlyDateBurgers = Calendar.getInstance();
		earlyDateBurgers.set(2033, 1, 1);
		
		pen.println("+------------------------------------------------------+\n");
		pen.println("3 sample incidents");
		pen.println("\n+------------------------------------------------------+\n");

		// A few basic incidents
		UshahidiExtensions.printIncident(pen, UshahidiUtils.SAMPLE_INCIDENT);
		UshahidiExtensions.printIncident(pen, UshahidiUtils.randomIncident());
		UshahidiExtensions.printIncident(pen, UshahidiUtils.randomIncident());

		pen.println("\n+------------------------------------------------------+\n");
		pen.println("Our made up Incident");
		pen.println("\n+------------------------------------------------------+\n");
		
		// A newly created incident
		UshahidiIncident myIncident = new UshahidiIncident(42, "Life",
				lateDate, new UshahidiLocation(112358, "Galaxy"), "For Hitchhikers");
		UshahidiExtensions.printIncident(pen, myIncident);

		pen.println("\n+------------------------------------------------------+\n");
		pen.println("The first incident from a sample list");
		pen.println("\n+------------------------------------------------------+\n");

		// One from a list
		UshahidiClient client = UshahidiUtils.SAMPLE_CLIENT;
		UshahidiExtensions.printIncident(pen, client.nextIncident());

		pen.println("\n+------------------------------------------------------+\n");
		pen.println("The first incident from a web client");
		pen.println("\n+------------------------------------------------------+\n");
		
		// One that requires connecting to the server
		UshahidiClient farmersClient = new UshahidiWebClient("https://farmersmarket.crowdmap.com/");
		UshahidiExtensions.printIncident(pen, farmersClient.nextIncident());
		
		pen.println("\n+------------------------------------------------------+\n");
		pen.println("The lowest and Highest ID incidents from a test list");
		pen.println("\n+------------------------------------------------------+\n");

		// print the lowest and highest id incidents from our test list of incidents
		UshahidiExtensions.lowHighId(UshahidiExtensions.ushahidiIncidenttester());
		
		pen.println("\n+------------------------------------------------------+\n");
		pen.println("The lowest and highest ID incidents from burgermap.org");
		pen.println("\n+------------------------------------------------------+\n");
		
		// Make a webClient to burgermap.org and print the lowest and highest id incidents
		// from the incidents on the site.
		UshahidiClient burgerClient = new UshahidiWebClient("http://burgermap.org");
		UshahidiExtensions.lowHighId(burgerClient);
		
		pen.println("\n+------------------------------------------------------+\n");
		pen.println("The incidents on farmersmarket.crowdmap.com between 1/1/2024\nand 12/28/2050");
		pen.println("\n+------------------------------------------------------+\n");
		
		// Make another client to farmersmarket and then print all of the incidents with dates
		// falling between earlyDate and lateDate
		UshahidiClient farmersClient2 = new UshahidiWebClient("https://farmersmarket.crowdmap.com/");
		UshahidiExtensions.printWithin(farmersClient2, earlyDateFarmers, lateDate);
		
		pen.println("\n+------------------------------------------------------+\n");
		pen.println("The incidents on burgermap.org between 1/1/2033 and 12/28/2050");
		pen.println("\n+------------------------------------------------------+\n");
		
		// Make a new client to burgermap.org and make an array of all the incidents that fall
		// between earlyDate and lateDate
		UshahidiClient burgerClient2 = new UshahidiWebClient("http://burgermap.org");
		UshahidiIncident[] incidents = UshahidiExtensions.arrayWithin(burgerClient2, earlyDateBurgers, lateDate);

		// Print all of the incidents in the array that was just made in order to test for success
		for (int i = 0; i < incidents.length; i++) {
			UshahidiExtensions.printIncident(pen, incidents[i]);
		}
		
		pen.close();
	} // main(String[])
}
