package jp.co.sample.form;

import org.hibernate.validator.constraints.Range;

/**
 * 従業員情報更新のフォーム.
 * 
 * @author hiroto.kitamura
 *
 */
public class UpdateEmployeeForm {
	/**
	 * ID.
	 */
	private String id;
	/**
	 * 扶養人数.
	 */
	@Range(min = 0, max = 1000, message = "扶養人数は0から1000までの数値で入力してください")
	private String dependentsCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

	@Override
	public String toString() {
		return "UpdateEmployeeForm [id=" + id + ", dependentsCount=" + dependentsCount + "]";
	}

}
