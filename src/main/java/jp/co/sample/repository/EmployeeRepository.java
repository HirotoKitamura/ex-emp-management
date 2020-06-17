package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * 従業員のリポジトリクラス.
 * 
 * @author hiroto.kitamura
 *
 */
@Repository
public class EmployeeRepository {
	@Autowired
	NamedParameterJdbcTemplate template;

	/**
	 * RowMapper.
	 * 
	 * データベースのデータをEmployeeオブジェクトに入れる
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		return new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getString("gender"),
				rs.getDate("hire_date"), rs.getString("mail_address"), rs.getString("zip_code"),
				rs.getString("address"), rs.getString("telephone"), rs.getInt("salary"),
				rs.getString("characteristics"), rs.getInt("dependents_count"));
	};

	/**
	 * 全件検索.
	 * 
	 * @return 検索結果のリスト
	 */
	public List<Employee> findAll() {
		String sql = "SELECT * FROM employees ORDER BY hire_date DESC;";
		return template.query(sql, EMPLOYEE_ROW_MAPPER);
	}

	/**
	 * 主キー検索.
	 * 
	 * @param id 検索するID
	 * @return 取得された従業員情報
	 */
	public Employee load(Integer id) {
		String sql = "SELECT * FROM employees WHERE id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
	}

	/**
	 * 従業員情報の変更
	 * 
	 * @param employee 変更する従業員の情報
	 */
	public void update(Employee employee) {
		String sql = "UPDATE employees SET name = :name, image = :image, gender = :gender"
				+ ", hire_date = :hireDate, mail_address = :mailAddress, zip_code = :zipCode"
				+ ", address = :address, telephone = :telephone, salary = :salary"
				+ ", characteristics = :characteristics, dependents_count = :dependentsCount WHERE id = :id;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		template.update(sql, param);
	}
}
