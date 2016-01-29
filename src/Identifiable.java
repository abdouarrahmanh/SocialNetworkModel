
public interface Identifiable extends Comparable<Identifiable> {

	public String getID();

	public void setID(String id);

	public int compareTo(Identifiable i);
}
