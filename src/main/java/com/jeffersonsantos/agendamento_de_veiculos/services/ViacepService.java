package com.jeffersonsantos.agendamento_de_veiculos.services;

import com.google.gson.Gson;
import java.io.IOException;
import com.jeffersonsantos.agendamento_de_veiculos.models.entities.Endereco;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

// Responsável pela consulta de CEP na API viaCEP.
@Service
public class ViacepService {
  public Endereco getEndereco(String cep) throws IOException {
    HttpGet req = new HttpGet("https://viacep.com.br/ws/"+cep+"/json/");

    try(CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
        CloseableHttpResponse resp = httpClient.execute(req)) {
      HttpEntity entity = resp.getEntity();

      if (entity != null) {
        String result = EntityUtils.toString(entity);

        return new Gson().fromJson(result, Endereco.class);
      }
    }
    return null;
  }
}
