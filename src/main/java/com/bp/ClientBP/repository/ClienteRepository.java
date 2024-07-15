package com.bp.ClientBP.repository;
import com.bp.ClientBP.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByPersonaIdentificacion(String numeroCedula);
}