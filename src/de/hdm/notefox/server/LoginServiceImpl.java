package de.hdm.notefox.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.notefox.server.db.NutzerMapper;
import de.hdm.notefox.shared.LoginInfo;
import de.hdm.notefox.shared.LoginService;
import de.hdm.notefox.shared.Nutzer;

/**
 * TODO
 * 
 * @author Harun Dalici & Muhammed Simsek
 *
 */
public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	private static final long serialVersionUID = 1L;

	private static LoginService loginService;

	public static LoginService loginService() {
		if (loginService == null) {
			loginService = new LoginServiceImpl();
		}
		return loginService;
	}

	@Override
	public LoginInfo login(String requestUri) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		LoginInfo loginInfo = new LoginInfo();

		if (user != null) {
			loginInfo.setLoggedIn(true);
			loginInfo.setEmail(user.getEmail());
			// loginInfo.setNutzer(NutzerMapper.nutzerMapper().nachNutzerEmailSuchen(user.getEmail()));
			loginInfo.setNutzer(new Nutzer());
			loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
		} else {
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
		}
		return loginInfo;
	}

	public Nutzer getCurrentNutzer() {
		LoginInfo login = login("");
		if (login.isLoggedIn()) {
			return login.getNutzer();
		} else {
			return null;
		}
	}
}