package com.projeto.sefaz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
public class Unidades implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unidades")
    private Long id;

    private String nome;

    private String local;


    @ManyToMany
    @JoinTable(
            name = "unidade_funcionario",
            joinColumns = @JoinColumn(name = "id_unidade"),
            inverseJoinColumns = @JoinColumn(name = "id_funcionario")
    )
    private Set<Funcionario> listaFuncionario;



}
