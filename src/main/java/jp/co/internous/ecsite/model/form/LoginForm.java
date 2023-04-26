package jp.co.internous.ecsite.model.form;

import java.io.Serializable;
/*シリアライズとは、複数の並列データを直列化して送信することである。
 *  具体的には、メモリ上に存在する情報を、ファイルとして保存したり、
 *  ネットワークで送受信したりできるように変換することである。
 */
public class LoginForm implements Serializable{
	
	private String userName;
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
