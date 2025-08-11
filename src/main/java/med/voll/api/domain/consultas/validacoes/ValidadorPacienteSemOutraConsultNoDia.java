package med.voll.api.domain.consultas.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultNoDia implements ValidadorAgendamentoDeConsultas {
    @Autowired
    private ConsultaRepository repository;


    public void validar(DadosAgendamentoConsulta dados){
        var PrimeiroHorario = dados.data().withHour(7);
        var UltimoHorario = dados.data().withHour(18);
        var PacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente() , PrimeiroHorario, UltimoHorario );
        if(PacientePossuiOutraConsultaNoDia){
            throw new ValidacaoException("Paciente possui consulta agendada no dia ");
        }
    }
}
