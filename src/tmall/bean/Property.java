package tmall.bean;

public class Property {
	private int id;
	// ��ȡproperty���Զ���ʱֱ�Ӱ���category���󣬶�������ʹ��id�����ݿ��в�ѯ��
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
