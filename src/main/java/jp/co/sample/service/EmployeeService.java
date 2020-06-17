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
}
