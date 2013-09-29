package edu.grinnell.csc207.nguyenti.ushahidi;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Random;

import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiIncidentList;
import edu.grinnell.glimmer.ushahidi.UshahidiLocation;

public class UshahidiExtensions {

    public static void printIncident(PrintWriter pen, UshahidiIncident incident) {
	pen.println("Incident #: " + incident.getId());
	pen.println("  Title: " + incident.getTitle());
	pen.println("  Description: " + incident.getDescription());
	pen.println("  Location: " + incident.getLocation());
	pen.println("  Status: (" + incident.getMode() + ", "
		+ incident.getActive() + ", " + incident.getVerified() + ")");
    }

    public static UshahidiIncidentList ushahidiIncidenttester() {
	UshahidiIncidentList incidents = new UshahidiIncidentList();
	Calendar date = Calendar.getInstance();
	Random rand = new Random();
	for (int i = 0; i < 12; i++) {
	    date.set(rand.nextInt(3000), rand.nextInt(12) + 1,
		    rand.nextInt(28) + 1);
	    incidents.addIncident(new UshahidiIncident(i, "Clever title " + i, date,
		    new UshahidiLocation(rand.nextInt(), "Location " + i, rand
			    .nextInt(1000) + rand.nextDouble(), rand
			    .nextInt(1000) + rand.nextDouble()), "Mmm, cheese."));
	}
	return incidents;
    }
}
