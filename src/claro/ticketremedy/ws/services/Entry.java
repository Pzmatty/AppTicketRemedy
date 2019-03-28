package claro.ticketremedy.ws.services;

import java.util.ArrayList;
import java.util.List;

public class Entry {
	
	private List<Field> fields;
    
	public Entry(){
		fields = new ArrayList<Field>();
	}
	
	public void addField(Field field) {
		fields.add(field);
	}
	
	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (fields != null) {
			for (Field f : fields) {
				sb.append("; " + f.toString());
			}
		}
		return sb.toString();
	}

}
