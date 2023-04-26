package jp.co.internous.ecsite.model.mapper;
/**ログインによってLoginFormに格納されるユーザ情報を条件にデータベース
 * 検索するためのDAO(MstUserMapper)を作成します。
 */
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import jp.co.internous.ecsite.model.domain.MstUser;
import jp.co.internous.ecsite.model.form.LoginForm;

@Mapper
public interface MstUserMapper {
	
	//データベースからユーザ情報を検索するために使用されます。
	//#{userName}と#{password}は、それぞれLoginFormクラスの
	//userNameとpasswordフィールドに対応しています。
	@Select(value="SELECT * FROM mst_user WHERE user_name = #{userName} and password = #{password}")
	//データベースからユーザ情報を検索する為に使用します
	MstUser findByUserNameAndPassword(LoginForm form);

	//count(id)をもとにuser_nameデータを抽出
	@Select(value="SELECT count(id) FROM mst_user WHERE user_name = #{userName}")
	//int findCountByUserName(String userName)は、データベーステーブルの列user_nameの値が
	//パラメーターuserNameの値と等しい行の数を表す整数値を返すJavaメソッド
	int findCountByUserName(String userName);

}
