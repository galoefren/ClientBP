package com.bp.ClientBP.testController;

import com.bp.ClientBP.controller.ClienteController;
import com.bp.ClientBP.model.Cliente;
import com.bp.ClientBP.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClienteController.class)
@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ClienteController clienteController;

    @BeforeEach
    public void init () {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    public void testGetAllClientes() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setClienteid(1L);
        cliente.setContrasena("password");
        cliente.setEstado(true);

        List<Cliente> allClientes = Collections.singletonList(cliente);

        when(clienteService.getAllClientes()).thenReturn(allClientes);

        mockMvc.perform(get("/clientes/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].clienteid", is(cliente.getClienteid().intValue())));

    }

    @Test
    public void testGetClienteById() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setClienteid(1L);
        cliente.setContrasena("password");
        cliente.setEstado(true);

        when(clienteService.getClienteById(1L)).thenReturn(cliente);

        mockMvc.perform(get("/clientes/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteid", is(cliente.getClienteid().intValue())));
    }

    @Test
    public void testCreateCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setClienteid(1L);
        cliente.setContrasena("password");
        cliente.setEstado(true);

        when(clienteService.createCliente(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contraseña\":\"password\",\"estado\":true}"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.clienteid", is(cliente.getClienteid().intValue())));


    }

    @Test
    public void testUpdateCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setClienteid(1L);
        cliente.setContrasena("password");
        cliente.setEstado(true);

        when(clienteService.updateCliente(eq(1L), any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(put("/clientes/put/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"contraseña\":\"newpassword\",\"estado\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteid", is(cliente.getClienteid().intValue())));
    }

    @Test
    public void testDeleteCliente() throws Exception {
        doNothing().when(clienteService).deleteCliente(1L);

        mockMvc.perform(delete("/clientes/delete/1"))
                .andExpect(status().isOk());

        verify(clienteService, times(1)).deleteCliente(1L);
    }

}
