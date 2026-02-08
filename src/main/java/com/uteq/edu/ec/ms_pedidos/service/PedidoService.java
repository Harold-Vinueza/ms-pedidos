package com.uteq.edu.ec.ms_pedidos.service;

import com.uteq.edu.ec.ms_pedidos.client.ClienteClient;
import com.uteq.edu.ec.ms_pedidos.client.ClienteDTO;
import com.uteq.edu.ec.ms_pedidos.model.Pedido;
import com.uteq.edu.ec.ms_pedidos.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteClient clienteClient;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteClient clienteClient) {
        this.pedidoRepository = pedidoRepository;
        this.clienteClient = clienteClient;
    }

    // Validar cliente en ms-clientes
    public ClienteDTO validarCliente(Long clienteId) {

        ClienteDTO cliente = clienteClient.obtenerClientePorId(clienteId);

        if (cliente == null) {
            throw new RuntimeException("CLIENTE_NO_EXISTE");
        }

        return cliente;
    }

    // Crear pedido SOLO si el cliente existe
    public Pedido crearPedido(Pedido pedido) {

        validarCliente(pedido.getClienteId());

        pedido.setFecha(LocalDate.now());
        return pedidoRepository.save(pedido);
    }

    // Listar pedidos
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }
}
