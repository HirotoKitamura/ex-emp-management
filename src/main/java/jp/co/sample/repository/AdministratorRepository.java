package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * 管理者のリポジトリクラス.
 * 
 * @author hiroto.kitamura
 *
 */
@Repository
public class AdministratorRepository {
	@Autowired
	NamedParameterJdbcTemplate template;

	/**
	 * RowMapper. データベースのデータをAdministratorオブジェクトに入れる
	 */
	private static final RowMapper<Administrator> ADMIN_ROW_MAPPER = (rs, i) -> {
		return new Administrator(rs.getInt("id"), rs.getString("name"), rs.getString("mail_address"),
				rs.getString("password"));
	};

	/**
	 * 管理者を挿入.
	 * 
	 * @param administrator 挿入する管理者情報
	 */
	public void insert(Administrator administrator) {
		String sql = "INSERT INTO administrators (name,mail_address,password) values(:name,:mailAddress,:password);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		template.update(sql, param);
	}

	/**
	 * メールアドレスとパスワードから管理者情報を取得.
	 * 
	 * 1件も取れない場合はnullを返す
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 取得された管理者情報
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		String sql = "SELECT id,name,mail_address,password FROM administrators WHERE mail_address = :mailAddress AND password = :password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",
				password);
		List<Administrator> adminList = template.query(sql, param, ADMIN_ROW_MAPPER);
		if (adminList.size() == 0) {
			return null;
		}
		return adminList.get(0);
	}

	/**
	 * メールアドレスからメールアドレスを取得.
	 * 
	 * 入力されたメールアドレスが存在するかチェックする
	 * 
	 * @param mailAddress メールアドレス
	 * @return 取得されたメールアドレス 存在しなければnull
	 */
	public String findByMailAddress(String mailAddress) {
		String sql = "SELECT mail_address FROM administrators WHERE mail_address = :mailAddress";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
		List<String> mailList = template.queryForList(sql, param, String.class);
		if (mailList.size() == 0) {
			return null;
		}
		return mailList.get(0);
	}
}
