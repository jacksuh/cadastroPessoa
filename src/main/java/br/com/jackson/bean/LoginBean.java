package br.com.jackson.bean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@SessionScoped
public class LoginBean {
	
	@Inject
    private LoginService service;

	
}
