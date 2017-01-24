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
 * Implementierungsklasse des Interface <code>LoginService</code>.
 * 
 * @see LoginService
 * @see LoginServiceAsync
 * @see RemoteServiceServlet
 * 
 * @author Harun Dalici & Muhammed Simsek
 *
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

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
			Nutzer nutzer = NutzerMapper.nutzerMapper().nachNutzerEmailSuchen(user.getEmail());
			if (nutzer == null) {
				nutzer = new Nutzer();
				nutzer.setEmail(user.getEmail());
				nutzer = NutzerMapper.nutzerMapper().anlegenNutzer(nutzer);
				if (nutzer == null) {
					loginInfo.setLoggedIn(false);
					loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
					return loginInfo;
				}
			}
			loginInfo.setNutzer(nutzer);
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