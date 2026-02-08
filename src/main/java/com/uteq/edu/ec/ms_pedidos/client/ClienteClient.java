package com.uteq.edu.ec.ms_pedidos.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ClienteClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public ClienteDTO obtenerClientePorId(Long id) {
        try {
            String url = "http://localhost:8081/api/clientes/" + id;
            return restTemplate.getForObject(url, ClienteDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
