package com.bp.ClientBP.service;


import com.bp.ClientBP.model.Cliente;
import com.bp.ClientBP.model.Persona;
import com.bp.ClientBP.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente not found"));
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Long id, Cliente clienteDetails) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Persona persona = clienteDetails.getPersona();
        cliente.setContrasena(clienteDetails.getContrasena());
        cliente.setEstado(clienteDetails.isEstado());
        cliente.getPersona().setNombre(persona.getNombre());
        cliente.getPersona().setGenero(persona.getGenero());
        cliente.getPersona().setEdad(persona.getEdad());
        cliente.getPersona().setDireccion(persona.getDireccion());
        cliente.getPersona().setTelefono(persona.getTelefono());
        return clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }

}
