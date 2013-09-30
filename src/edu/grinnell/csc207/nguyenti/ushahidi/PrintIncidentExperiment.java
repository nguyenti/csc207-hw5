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
	Calendar date = Calendar.getInstance();
	date.set(2050, 12, 28);

	// A few basic incidents
	UshahidiExtensions.printIncident(pen, UshahidiUtils.SAMPLE_INCIDENT);
	UshahidiExtensions.printIncident(pen, UshahidiUtils.randomIncident());
	UshahidiExtensions.printIncident(pen, UshahidiUtils.randomIncident());

	// A newly created incident
	UshahidiIncident myIncident = new UshahidiIncident(42, "Life",
		date, new UshahidiLocation(112358, "Galaxy"), "For Hitchhikers");
	UshahidiExtensions.printIncident(pen, myIncident);

	// One from a list
	UshahidiClient client = UshahidiUtils.SAMPLE_CLIENT;
	UshahidiExtensions.printIncident(pen, client.nextIncident());

	// One that requires connecting to the server
	UshahidiClient webclient = new UshahidiWebClient("https://farmersmarket.crowdmap.com/");
	UshahidiExtensions.printIncident(pen, webclient.nextIncident());
	
	UshahidiExtensions.lowHighId(UshahidiExtensions.ushahidiIncidenttester());
	
	
    } // main(String[])
    
}
