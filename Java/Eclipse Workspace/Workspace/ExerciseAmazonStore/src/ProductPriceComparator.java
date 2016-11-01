import java.util.Comparator;

public class ProductPriceComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		if(p1.getPrice()-p2.getPrice()>0){
			return 1;
		}
		if(p1.getPrice()-p2.getPrice()<0){
			return -1;
		}
		else{
			return 0;
		}
	}

}
