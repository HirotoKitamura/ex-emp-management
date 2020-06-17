package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.LoginForm;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * Employeeのコントローラークラス.
 * 
 * @author hiroto.kitamura
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	/**
	 * 従業員一覧を出力.
	 * 
	 * @param model リクエストスコープ
	 * @return 従業員一覧画面 ログインしていない場合はエラーを出してログイン画面へ
	 * 
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		if (session.getAttribute("administratorName") == null) {
			model.addAttribute("error", "ログインしてください");
			return "administrator/login";
		}
		model.addAttribute("employeeList", employeeService.showList());
		return "employee/list";
	}

	/**
	 * クリックされた従業員の情報を出力.
	 * 
	 * @param id    クリックされた従業員のID
	 * @param model リクエストスコープ
	 * @return 従業員情報の画面 ログインしていない場合はエラーを出してログイン画面へ
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		if (session.getAttribute("administratorName") == null) {
			model.addAttribute("error", "ログインしてください");
			return "administrator/login";
		}
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/**
	 * 従業員の扶養人数を更新.
	 * 
	 * @param form 従業員のIDと入力された扶養人数が入ったフォーム
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

	/**
	 * ログアウト.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
}
