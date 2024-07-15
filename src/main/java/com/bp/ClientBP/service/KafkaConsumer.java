package com.bp.ClientBP.service;

import com.bp.ClientBP.model.Cliente;
import com.bp.ClientBP.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final String RESPONSE_TOPIC = "cliente_id_respuesta";
    private static final String RESPONSE_TOPIC_NOMBRE = "cliente_id_nombre";


    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "buscar_cliente", groupId = "group_id")
    public void consume(String message) {
        // Buscar clienteId por numeroCedula
        Cliente cliente = clienteRepository.findByPersonaIdentificacion(message);

        if (cliente != null) {
            kafkaTemplate.send(RESPONSE_TOPIC, cliente.getClienteid().toString());
        } else {
            kafkaTemplate.send(RESPONSE_TOPIC, "null");
        }
    }


    @KafkaListener(topics = "buscar_cliente_nombre", groupId = "group_id")
    public void consumeCliente(String message) {
        // Buscar clienteId por numeroCedula
        Cliente cliente = clienteRepository.findByPersonaIdentificacion(message);
        if (cliente != null) {
            kafkaTemplate.send(RESPONSE_TOPIC_NOMBRE, cliente.getClienteid().toString()+"_"+cliente.getPersona().getNombre());
        } else {
            kafkaTemplate.send(RESPONSE_TOPIC_NOMBRE, "null");
        }
    }
}
