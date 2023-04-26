package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.ecsite.model.domain.MstGoods;
import jp.co.internous.ecsite.model.domain.MstUser;
import jp.co.internous.ecsite.model.form.GoodsForm;
import jp.co.internous.ecsite.model.form.LoginForm;
import jp.co.internous.ecsite.model.mapper.MstGoodsMapper;
import jp.co.internous.ecsite.model.mapper.MstUserMapper;

@Controller
@RequestMapping("/ecsite/admin")
public class AdminController {
	//ログイン認証機能と、フロントエンドに返すオブジェクトをmodelに追加する処理
	@Autowired
	private MstUserMapper userMapper;
	
	@Autowired
	private MstGoodsMapper goodsMapper;
	
	@RequestMapping("/")
	public String index () {
		return "admintop";
	}

	//ブラウザからLoginFormを介してユーザ情報を受け取り、
	//ログイン後のページに遷移させるメソッド
	@PostMapping("/welcome")
	//ブラウザで入力された項目が自動的にFormクラスの各フィールドに格納される
	//引数として渡されたLoginFormオブジェクトとModelオブジェクトを使用して、
	//ユーザーをウェルカムページにリダイレクトするための関数です。
	public String welcome(LoginForm form, Model model) {
		
		//引数として渡されたLoginFormオブジェクトを使用して、ユーザー名とパスワードを検索し、
		//データベースからユーザーオブジェクトを取得するための関数です。
		//MstUserオブジェクトを宣言し、変数userに割り当てるためのコードです。
		//findByUserNameAndPassword関数を呼び出し、その戻り値を変数userに割り当てるためのコードです。
		MstUser user = userMapper.findByUserNameAndPassword(form);
		
		//このコードは、変数userがnullである場合に、if文の中の処理を実行するために使用されます。
		if(user == null) {
			//エラーメッセージを表示するためのコードです。
			//Modelオブジェクトに属性を追加するために使用されます。
			//「add」は、英語で「加える」という意味があります。
			//「Attribute」は、英語で「属性」という意味があります。
			model.addAttribute("errMessage","ユーザー名またはパスワードが違います。");
			//このコードは、指定されたURLにリダイレクトするために使用されます。
			return "forward:/ecsite/admin/";
		}
		
		//このコードは、変数userが管理者でない場合に、if文の中の処理を実行するために使用されます。
		if(user.getIsAdmin() == 0) {
			model.addAttribute("errMessage","管理者ではありません。");
			return "forward:/ecsite/admin/";
		}
		//goodsMapperというオブジェクトのfindAll()メソッドを呼び出して、MstGoodsという型のリストを取得しています。
		//このリストには、goodsMapperが保持するすべてのMstGoodsオブジェクトが含まれます。
		List<MstGoods> goods =goodsMapper.findAll();
		//ModelというオブジェクトにuserNameという属性を追加し、その属性にuser.getUserName()の値を設定しています。
		//Modelは、JavaのWebアプリケーションで、データオブジェクトを表すために使用されます
		model.addAttribute("userName",user.getUserName());
		//Modelというオブジェクトにpasswordという属性を追加し、その属性にuser.getPassword()の値を設定しています。
		model.addAttribute("password",user.getPassword());
		//Modelというオブジェクトにgoodsという属性を追加し、その属性にgoodsの値を設定しています。
		model.addAttribute("goods",goods);
		
		//welcome.htmlに遷移させる
		return "welcome";
	}
	
	@PostMapping("/goodsMst")
	//goodsMstというメソッドを定義しています。このメソッドは、LoginFormというクラスのオブジェクトfとModelというオブジェクトmを引数に取り、String型の戻り値を返します。
	//このメソッドは、Spring Bootのセッション属性を使用するために使用されます
	public String goodsMst(LoginForm f, Model m) {
		//ModelというオブジェクトmにuserNameという属性を追加し、その属性にf.getUserName()の値を設定しています。
		m.addAttribute("userName",f.getUserName());
		//Modelというオブジェクトmにpasswordという属性を追加し、その属性にf.getPassword()の値を設定しています。
		m.addAttribute("password",f.getPassword());
		//goodsMstというメソッドの戻り値を"goodsMst"に設定しています。
		//このメソッドは、Spring Bootのセッション属性を使用するために使用されます
		return "goodsMst";
	}
	
