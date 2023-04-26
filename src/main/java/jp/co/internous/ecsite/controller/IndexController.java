package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.ecsite.model.domain.MstGoods;
import jp.co.internous.ecsite.model.domain.MstUser;
import jp.co.internous.ecsite.model.dto.HistoryDto;
import jp.co.internous.ecsite.model.form.Cartform;
import jp.co.internous.ecsite.model.form.HistoryForm;
import jp.co.internous.ecsite.model.form.LoginForm;
import jp.co.internous.ecsite.model.mapper.MstGoodsMapper;
import jp.co.internous.ecsite.model.mapper.MstUserMapper;
import jp.co.internous.ecsite.model.mapper.TblPurchaseMapper;

@Controller
//localhost:8080/ecsite/のURLでアクセス出来るように
@RequestMapping("/ecsite")
public class IndexController {

	//MstGoodsを介してmst_goodsテーブルにアクセスするためのmapper
	@Autowired
	private MstGoodsMapper goodsMapper;

	//MstUserを介してmst_userテーブルにアクセスするためのmapper
	@Autowired
	private MstUserMapper userMapper;

	//TblPurchaseを介してTblPurchaseテーブルにアクセスするためのmapper
	@Autowired
	private TblPurchaseMapper purchaseMapper;

	//JavaオブジェクトをJSON形式に変換するために使用されます
	private Gson gson = new Gson();

	//トップページに遷移するメソッド
	@GetMapping("/")
	//goodsテーブルから取得した商品エンティティの一覧を、フロントに渡すModelに追加する
	public String index(Model model) {
		//List<MstGoods>オブジェクトに格納するためのメソッド
		//findAllはデータベーステーブルからすべての行を取得するために使用されるメソッドです
		List<MstGoods> goods = goodsMapper.findAll();
		//model.addAttributeメソッドで画面に渡したいデータをModelオブジェクトに追加
		//goodsという名前の属性が追加され、その値はgoodsという名前の変数
		model.addAttribute("goods", goods);

		//index.htmlに遷移する
		return "index";
	}

	//文字列そのものが返却される
	@ResponseBody
	//ログイン機能を追加
	@PostMapping("/api/login")
	public String loginApi(@RequestBody LoginForm f) {
		//mst_userテーブルからユーザ名とパスワードによって検索し、結果を取得
		MstUser user = userMapper.findByUserNameAndPassword(f);

		//ユーザー名がnullの時ゲストにする
		if (user == null) {
			//userをインスタンス化
			user = new MstUser();
			//userにゲストを代入
			user.setFullName("ゲスト");
		}

		//MstUser型のuserをJSON形式の文字列に変換し、画面側に返却
		return gson.toJson(user);
	}
	
	
	@ResponseBody
	//このメソッドは、指定されたURLパスに対してPOSTリクエストが送信された場合に呼び出される
	@PostMapping("/api/purchase")
	//リクエストボディから受信したJSONデータをCartformオブジェクトのfという変数名にして変換します
	public int purchaseApi(@RequestBody Cartform f) {

		//CartformオブジェクトのgetCartList()メソッドから取得したリストの各要素に対して、指定された処理を実行します
		//(引数)->{処理内容}のラムダ式
		f.getCartList().forEach((c) -> {
			//オブジェクトcの価格を取得するためのメソッド
			//オブジェクトcの価格と数を掛けた値をtotal変数に代入することを意味します。
			int total = c.getPrice() * c.getCount();
			//オブジェクトfのユーザーID、オブジェクトcのID、商品名、数、およびtotal変数をデータベースに挿入することを意味します。
			purchaseMapper.insert(f.getUserId(), c.getId(), c.getGoodsName(), c.getCount(), total);
		});

		//オブジェクトfのカートリストの要素数を返すことを意味します。
		return f.getCartList().size();
	}

	//購入履歴の表示を担うメソッド
	@ResponseBody
	@PostMapping("/api/history")
	//HTTP POSTリクエストを受信し、リクエストの内容を処理して応答を返すことができます。
	public String historyApi(@RequestBody HistoryForm f) {
		//HistoryFormオブジェクトのuserIdフィールドから値を取得し、int型の変数userIdに代入する
		int userId = f.getUserId();
		//List<HistoryDto> historyは、購入履歴を格納するために使用されるJavaオブジェクトです。
		//purchaseMapper.findHistory(userId)は、userIdを引数にして、購入履歴を取得するために使用される
		List<HistoryDto> history = purchaseMapper.findHistory(userId);

		//historyをJSON形式の文字列に変換し、画面側に返却
		return gson.toJson(history);
	}

}
