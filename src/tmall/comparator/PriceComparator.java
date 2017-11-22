package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class PriceComparator implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		
		return (int) (o1.getPromotePrice()-o2.getPromotePrice());
	}

}
