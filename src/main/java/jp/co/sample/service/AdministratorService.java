package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

/**
 * Administratorのサービスクラス.
 * 
 * @author hiroto.kitamura
 *
 */
@Service
@Transactional
public class AdministratorService {
	@Autowired
	private AdministratorRepository administratorRepository;

	/**
	 * 管理者を登録.
	 * 
	 * @param administrator 登録する管理者情報
	 */
	public void insert(Administrator administrator) {
		administratorRepository.insert(administrator);
	}

	/**
	 * 管理者情報をIDから変更.
	 * 
	 * @param administrator 新しい管理者情報
	 */
	public void update(Administrator administrator) {
		administratorRepository.update(administrator);
	}

	/**
	 * 管理者のログイン.
	 * 
	 * @param mailAddress 入力されたメールアドレス
	 * @param password    入力されたパスワード
	 * @return 取得された管理者情報
	 */
	public Administrator login(String mailAddress, String password) {
		return administratorRepository.findByMailAddressAndPassword(mailAddress, password);
	}

	/**
	 * 入力されたメールアドレスが使用できるかチェック.
	 * 
	 * 既にDBに登録されていれば使用できない
	 * 
	 * @param mailAddress 入力されたメールアドレス
	 * @return DB上に存在しなければtrue、存在すればfalse
	 */
	public boolean isValidMailAddress(String mailAddress) {
		return (administratorRepository.findByMailAddress(mailAddress) == null);
	}

}
