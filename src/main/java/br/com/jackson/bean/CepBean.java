package br.com.jackson.bean;

import br.com.jackson.dto.CepResponse;
import br.com.jackson.http.CepService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class CepBean {

	 @Inject
	    private CepService cepService;

	    private String cep;
	    private CepResponse endereco;

	    public void consultar() {
	        this.endereco = cepService.buscarCep(cep);
	    }

	    // getters e setters

	    public String getCep() { return cep; }
	    public void setCep(String cep) { this.cep = cep; }

	    public CepResponse getEndereco() { return endereco; }
	}
