import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataReader {
	boolean[][] friendsMatrix;
	List<IUser> userList;
	List<IGroup> groupList;
	FileInputStream fis;
	
	/* 
	 * File format
	 * int for number of users (U)
	 * int for number of groups (G)
	 * list of null terminated userid's, ordered to match matrix
	 * list of null terminated groupid's, ordered to match matrix
	 * matrix of users mapped to groups U x G
	 * laplacian matrix of friendships U x U
	 */
	
	public DataReader(String fileName) throws FileNotFoundException {
		
		fis = new FileInputStream(fileName);
		userList = new ArrayList<IUser>();
		groupList = new ArrayList<IGroup>();
	}
	
	public void readDataSet() throws IOException {
		int numUsers = readInt();
		int numGroups = readInt();
		readUserIds(numUsers);
		readGroupIds(numGroups);
		readGroupMatrix();
		readUserMatrix();
		fis.close();
	}
	
	public boolean close() {
		try {
			fis.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public List<IUser> getUserList() {
		return userList;
	}
	
	public List<IGroup> getGroupList() {
		return groupList;
	}
	
	protected void readUserMatrix() throws IOException {
		for (int i = 0; i < userList.size(); ++i) {
			boolean[] row = readBooleanArray(userList.size());
			for (int j = 0; j < row.length; ++j) {
				if (row[j]) {
					userList.get(i).follow(userList.get(j));
				}
			}
		}
	}
	
	protected void readGroupMatrix() throws IOException {
		for (int i = 0; i < groupList.size(); ++i) {
			boolean[] row = readBooleanArray(userList.size());
			for (int j = 0; j < row.length; ++j) {
				if (row[j]) {
					userList.get(j).addToGroup(groupList.get(i));
				}
			}
		}
	}
	
	protected void readUserIds(int numElements) throws IOException {
		int c;
		String id;
		for (int i = 0; i < numElements; ++i) {
			id = "";
			while ((c = fis.read()) != '\0') {
				id += (char)c;
			}
			IUser user = new User();
			user.setID(id);
			userList.add(user);
		}
	}

	protected void readGroupIds(int numElements) throws IOException {
		int c;
		String id;
		for (int i = 0; i < numElements; ++i) {
			id = "";
			while ((c = fis.read()) != 0) {
				id += (char)c;
			}
			IGroup group = new Group();
			group.setID(id);
			groupList.add(group);
		}
	}
	
	protected boolean[] readBooleanArray(int size) throws IOException {
		int byteSize = (size + 7) /8;
		boolean[] barray = new boolean[size];
		byte[] b = new byte[byteSize];
		fis.read(b);
		for (int i = 0; i < byteSize; ++i) {
			for (int j = 0; j < 8 && i * 8 + j < size; ++j) {
				barray[(i*8)+j] = (b[i] & (1 << j)) != 0;
			}
		}
		return barray;
	}
	
	protected int readInt() throws IOException {
		byte[] b = new byte[4];
		fis.read(b);
		int result = 0;
		for (byte i = 0; i < b.length; ++i) {
			result += b[i] << (i*8);
		}
		return result;
	}
}
