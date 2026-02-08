package com.uteq.edu.ec.ms_pedidos.repository;

import com.uteq.edu.ec.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
