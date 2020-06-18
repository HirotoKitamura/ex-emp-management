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

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * Administratorのコントローラークラス.
 * 
 * @author hiroto.kitamura
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private HttpSession session;

	/**
	 * 管理者登録画面の入力値チェック用.
	 * 
	 * @return InsertAdministratorFormのインスタンス
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	/**
	 * ログイン画面の入力値チェック用.
	 * 
	 * @return LoginFormのインスタンス
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/**
	 * ログイン画面を呼び出す.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	/**
	 * ログインする.
	 * 
	 * @param form  ログインフォームに入力された情報
	 * @param model リクエストスコープ
	 * @return ログイン成功なら従業員一覧画面、失敗ならログイン画面
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		if (form.getMailAddress() == null) {
			return "administrator/login";
		}
		Administrator admin = administratorService.login(form.getMailAddress(), form.getPassword());
		if (admin == null) {
			model.addAttribute("error", "メールアドレスまたはパスワードが不正です。");
			return "administrator/login";
		}
		session.setAttribute("administrator", admin);
		return "forward:/employee/showList";
	}

	/**
	 * 管理者登録の画面を呼び出す.
	 * 
	 * @return 管理者登録の画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert(Model model) {
		return "administrator/insert";
	}

	/**
	 * 管理者を登録し、ログイン画面を呼び出す.
	 * 
	 * @param form フォームに入力された情報
	 * @return ログイン画面 入力値チェックで弾かれた場合は遷移しない
	 */
	@RequestMapping("/insert")
	public String insert(@Validated InsertAdministratorForm form, BindingResult result, Model model) {
		boolean hasErrors = false;
		if (result.hasErrors()) {
			hasErrors = true;
		}
		if (!administratorService.isValidMailAddress(form.getMailAddress())) {
			model.addAttribute("error", "入力されたメールアドレスは既に使用されています");
			hasErrors = true;
		}
		if (hasErrors) {
			return toInsert(model);
		}
		Administrator admin = new Administrator();
		BeanUtils.copyProperties(form, admin);
		administratorService.insert(admin);
		return "redirect:/";
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

	/**
	 * 管理者情報変更画面に飛ぶ.
	 * 
	 * @return 管理者情報変更画面
	 */
	@RequestMapping("/toEditProfile")
	public String toEditProfile(Model model) {
		if (session.getAttribute("administrator") == null) {
			model.addAttribute("error", "ログインしてください");
			return "administrator/login";
		}
		return "administrator/edit";
	}
	
	/**
	 * 管理者情報の変更
	 * @param form　フォームに入力された情報　
	 * @param result　入力値チェックの結果
	 * @param model　リクエストスコープ
	 * @return　遷移するページ
	 */
	@RequestMapping("/editProfile")
	public String editProfile(@Validated InsertAdministratorForm form, BindingResult result, Model model) {
		boolean hasErrors = false;
		if (result.hasErrors()) {
			hasErrors = true;
		}
		if (!administratorService.isValidMailAddress(form.getMailAddress()) 
				&& !form.getMailAddress().equals(((Administrator)session.getAttribute("administrator")).getMailAddress())) {
			model.addAttribute("error", "入力されたメールアドレスは既に使用されています");
			hasErrors = true;
		}
		if (hasErrors) {
			return toEditProfile(model);
		}
		Administrator admin = new Administrator();
		BeanUtils.copyProperties(form, admin);
		System.out.println(admin.getId());
		admin.setId(((Administrator)session.getAttribute("administrator")).getId());
		session.setAttribute("administrator", admin);
		administratorService.update(admin);
		return "redirect:/employee/showList";
	}
}
