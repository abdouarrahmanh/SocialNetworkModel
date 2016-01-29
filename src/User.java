import java.util.ArrayList;
import java.util.List;

public class User implements IUser {
    public List<IUser> userList = new ArrayList<>();
    public List<IGroup> usersGroups = new ArrayList<>();
    public List<String> tweetList = new ArrayList<>();
    public String id;


    public User() {
    }

    public String toString() {
        return id;
    }


    public String getID() {

        return id;
    }

    public void setID(String id) {

        this.id = id;
    }


    @Override
    public void follow(IUser user) {
        userList.add(user);
    }

    @Override
    public void unfollow(IUser user) {
        userList.remove(user);
    }

    @Override
    public void addToGroup(IGroup group) {
        boolean inside = false;
        boolean inside2 = false;
        for(int x = 0; x < group.getUserCount(); x++){
            if(group.getUsers().get(x).equals(this)){
                inside = true;
            }
        }
        for(int x = 0; x < usersGroups.size(); x++){
            if(usersGroups.get(x).equals(group)){
                inside2 = true;
            }
        }

        if(inside == false){
            group.addUser(this);
        }
        if(inside2 == false) {
            usersGroups.add(group);
        }
    }

    @Override
    public void removeFromGroup(IGroup group) {
        group.removeUser(this);
        usersGroups.remove(group);
    }

    @Override
    public List<IUser> getFollowedUsers() {

        return userList;

    }

    @Override
    public List<IGroup> getGroups() {
        return usersGroups;
    }


    @Override
    public List<IUser> getFollowedUsersInGroup(IGroup group) {
        List<IUser> FollowedInGroup = new ArrayList<>();
        for (int i = 0; i < group.getUserCount(); i++) {
            for (int k = 0; k < this.userList.size(); k++) {
                if (group.getUsers().get(i).equals(this.userList.get(k))) {
                    FollowedInGroup.add(userList.get(k));
                }
            }
        }
        return FollowedInGroup;
    }

    @Override
    public void tweeted(String tweet) {
        tweetList.add(tweet);
    }

    @Override
    public String getLastTweet() {
        return tweetList.get(tweetList.size() - 1);
    }

    @Override
    public List<String> getTweetHistory() {
        return tweetList;
    }

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

    @Override
    public boolean equals(Object other) {
        if ((other instanceof User) && (this.getID() == ((User) other).getID())) {
            return true;
        } else {
            return false;
        }
    }
}
