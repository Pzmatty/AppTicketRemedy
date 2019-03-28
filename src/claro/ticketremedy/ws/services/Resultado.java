package claro.ticketremedy.ws.services;

import java.util.ArrayList;
import java.util.List;

public class Resultado {

	private List<Entry> entries;

	public Resultado() {
		entries = new ArrayList<Entry>();
	}

	public void addEntry(Entry entry) {
		entries.add(entry);
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (entries != null) {
			sb.append("Resultado=");
			if (entries.isEmpty()) {
				sb.append("La consulta no recupera registros.");
			} else {
				int i = 0;
				for (Entry e : entries) {
					if(i==0)
					  sb.append("Entry #" + ++i + e.toString());
					else
					  sb.append(",\n Entry #" + ++i + e.toString());
				}
			}
		}
		return sb.toString();
	}
}
