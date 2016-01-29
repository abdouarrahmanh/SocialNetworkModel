import java.util.List;


public interface IGroup extends Identifiable {

	public List<IUser> getUsers();

	public void addUser(IUser user);

	public void removeUser(IUser user);

	public int getUserCount();
}
