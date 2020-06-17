package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * Employeeのサービスクラス.
 * 
 * @author hiroto.kitamura
 *
 */
@Service
@Transactional
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員の全件検索.
	 * 
	 * @return 検索結果のリスト
	 */
	public List<Employee> showList() {
		return employeeRepository.findAll();
	}

	/**
	 * 従業員をIDから検索.
	 * 
	 * @param id フォームから渡されたID
	 * @return 取得された従業員情報
	 */
	public Employee showDetail(Integer id) {
		return employeeRepository.load(id);
	}

	/**
	 * 従業員情報を更新.
	 * 
	 * @param employee 渡された従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}
}
