package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
	public String showDetail(Integer id, Model model) {
		if (session.getAttribute("administratorName") == null) {
			model.addAttribute("error", "ログインしてください");
			return "administrator/login";
		}
		Employee employee = employeeService.showDetail(id);
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/**
	 * 従業員の扶養人数を更新.
	 * 
	 * @param form 従業員のIDと入力された扶養人数が入ったフォーム
	 * @return 従業員一覧画面 異常な入力があれば遷移しない
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		BeanUtils.copyProperties(form, employee);
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

}
