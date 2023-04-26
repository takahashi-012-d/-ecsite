package jp.co.internous.ecsite.model.form;

import java.io.Serializable;
import java.util.List;

public class Cartform implements Serializable{
	
	private int userId;
	//Cartという名前のクラスのインスタンスを格納するためのリストです。
	private List<Cart> cartList;
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public List<Cart> getCartList() {
		return cartList;
	}
	
	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

}
