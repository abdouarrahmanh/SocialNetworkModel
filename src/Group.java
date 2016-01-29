import java.util.ArrayList;
import java.util.List;

public class Group implements IGroup {
    public List<IUser> groupList = new ArrayList<>();
    public String id;

    public Group(){}

    public String toString(){
        return id;
    }

    @Override
    public List<IUser> getUsers() {
        return this.groupList;
    }

    @Override
    public void addUser(IUser user) {
        groupList.add(user);
    }

    @Override
    public void removeUser(IUser user) {
        groupList.remove(user);
    }

    @Override
    public int getUserCount() {
        return groupList.size();
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(Identifiable i) {
        int uVal = 0;
        User u = (User) i;
        if (i instanceof User) {
            if (this.getID().compareTo(u.getID()) > 0) {
                uVal = 1;
            }
            if (this.getID().compareTo(u.getID()) == 0) {
                uVal = 0;
            } else if (this.getID().compareTo(u.getID()) < 0) {
                uVal = -1;
            }
        }
        return uVal;
    }
}
