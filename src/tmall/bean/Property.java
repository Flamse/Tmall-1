package tmall.bean;

public class Property {
	private int id;
	// 获取property属性对象时直接包含category对象，而不用再使用id从数据库中查询。
	private Category category;
	private String name;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
