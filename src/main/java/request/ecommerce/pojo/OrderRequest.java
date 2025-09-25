package request.ecommerce.pojo;

import java.util.List;

public class OrderRequest {
	private List<OrderDetails>orders;

	public List<OrderDetails> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDetails> orders) {
		this.orders = orders;
	}
}
