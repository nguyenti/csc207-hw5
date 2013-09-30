package edu.grinnell.csc207.nguyenti.ushahidi;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Random;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiIncidentList;
import edu.grinnell.glimmer.ushahidi.UshahidiLocation;

public class UshahidiExtensions {

    
    public static void printIncident(PrintWriter pen, UshahidiIncident incident) {
	pen.println("Incident #: " + incident.getId());
	pen.println("  Title: " + incident.getTitle());
	pen.println("  Description: " + incident.getDescription());
	pen.println("  Date: " + (incident.getDate().get(Calendar.MONTH) + 1) + "/"
		+ incident.getDate().get(Calendar.DATE) + "/" + incident.getDate().get(Calendar.YEAR));
	pen.println("  Location: " + incident.getLocation());
	pen.println("  Status: (" + incident.getMode() + ", "
		+ incident.getActive() + ", " + incident.getVerified() + ")");
    }

    public static UshahidiIncidentList ushahidiIncidenttester() {
	UshahidiIncidentList incidents = new UshahidiIncidentList();
	Random rand = new Random();
	for (int i = 0; i < 12; i++) {
	    Calendar date = Calendar.getInstance();
	    date.set(rand.nextInt(3000), rand.nextInt(12) + 1,
		    rand.nextInt(28) + 1);
	    incidents.addIncident(new UshahidiIncident(i, "Clever title " + i,
		    date, new UshahidiLocation(Math.abs(rand.nextInt()), "Location " + i,
			    rand.nextInt(179) + rand.nextDouble(), rand
				    .nextInt(179) + rand.nextDouble()),
		    "Mmm, cheese."));
	}
	return incidents;
    }

    public static void lowHighId(UshahidiClient client) throws Exception {
	UshahidiIncident incident;
	UshahidiIncident high = client.nextIncident();
	UshahidiIncident low = high;
	while (client.hasMoreIncidents()) {
	    incident = client.nextIncident();
	    int id = incident.getId();
	    if (id > high.getId()) {
		high = incident;
	    } else if (id < low.getId()) {
		low = incident;
	    }
	}
	PrintWriter pen = new PrintWriter(System.out, true);
	printIncident(pen, high);
	printIncident(pen, low);
    }

}
