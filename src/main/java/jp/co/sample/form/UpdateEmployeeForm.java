package jp.co.sample.form;

import java.sql.Date;

import javax.validation.constraints.NotBlank;

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
	private Integer id;
	/**
	 * 名前.
	 */
	@NotBlank(message = "名前を入力してください")
	private String name;
	/**
	 * 画像.
	 */
	private String image;
	/**
	 * 性別.
	 */
	private String gender;
	/**
	 * 入社日.
	 */
	private Date hireDate;
	/**
	 * メールアドレス.
	 */
	private String mailAddress;
	/**
	 * 郵便番号.
	 */
	private String zipCode;
	/**
	 * 住所.
	 */
	private String address;
	/**
	 * 電話番号.
	 */
	private String telephone;
	/**
	 * 給料.
	 */
	@Range(min = 0, max = 1000000000, message = "給料は0から10億までの数値で入力してください")
	private Integer salary;
	/**
	 * 特性.
	 */
	private String characteristics;
	/**
	 * 扶養人数.
	 */
	@Range(min = 0, max = 1000, message = "扶養人数は0から1000までの数値で入力してください")
	private Integer dependentsCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public Integer getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(Integer dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

	@Override
	public String toString() {
		return "UpdateEmployeeForm [id=" + id + ", name=" + name + ", image=" + image + ", gender=" + gender
				+ ", hireDate=" + hireDate + ", mailAddress=" + mailAddress + ", zipCode=" + zipCode + ", address="
				+ address + ", telephone=" + telephone + ", salary=" + salary + ", characteristics=" + characteristics
				+ ", dependentsCount=" + dependentsCount + "]";
	}

}
