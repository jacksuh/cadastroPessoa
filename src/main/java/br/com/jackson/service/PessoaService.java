package br.com.jackson.service;

import org.apache.poi.ss.usermodel.*;

import br.com.jackson.model.Pessoa;

import javax.servlet.http.Part;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
import java.io.InputStream;
import java.util.List;


@Stateless
public class PessoaService {
	
	@PersistenceContext(unitName = "meuPU")
    private EntityManager em;
	
	public void importarExcel(Part file) {
        if (file == null) {
            throw new RuntimeException("Nenhum arquivo enviado");
        }

        try (InputStream is = file.getInputStream();
             Workbook wb = WorkbookFactory.create(is)) {

            Sheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {
                // pula cabeçalho se tiver
                if (row.getRowNum() == 0) {
                    continue;
                }

                Cell nomeCell = row.getCell(0); // primeira coluna
                if (nomeCell == null) continue;

                String nome = nomeCell.getStringCellValue();
                if (nome == null || nome.trim().isEmpty()) continue;

                Pessoa p = new Pessoa();
                p.setNome(nome.trim());

                em.persist(p);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao importar Excel: " + e.getMessage(), e);
        }
    }

    public void salvar(Pessoa p) {
        if (p.getId() == null) {
            em.persist(p);
        } else {
            em.merge(p);
        }
    }

    public List<Pessoa> listar() {
        return em.createQuery("select p from Pessoa p", Pessoa.class)
                 .getResultList();
    }

    public Pessoa novaPessoa() {
        return new Pessoa();
    }
}
