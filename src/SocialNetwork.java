import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SocialNetwork {
	private List<Identifiable> users;
	private List<Identifiable> groups;
	
	public SocialNetwork() {
		users = new ArrayList<Identifiable>();
		groups = new ArrayList<Identifiable>();
		
	}
	
	public IGroup getGroup(String groupID) {
		return (IGroup)groups.get(findIndex(groupID,groups));
	}
	
	public int getUserCount() {
		return users.size();
	}
	
	public int getGroupCount() {
		return groups.size();
	}
	
	public IUser getUser(String userID) {
		return (IUser)users.get(findIndex(userID,users));
	}
	
	public IUser getUser(int i) {
		return (IUser)users.get(i);
	}
	
	public IGroup getGroup(int i) {
		return (IGroup)groups.get(i);
	}
	
	public void addGroups(List<IGroup> gs) {
		for (int i = 0; i < gs.size(); ++i) {
			this.addGroup(gs.get(i));
		}
	}
	
	public int addGroup(IGroup group) {
		if (groups.isEmpty()) {
			groups.add(group);
			return 0;
		} else {
			int i = findIndex(group.getID(),groups);
			groups.add(i,group);
			return i;
		}
	}
	
	public void addUsers(List<IUser> us) {
		for (int i = 0; i < us.size(); ++i) {
			this.addUser(us.get(i));
		}
	}
	
	public int addUser(IUser user) {
		if (users.isEmpty()) {
			users.add(user);
			return 0;
		} else {
			int i = findIndex(user.getID(),users);
			users.add(i,user);
			return i;
		}
	}

	public List<IUser> recommendUsersToFollow(IUser user, double minCoefficient) {

		List<IUser> potentialUserstoRecommend = new ArrayList<>();
		List<IUser> recommmendedUsers = new ArrayList<>();
		List<IUser> F = user.getFollowedUsers();
		List<IUser> FOF = new ArrayList<>();
		List<IUser> FOFsorted = new ArrayList<>();
		int userListSize = F.size();
		List<Integer> userCounts = new ArrayList<>();
		List<Integer> groupCounts = new ArrayList<>();
		int totalUsers = user.getFollowedUsers().size();


		for (int x =0; x < userListSize; x++){
			for (int i = 0; i < F.get(x).getFollowedUsers().size(); i++){
				FOF.add(F.get(x).getFollowedUsers().get(i));
			}
		}

		for (int i = 0; i < FOF.size(); i++)
		{
			IUser current = FOF.get(i);
			if (!user.equals(current) && !F.contains(current)) {
				FOFsorted.add(current);
			}
		}

		for (int y = 0; y<FOFsorted.size(); y++) {
			if (!potentialUserstoRecommend.contains(FOFsorted.get(y))) {
				potentialUserstoRecommend.add(FOFsorted.get(y));
				userCounts.add(1);
			} else {
				int pos = potentialUserstoRecommend.indexOf((FOFsorted.get(y)));
				userCounts.set(pos, userCounts.get(pos) + 1);
			}
		}


		for (int j = 0; j < potentialUserstoRecommend.size(); j++){
			for (int f = 0; f  < potentialUserstoRecommend.get(j).getGroups().size(); f++){
				int fig = user.getFollowedUsersInGroup(potentialUserstoRecommend.get(j).getGroups().get(f)).size();
				groupCounts.add(fig);
			}
		}

		for (int z = 0; z < userCounts.size(); z++){
			double g = (double) (groupCounts.get(z)*5)/totalUsers;

			double cur_coeff = ((double) userCounts.get(z) + g)/totalUsers;
			if (cur_coeff > minCoefficient){
				recommmendedUsers.add(potentialUserstoRecommend.get(z));
			}
		}
		return recommmendedUsers;
	}
	private static int findIndex(String id, List<Identifiable> list) {
		//binary search to add users in order
		int startIndex = 0;
		int endIndex = list.size();
		int midIndex;
		while (startIndex < endIndex) {
			midIndex = (startIndex + endIndex) / 2;
			Identifiable other = list.get(midIndex);
			//If someone accidently added  null to the list, it should go to the end.
			int compValue = other != null ? id.compareTo(other.getID()) : -1;
			if (compValue == 0) {
				//The user already exists in users list
				return midIndex;
			} else if (compValue > 0) {
				startIndex = midIndex + 1;
			} else {
				endIndex = midIndex;
			}
		}
		return endIndex;
	}
	
	public static void main(String[] args) {
		try {
			//set up our network
			SocialNetwork sn = new SocialNetwork();
			DataReader dr = new DataReader("network.dat");
			dr.readDataSet();
			sn.addUsers(dr.getUserList());
			sn.addGroups(dr.getGroupList());
			
			//process the twitter data
			TweetReader tr = new TweetReader("tweets.dat");
			while (tr.advance()) {
				sn.getUser(tr.getTweeterID()).tweeted(tr.getTweet());
			}
			
			//gather personal info
			IUser lt = sn.getUser("lorenterveen");
			List<IUser> friends = lt.getFollowedUsers();
			List<IGroup> groups = lt.getGroups();
			
			// print out user data
			System.out.format("The social network has %d users, and %d groups.\n", sn.getUserCount(), sn.getGroupCount());
			System.out.format("%s has %d friends and is in %d groups\n", lt, friends.size(), groups.size());
			
			System.out.println();
			
			for (IUser friend : friends) {
				System.out.format("%s => %s\n", friend, friend.getLastTweet());
			}
			
			System.out.println();
		} catch (Exception e) {
			// panic - and tell us about it
			e.printStackTrace();
		}
	}
}
