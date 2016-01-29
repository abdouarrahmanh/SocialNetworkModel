import java.util.List;
import junit.framework.TestCase;


public class NetworkTest extends TestCase {
	IUser u1, u2, u3, u4, u5, u6,u7,u8,u9,u10,u11,u12,u13;
	IGroup g1, g2, g3, g4;
	SocialNetwork sn;
	
	public void setUp() {
		u1 = new User();
		u2 = new User();
		u3 = new User();
		u4 = new User();
		u5 = new User();
		u6 = new User();
		u7 = new User();
		u8 = new User();
		u9 = new User();
		u10 = new User();
		u11 = new User();
		u12 = new User();
		u13 = new User();
		
		g1 = new Group();
		g2 = new Group();
		g3 = new Group();
		g4 = new Group();
		
		u1.setID("Watson");
		u2.setID("Holmes");
		u3.setID("lestrade");
		u4.setID("Lestrade");
		u5.setID("Hudson");
		u6.setID("Watson");
		u8.setID("Barton");
		u9.setID("Bradstreet");
		u10.setID("Forbes");
		u11.setID("Gregory");
		u12.setID("MacDonald");
		u13.setID("Patterson");
		
		g1.setID("Scotland Yard");
		g2.setID("Baker Street");
		g3.setID("Detectives");
		
		u1.follow(u2);
		u1.follow(u5);
		u2.follow(u1);
		u4.follow(u1);
		u4.follow(u2);
		u4.follow(u8);
		u4.follow(u9);
		u4.follow(u10);
		u5.follow(u1);
		u5.follow(u2);
		u5.follow(u4);
		u8.follow(u9);
		u8.follow(u10);
		u9.follow(u4);
		u9.follow(u8);
		u9.follow(u10);
		u9.follow(u11);
		u9.follow(u12);
		u10.follow(u4);
		u10.follow(u9);
		u10.follow(u11);
		u10.follow(u12);
		u10.follow(u13);
		
		u1.addToGroup(g2);
		u2.addToGroup(g2);
		u2.addToGroup(g3);
		u4.addToGroup(g1);
		u4.addToGroup(g3);
		u5.addToGroup(g2);
		u8.addToGroup(g1);
		u9.addToGroup(g1);
		u10.addToGroup(g1);
		u11.addToGroup(g1);
		u12.addToGroup(g1);
		u13.addToGroup(g1);
		
		sn = new SocialNetwork();
		
		sn.addUser(u1);
		sn.addUser(u2);
		sn.addUser(u3);
		sn.addUser(u4);
		sn.addUser(u5);
		sn.addUser(u6);
		sn.addUser(u8);
		sn.addUser(u9);
		sn.addUser(u10);
		sn.addUser(u11);
		sn.addUser(u12);
		sn.addUser(u13);
		
		sn.addGroup(g1);
		sn.addGroup(g2);
		sn.addGroup(g3);
	}
	
	public void testID() {
		u7.setID("Moriarty");
		g4.setID("Baker Street Irregulars");
		assertEquals(u7.getID(),"Moriarty");
		assertEquals(g4.getID(),"Baker Street Irregulars");
	}
	public void testCompareTo() {
		assertTrue(u1.compareTo(u6) == 0);
		assertTrue(u1.compareTo(u2) > 0);
		assertTrue(u1.compareTo(u3) < 0);
	}
	public void testEquals() {
		assertEquals(u1,u1);
		assertEquals(u1,u6);
		assertNotSame(u1,u2);
		assertNotSame(u3,u4);
	}
	public void testGetFollowedUsers() {
		assertTrue(u1.getFollowedUsers().contains(u2));
		assertFalse(u1.getFollowedUsers().contains(u4));
		assertEquals(u1.getFollowedUsers().size(),2);
	}
	public void testGetFollowedUsersInGroup() {
		assertTrue(u4.getFollowedUsersInGroup(g3).contains(u2));
		assertEquals(u4.getFollowedUsersInGroup(g3).size(),1);
	}
	public void testGetGroups() {
		assertEquals(u1.getGroups().size(),1);
		assertEquals(u2.getGroups().size(),2);
		assertTrue(u2.getGroups().contains(g2));
		assertTrue(u2.getGroups().contains(g3));
	}
	public void testToString() {
		assertEquals(u1.toString(),"Watson");
		assertEquals(u2.toString(),"Holmes");
		assertEquals(g1.toString(),"Scotland Yard");
	}
	public void testRecommendUsersToFollow() {
		List<IUser> rf = sn.recommendUsersToFollow(u4, 0.3);
		assertFalse(rf.contains(u10));
		assertFalse(rf.contains(u2));
		assertTrue(rf.contains(u13));
		
		assertTrue(rf.contains(u11));
		assertTrue(rf.contains(u12));
	}
}
