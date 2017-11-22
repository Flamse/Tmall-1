package tmall.bean;

public class User {
	private int id;
	private String name;
	private String password;

	// 返回匿名格式
	public String getAnonymousName() {
		if (null == name) {
			return null;
		} else if (name.length() <= 1) {
			return "*";
		} else if (name.length() == 2) {
			return name.substring(0, 1) + "*";
		}
		// 保留第一个和最后一个字符
		char[] cs = name.toCharArray();
		for (int i = 1; i < (cs.length - 1); i++) {
			cs[i] = '*';
		}
		return new String(cs);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
