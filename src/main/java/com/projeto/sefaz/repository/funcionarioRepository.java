package com.projeto.sefaz.repository;

import com.projeto.sefaz.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface funcionarioRepository extends JpaRepository<Funcionario, Long> {
}
