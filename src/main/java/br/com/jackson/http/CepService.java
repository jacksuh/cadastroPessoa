package br.com.jackson.http;

import br.com.jackson.dto.CepResponse;
import br.com.jackson.http.CepService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

@Stateless
public class CepService {
	
	 @Inject
	    private HttpClientService httpClientService;

	    public CepResponse buscarCep(String cep) {
	        // tira traço se o usuário digitar 01001-000
	        String cepLimpo = cep.replaceAll("\\D", "");

	        String url = "https://viacep.com.br/ws/" + cepLimpo + "/json/";
	        String json = httpClientService.get(url);

	        try (JsonReader reader = Json.createReader(new StringReader(json))) {
	            JsonObject obj = reader.readObject();

	            // ViaCEP retorna {"erro": true} se o CEP não existe
	            if (obj.containsKey("erro") && obj.getBoolean("erro")) {
	                throw new RuntimeException("CEP não encontrado: " + cep);
	            }

	            CepResponse resp = new CepResponse();
	            resp.setCep(obj.getString("cep", ""));
	            resp.setLogradouro(obj.getString("logradouro", ""));
	            resp.setBairro(obj.getString("bairro", ""));
	            resp.setLocalidade(obj.getString("localidade", ""));
	            resp.setUf(obj.getString("uf", ""));

	            return resp;
	        }
	    }

}
