package br.com.jackson.bean;

import br.com.jackson.model.Pessoa;
import br.com.jackson.service.PessoaService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.Part;
import javax.servlet.http.Part;

@Named
@SessionScoped
public class PessoaBean  implements Serializable{

	@Inject
    private PessoaService service;

    private Pessoa pessoa;
    private List<Pessoa> pessoas;

    private Part arquivoExcel;
    
    @PostConstruct
    public void init() {
        pessoa = service.novaPessoa();
        pessoas = service.listar();
    }

    public void salvar() {
        service.salvar(pessoa);
        pessoas = service.listar();
        pessoa = service.novaPessoa();
    }
    
    public void importarExcel() {
        if (arquivoExcel == null) {
            // ideal: adicionar uma FacesMessage na tela
            System.out.println("Nenhum arquivo selecionado");
            return;
        }

        service.importarExcel(arquivoExcel);
        pessoas = service.listar(); // atualiza grid depois da importação
    }

    public Part getArquivoExcel() {
        return arquivoExcel;
    }

    public void setArquivoExcel(Part arquivoExcel) {
        this.arquivoExcel = arquivoExcel;
    }
    
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

}
