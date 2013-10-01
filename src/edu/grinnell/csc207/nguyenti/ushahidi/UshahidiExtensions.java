package edu.grinnell.csc207.nguyenti.ushahidi;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiIncidentList;
import edu.grinnell.glimmer.ushahidi.UshahidiLocation;

public class UshahidiExtensions {

	public static void printIncident(PrintWriter pen, UshahidiIncident incident) {
		pen.println("Incident #: " + incident.getId());
		pen.println("  Title: " + incident.getTitle());
		pen.println("  Description: " + incident.getDescription());
		pen.println("  Date: " + (incident.getDate().get(Calendar.MONTH) + 1)
				+ "/" + incident.getDate().get(Calendar.DATE) + "/"
				+ incident.getDate().get(Calendar.YEAR));
		pen.println("  Location: " + incident.getLocation());
		pen.println("  Status: (" + incident.getMode() + ", "
				+ incident.getActive() + ", " + incident.getVerified() + ")");
	}

	public static UshahidiIncidentList ushahidiIncidenttester() {
		UshahidiIncidentList incidents = new UshahidiIncidentList();
		for (int i = 0; i < 12; i++) {
			Calendar date = Calendar.getInstance();
			date.set(3000 - (150 * i), i + 1,
					28 - (2 * i));
			incidents.addIncident(new UshahidiIncident(i, "Clever title " + i,
					date, new UshahidiLocation(i,
							"Location " + i, i * 10.5, 180 - (3 * i)), "Mmm, cheese."));
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

	public static void printWithin(UshahidiClient client, Calendar start,
			Calendar end) throws Exception {
		PrintWriter pen = new PrintWriter(System.out, true);
		while (client.hasMoreIncidents()) {
			UshahidiIncident incident = client.nextIncident();
			if (incident.getDate().compareTo(start) >= 0
					&& incident.getDate().compareTo(end) <= 0) {
				printIncident(pen, incident);
			}// if
		}// while
	}// printWithin

	public static UshahidiIncident[] arrayWithin(UshahidiClient client,
			Calendar start, Calendar end) throws Exception {
		UshahidiIncident[] hack = new UshahidiIncident[1];		
		ArrayList<UshahidiIncident> incidentsWithin = new ArrayList<UshahidiIncident>();
		
		while (client.hasMoreIncidents()) {
			UshahidiIncident test = client.nextIncident();
			if (test.getDate().compareTo(start) >= 0
					&& test.getDate().compareTo(end) <= 0) {
				incidentsWithin.add(test);
			}// if
		}// for
		
		return incidentsWithin.toArray(hack);
	}

}