	@PostMapping("/addGoods")
	//addGoodsメソッドは、引数としてGoodsFormとLoginFormを受け取り、Modelを返すJavaのメソッドです。
	//このメソッドは、商品情報を登録するために使用されます。
	//引数のGoodsFormは、商品情報を保持するために使用されます。
	//LoginFormは、ログイン情報を保持するために使用されます。
	//Modelは、ビューに渡すデータを保持するために使用されます。
	public String addGoods(GoodsForm goodsForm, LoginForm loginForm, Model m) {
		//m.addAttributeは、Modelに属性を追加するためのJavaのメソッドです。
		//この場合、"userName"という属性が追加され、loginForm.getUserName()の値が設定されます。
		//これにより、ビューでuserNameを使用してログインユーザー名を表示できます。
		m.addAttribute("userName",loginForm.getUserName());
		//この場合、"password"という属性が追加され、loginForm.getPassword()の値が設定されます。
		//これにより、ビューでpasswordを使用してログインユーザー名を表示できます。
		m.addAttribute("password",loginForm.getPassword());
		//このクラスは、商品情報を保持するために使用されます。MstGoods goods = new MstGoods();
		//というコードは、新しいMstGoodsオブジェクトを作成し、goods変数に割り当てるために使用されます。
		MstGoods goods = new MstGoods();
		//このメソッドは、MstGoodsオブジェクトのgoodsNameフィールドに値を設定するために使用されます。
		//goodsForm.getGoodsName()は、フォームから入力された商品名を取得するために使用されます。
		//これにより、商品名がMstGoodsオブジェクトに設定されます。
		goods.setGoodsName(goodsForm.getGoodsName());
		//このメソッドは、MstGoodsオブジェクトのpriceフィールドに値を設定するために使用されます。
		//goodsForm.getPrice()は、フォームから入力された価格を取得するために使用されます。
		//これにより、価格がMstGoodsオブジェクトに設定されます。
		goods.setPrice(goodsForm.getPrice());
		//このメソッドは、MstGoodsオブジェクトをデータベースに挿入するために使用されます。
		goodsMapper.insert(goods);
		
		return "forward:/ecsite/admin/welcome";
	}
	
	@ResponseBody
	@PostMapping("/api/deleteGoods")
	//このAPIは、HTTPリクエストを受け取り、データベースからデータを削除するために使用されます。
	//このAPIは、引数として渡されたIDに基づいて、goodsMapperという名前のオブジェクトからデータを削除します。
	//削除が成功した場合は、「1」が返され、失敗した場合は「-1」が返されます。
	//このように、APIの呼び出し元は、戻り値に基づいて処理を続行するかどうかを判断できます。
	
	//このメソッドは、GoodsFormオブジェクトを受け取り、データベースから削除するために使用されます。
	public String deleteApi(@RequestBody GoodsForm f,Model m) {
		//GoodsMapperオブジェクトを使用して、データベースからIDによってデータを削除します。
		//tryブロックは、例外が発生する可能性があるコードを含むブロックです。
		//tryブロック内で例外が発生した場合、tryブロック内の残りのコードは実行されません。
		//その代わりに、catchブロックが実行されます。
		//catchブロックは、例外を処理するためのコードを含むブロックです。
		try {
			//goodsMapperというオブジェクトのdeleteByidメソッドを呼び出し、引数としてf.getId()を渡しています。
			//このコードは、f.getId()に対応するデータを削除するために使用されます。
			goodsMapper.deleteByid(f.getId());
			//Javaで使用される例外の1つで、不正な引数、
			//または不適切な引数をメソッドに渡したことを示すためにスローされます。
			//不正な引数、または不適切な引数をメソッドに渡したことを示すためにスローされます。
		}catch(IllegalArgumentException e) {
			//関数の戻り値として-1を返すために使われるコードです。戻り値が-1であることは、通常、エラーが発生したことを示します。
			//このような場合、プログラムはエラー処理コードにジャンプし、エラーを処理するためのコードを実行します。
			return "-1";
		}
		//関数の戻り値として1を返すために使われるコードです。戻り値が1であることは、通常、成功したことを示します。
		//このような場合、プログラムは通常の処理を続けます。
		return "1";
	}
	
}
