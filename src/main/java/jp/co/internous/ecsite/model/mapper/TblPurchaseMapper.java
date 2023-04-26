package jp.co.internous.ecsite.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.internous.ecsite.model.dto.HistoryDto;

@Mapper
public interface TblPurchaseMapper {
	
	//userIdを引数にして、TblPurchaseテーブルからuserIdに対応する購入履歴を取得するJavaコードです。
	List<HistoryDto> findHistory(int userId);
	//引数として渡されたユーザーID、商品ID、商品名、商品数、合計金額をデータベースに挿入する関数です。
	//この関数は、データベースに新しい行を追加するために使用されます。
	int insert(int userId, int goodsId, String goodsName, int itemCount, int total);

}
