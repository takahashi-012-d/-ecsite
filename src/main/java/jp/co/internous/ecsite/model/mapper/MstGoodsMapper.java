//全商品をmst_goodsテーブルから検索するためのDAO
package jp.co.internous.ecsite.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.internous.ecsite.model.domain.MstGoods;

@Mapper
public interface MstGoodsMapper {
	
	//mst_goodsテーブルからすべてのカラムを取得するためのSQL文
	@Select(value="SELECT * FROM mst_goods")
	//MstGoodsテーブルのレコードを取得するためのメソッド
	List<MstGoods> findAll();
	
	//mst_goodsテーブルの商品名と値段を参照して
	//テーブルに新しい行を追加する際に、その行の各列に挿入する値を指定する部分
	@Insert("INSERT INTO mst_goods (goods_name, price) VALUES (#{goodsName}, #{price})")
	//DB側で自動採番されるよう設定
	@Options(useGeneratedKeys=true, keyProperty="id")
	//MstGoodsはJavaオブジェクトであり、goodsがそのオブジェクトに対応します1
	int insert(MstGoods goods);
	
	//mst_goodsテーブルの削除したいid(変数)を選択し削除する
	@Update("DELETE FROM mst_goods WHERE id = #{id}")
	//このメソッドは、引数で指定したIDに対応するエンティティを削除する
	int deleteByid(long id);
	
}
