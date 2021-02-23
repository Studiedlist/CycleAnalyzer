package org.einnfeigr.iot;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mobile.device.Device;
import org.springframework.web.servlet.ModelAndView;

public class ControllerUtils {

	private static final List<String> availableLangs = new ArrayList<>();
	
	public static final String DEFAULT_VERSION = "desktop";
	
	public static final String VERSION_COOKIE_NAME = "ver";
	public static final String VERSION_PARAM_NAME = "ver";
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Device device;
	
	public ControllerUtils(HttpServletRequest request, HttpServletResponse response, 
			Device device) {
		this.request = request;
		this.response = response;
		this.device = device;
	}
	
	public static List<String> getAvailableLangs() {
		return availableLangs;
	}
	
	//Selected language must be displayed first in list
	public static List<String> getSortedLangs(Locale locale) {
		List<String> langs = new ArrayList<>(availableLangs);
		langs.remove(locale.getLanguage());
		langs.add(0, locale.getLanguage());
		return langs;
	}
	
	public ModelAndView buildMav(String path) throws FileNotFoundException {
		return buildMav(path, device, request, response, "");
	}
	
	public ModelAndView buildMav(String path, Object...params) throws FileNotFoundException {
		return buildMav(path, arrayToMap(params));
	}
	
	public ModelAndView buildMav(String path, Map<String, Object> params) throws FileNotFoundException {
		String version = parseParam(VERSION_COOKIE_NAME, VERSION_PARAM_NAME);
		version = version == null ? DEFAULT_VERSION : version;
		ModelAndView mav = new ModelAndView(path);
		Map<String, Object> data = mav.getModel();
		data.put("isMobile", !device.isNormal() || "mobile".equals(version));
		data.put("page", path);
		data.putAll(params);
		return mav;
	}
	
	private String parseParam(String cookieName, String paramName) {
		String param;
		if((param = request.getParameter(paramName)) != null) {
			response.addCookie(new Cookie(cookieName, param));
			return param;
		}
		if(request.getCookies() == null) {
			return null;
		}
		for(Cookie cookie : request.getCookies()) {
			if(cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	private static Map<String, Object> arrayToMap(Object[] params) {
		Map<String, Object> map = new HashMap<>();
		if(params.length > 0) {
			for(int x = 1; x < params.length; ++x) {
				map.put(params[x-1].toString(), params[x]);
			}
		}
		return map;
	}
	
}
