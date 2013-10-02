/*
 * UshahidiExtensions, siomple extensions for printing/gathering various Ushahidi incidents
 * @author Daniel Goldstein
 * @author Mark Lewis
 * @author Tiffany Nguyen
 * @author Earnest Wheeler
 */

package edu.grinnell.csc207.nguyenti.ushahidi;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiIncidentList;
import edu.grinnell.glimmer.ushahidi.UshahidiLocation;

public class UshahidiExtensions {
	/*
	 * Writes the incident number, title, description, date, location, and status of an incident to the
	 * location given by the printwriter.
	 * It is written in a nicely formatted block 
	 */
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
	} // printIncident
	/*
	 * Creates a list of Ushahidi incidents of twelve constructed incidents.
	 * Primarily used for testing other methods.
	 */
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
	} // ushahidiIncidenttester
	/*
	 * Given an ushahidi client, prints the incidents from the client with the
	 * lowest and highest incident IDs
	 */
	public static void lowHighId(UshahidiClient client) throws Exception {
		UshahidiIncident incident;
		UshahidiIncident high = client.nextIncident();
		UshahidiIncident low = high;
		// iterate through all incidents, finding highest and lowest
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
	/*
	 * Given a client and two dates, prints all the incidents in the client
	 * within the two dates.
	 * For any incidents to be printed, the start date must be before the 
	 * end date.
	 */
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
	/*
	 * Given a client and two dates, returns an array of type 
	 * UshahidiIncident containing all of the incidents within
	 * the client that occured within the two dates.
	 * For the array returned to be nonempty, the start date must be
	 * before the end date.
	 */
	public static UshahidiIncident[] arrayWithin(UshahidiClient client,
			Calendar start, Calendar end) throws Exception {
		UshahidiIncident[] hack = new UshahidiIncident[1];
		// Use an arraylist for variable length
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
