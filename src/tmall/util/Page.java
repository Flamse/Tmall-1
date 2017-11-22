package tmall.util;

public class Page {

	int start;
	int count;
	int total;
	String param;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	// �ж��Ƿ���ǰһҳ
	public boolean isHasPreviousPage() {
		if (start == 0) {
			return false;
		}
		return true;
	}

	// �ж��Ƿ��к�һҳ
	public boolean isHasNextPage() {
		int totalPage = getTotalPage();
		if ((start / count) + 1 == totalPage) {
			return false;
		}
		return true;
	}

	// ��ȡ��ҳ��
	public int getTotalPage() {
		if (total / count == 0) {
			return 1;
		}

		int rs = total % count;
		if (rs == 0) {
			return total / count;
		} else {
			return (total / count) + 1;
		}
	}

	// ��ȡ���һҳ�Ŀ�ʼ�±�
	public int getLastStart() {
		int totalPage = getTotalPage();
		return count * (totalPage - 1);
	}

	public Page() {
		super();
	}

	public Page(int start, int count) {
		super();
		this.start = start;
		this.count = count;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
