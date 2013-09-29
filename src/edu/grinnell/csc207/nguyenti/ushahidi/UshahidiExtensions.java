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
	pen.println("  Date: " + incident.getDate().get(Calendar.MONTH) + "/"
		+ incident.getDate().get(Calendar.DATE) + "/" + incident.getDate().get(Calendar.YEAR));
	pen.println("  Location: " + incident.getLocation());
	pen.println("  Status: (" + incident.getMode() + ", "
		+ incident.getActive() + ", " + incident.getVerified() + ")");
    }

    public static UshahidiIncidentList ushahidiIncidenttester() {
	UshahidiIncidentList incidents = new UshahidiIncidentList();
	Calendar date = Calendar.getInstance();
	Random rand = new Random(123456789);
	for (int i = 0; i < 12; i++) {
	    date.set(rand.nextInt(3000), rand.nextInt(12) + 1,
		    rand.nextInt(28) + 1);
	    incidents.addIncident(new UshahidiIncident(i, "Clever title " + i,
		    date, new UshahidiLocation(rand.nextInt(), "Location " + i,
			    rand.nextInt(180) + rand.nextDouble(), rand
				    .nextInt(180) + rand.nextDouble()),
		    "Mmm, cheese."));
	    //rand.setSeed(i*10000);
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
