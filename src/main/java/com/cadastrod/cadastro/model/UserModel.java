package com.cadastrod.cadastro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// Entity ele transforma uma classe em uam entidade do banco de dados

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_cadastro_de_usuarios")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String senha;
    //private String nome;
    //private String sobrenome;

    //private String telefone;
    //private String sexo;
    //private String estado_civil;
    //private String raca;
    //private String data_nascimento;
    //private String naturalidade;
    //private String uf;
    //private String nome_pai;
    //private String nome_mae;
    //private String cep;
    //private String logradouro;
    //private String numero;
    //private String complemento;
    //private String bairro;
    //private String cidade;
    //private String gestor;
    //private String sede;
    //private String celular;
    //private String tel_recado;
    //private String nome_recado;
//codigos oficiais
    //private String nacionalidade;
    //private String data_chegada;
    //private String grau_instrucao;
    //private String vinculo_empregaticio;
    //private String tipo_salario;
    //private String situacao_caged;
    //private String banco;
    //private String agencia;
    //private String conta;
    //private String tipo;
    //private String pagamento;
    //private String cpf_responsavel;
    //private String nome_responsavel;
    //private String tipo_contrato;
    //private String horario;
    //private String dias_experiencia;
    //private String dias_prorrogacao;
    //private String horas_semanais;
    //private String horas_mensais;
    //private String insalubridade;
    //private String periculosidade;
    //private String data_admissao;
    //private String data_demissao;
    //private String data_afastamento;
    //private String data_retorno;
    //private String motivo_demissao;
    //private String motivo_afastamento;
    //private String situacao_caged_demissao;
    //FGTS
    //private String cod_admissao;
    //private String data_opcao;
    //private String risco_trabalho;
    //private String observacao;
    //private String sindicato;
    //private String tributacao_sindical;
    //private String matricula;
    //documentos
    //private String carteira_profissional;
    //private String serie;
    //private String uf_ctps;
    //private String data_ctps;
    //private String titulo_eleitor;
    //private String zona;
    //private String secao;
    //private String n_habilitacao;
    //private String vencimento_habilitacao;
    //private String concelho_regional;
    //private String n_conselho_regional;
    //private String sigla_regional;
    //private String cpf;
    //private String rg;
    //private String uf_emissor;
    //private String data_expedicao_rg;
    //private String orgao_emissor_rg;
    //private String vertificado_reservista;
    //private String categoria_reservista;
    //private String pis;
    //private String data_cadastro_pis;
    // Varios serviços pode ter um usuário
    //@ManyToOne
    //@JoinColumn(name =  "id_servico") //FK
    //private ServicosModel servicosModel;
}