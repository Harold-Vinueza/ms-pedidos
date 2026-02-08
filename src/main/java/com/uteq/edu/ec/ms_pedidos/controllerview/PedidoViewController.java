package com.uteq.edu.ec.ms_pedidos.controllerview;

import com.uteq.edu.ec.ms_pedidos.client.ClienteDTO;
import com.uteq.edu.ec.ms_pedidos.model.Pedido;
import com.uteq.edu.ec.ms_pedidos.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PedidoViewController {

    private final PedidoService pedidoService;

    public PedidoViewController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    //  Mostrar vista principal
    @GetMapping("/pedidos")
    public String verPedidos(Model model) {

        model.addAttribute("pedidos", pedidoService.listarPedidos());
        model.addAttribute("pedido", new Pedido()); // formulario limpio
        return "pedidos";
    }

      


    // Crear pedido con control de errores
    @PostMapping("/pedidos/crear") // /crear
    public String crearPedido(Pedido pedido, Model model) {

        try {
            // Validar cliente y obtener datos desde ms-clientes
            ClienteDTO cliente = pedidoService.validarCliente(pedido.getClienteId());

            // Crear pedido
            pedidoService.crearPedido(pedido);

            model.addAttribute("cliente", cliente);
            model.addAttribute("mensaje", "Pedido registrado correctamente ✅");

            // Limpiar formulario SOLO cuando se guarda bien
            model.addAttribute("pedido", new Pedido());

        } catch (RuntimeException e) {

            if ("CLIENTE_NO_EXISTE".equals(e.getMessage())) {
                model.addAttribute("error", "❌ El cliente no existe en ms-clientes");
            } else {
                model.addAttribute("error", "❌ Error inesperado al registrar el pedido");
            }

            //  CLAVE: mantener datos del formulario
            model.addAttribute("pedido", pedido);
        }

        model.addAttribute("pedidos", pedidoService.listarPedidos());
        return "pedidos";
    }
}
