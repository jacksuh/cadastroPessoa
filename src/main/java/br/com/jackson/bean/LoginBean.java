package br.com.jackson.bean;

import br.com.jackson.model.Pessoa; // ou Usuario, se tiver

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private LoginService service;

    private String usuario;
    private String senha;

    // quem está logado (pode ser Pessoa, Usuario, etc.)
    private Pessoa pessoaLogada;

    // método chamado pelo botão "Entrar"
    public String logar() {
        Pessoa autenticado = service.autenticar(usuario, senha);

        if (autenticado != null) {
            this.pessoaLogada = autenticado;

            // vai para a página principal
            return "/index.xhtml?faces-redirect=true";
        } else {
            // mostra mensagem de erro
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                 "Login ou senha inválidos", null)
            );
            return null; // fica na mesma página
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .invalidateSession();

        return "/login.xhtml?faces-redirect=true";
    }

    // GETTERS e SETTERS

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Pessoa getPessoaLogada() {
        return pessoaLogada;
    }

    public void setPessoaLogada(Pessoa pessoaLogada) {
        this.pessoaLogada = pessoaLogada;
    }
}
