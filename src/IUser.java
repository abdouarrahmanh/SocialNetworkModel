import java.util.List;


public interface IUser extends Identifiable {

    public void follow(IUser user);

    public void unfollow(IUser user);

    public void addToGroup(IGroup group);

    public void removeFromGroup(IGroup group);

    public List<IUser> getFollowedUsers();

    public List<IGroup> getGroups();

    public List<IUser> getFollowedUsersInGroup(IGroup group);

    public void tweeted(String tweet);

    public String getLastTweet();

    public List<String> getTweetHistory();
}
