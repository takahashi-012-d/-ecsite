package jp.co.internous.ecsite.model.dto;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import jp.co.internous.ecsite.model.domain.TblPurchase;

public class HistoryDto {
	
	private int id;
	private int userId;
	private int goodsId;
	private String goodsName;
	private int itemCount;
	private int total;
	private String createdAt;
	
	//Javaでクラスを作成する際に、デフォルトコンストラクタを定義するために使用されるJavaコードです。
	//デフォルトコンストラクタは、引数を持たないコンストラクタであり、クラスのインスタンスを作成するために使用されます。
	public HistoryDto() {}
	
	//Javaでクラスを作成する際に、引数としてTblPurchaseオブジェクトを受け取り、
	//HistoryDtoオブジェクトを作成するために使用されるJavaコードです。
	public HistoryDto(TblPurchase entity) {
		this.id = entity.getId();
		this.userId = entity.getUserId();
		this.goodsId = entity.getGoodsId();
		this.goodsName = entity.getGoodsName();
		this.itemCount = entity.getItemCount();
		this.total = entity.getTotal();
		
		//TblPurchaseオブジェクトの作成日時を取得するために使用されるJavaコードです。
		Timestamp d = entity.getCreatedAt();
		//Javaで日付をフォーマットするために使用されるJavaコードです。
		//このコードは、指定されたフォーマットで日付を文字列に変換するために使用されます。
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//TblPurchaseオブジェクトの作成日時をフォーマットして、文字列として保存するために使用されるJavaコードです。
		this.createdAt = f.format(d);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}

}
