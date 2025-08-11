package med.voll.api.domain.consultas.validacoes;


import med.voll.api.domain.consultas.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsultas {

    void validar(DadosAgendamentoConsulta dados);
}
