package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	@Test
	public void deveCriarUmaSessaoSeInicioIgualAFim() {
		
		Espetaculo espetaculo = new Espetaculo();

		LocalDate inicio = new LocalDate();
		LocalDate fim = inicio;
		LocalTime horario = new LocalTime();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);

		assertTrue(sessoes.size() == 1);
		
		Sessao s = sessoes.get(0);
		assertTrue(new LocalDate(s.getInicio()).isEqual(inicio));
		assertTrue(new LocalTime(s.getInicio()).isEqual(horario));
	}
	
	@Test
	/*
     * - Caso a data de inicio seja 01/01/2010, a data de fim seja 03/01/2010,
     * e a periodicidade seja DIARIA, o algoritmo cria 3 sessoes, uma 
     * para cada dia: 01/01, 02/01 e 03/01.
	 */
	public void deveCriarTresSessoesDiarias() {
		
		Espetaculo espetaculo = new Espetaculo();

		LocalDate inicio = new LocalDate(2010,1,1);
		LocalDate fim = new LocalDate(2010,1,3);
		LocalTime horario = new LocalTime();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		assertTrue(sessoes.size() == 3);
		
		LocalDate date = inicio;
		for (Sessao sessao : sessoes) {
			assertTrue(new LocalDate(sessao.getInicio()).isEqual(date));
			assertTrue(new LocalDate(sessao.getInicio()).isEqual(date));
			assertTrue(new LocalTime(sessao.getInicio()).isEqual(horario));
			date = date.plusDays(1);
		}
	}
	
	@Test
	/*
     * - Caso a data de inicio seja 01/01/2010, a data fim seja 31/01/2010,
     * e a periodicidade seja SEMANAL, o algoritmo cria 5 sessoes, uma
     * a cada 7 dias: 01/01, 08/01, 15/01, 22/01 e 29/01.
	 */
	public void deveCriarTresSessoesSemanais() {
		
		Espetaculo espetaculo = new Espetaculo();

		LocalDate inicio = new LocalDate(2010,1,1);
		LocalDate fim = new LocalDate(2010,1,31);
		LocalTime horario = new LocalTime();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.SEMANAL);
		assertTrue(sessoes.size() == 5);
		
		LocalDate date = inicio;
		for (Sessao sessao : sessoes) {
			assertTrue(new LocalDate(sessao.getInicio()).isEqual(date));
			assertTrue(new LocalTime(sessao.getInicio()).isEqual(horario));
			date = date.plusWeeks(1);
		}
	}
	
	@Test
	/*
     * - Caso a data de inicio seja 01/01/2010, a data fim seja 31/01/2010,
     * e a periodicidade seja SEMANAL, o algoritmo cria 5 sessoes, uma
     * a cada 7 dias: 01/01, 08/01, 15/01, 22/01 e 29/01.
	 */
	public void naoCriarSessoesSeFimMenorInicio() {
		
		Espetaculo espetaculo = new Espetaculo();

		LocalDate inicio = new LocalDate(2010,1,31);
		LocalDate fim = new LocalDate(2010,1,1);
		LocalTime horario = new LocalTime();
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, Periodicidade.SEMANAL);
		assertTrue(sessoes.size() == 0);
	}
}
