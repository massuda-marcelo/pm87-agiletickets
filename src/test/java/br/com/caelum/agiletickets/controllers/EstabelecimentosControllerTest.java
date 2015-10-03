package br.com.caelum.agiletickets.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.caelum.agiletickets.domain.DiretorioDeEstabelecimentos;
import br.com.caelum.agiletickets.models.Estabelecimento;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.ValidationException;
import br.com.caelum.vraptor.validator.Validator;

public class EstabelecimentosControllerTest {

	private @Mock DiretorioDeEstabelecimentos diretorio;
	private @Spy Result result = new MockResult();
	private @Spy Validator validator = new MockValidator();
	
	private EstabelecimentosController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		controller = new EstabelecimentosController(result, validator, diretorio);
	}

	@Test(expected=ValidationException.class)
	public void naoDeveAdicionarEstabelecimentoSemNome() throws Exception {
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setEndereco("Um endereco");

		controller.adiciona(estabelecimento);

		verifyNoMoreInteractions(diretorio);
	}

	@Test(expected=ValidationException.class)
	public void naoDeveAdicionarEstabelecimentoSemEndereco() throws Exception {
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setNome("Um nome");

		controller.adiciona(estabelecimento);

		verifyNoMoreInteractions(diretorio);
	}

	@Test
	public void deveAdicionarEstabelecimentoComNomeEEndereco() throws Exception {
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setNome("Um nome");
		estabelecimento.setEndereco("Um endereco");

		controller.adiciona(estabelecimento);

		verify(diretorio).adiciona(estabelecimento);
	}
	
	@Test
	public void deveListarTodosDoDiretorio() {
		DiretorioDeEstabelecimentos DAO = Mockito.mock(DiretorioDeEstabelecimentos.class);
		List<Estabelecimento> emptyList = Collections.emptyList();
		Mockito.when(DAO.todos()).thenReturn(emptyList);
		
		EstabelecimentosController sujeito = new EstabelecimentosController(null, null, DAO);
		
		List<Estabelecimento> todos = sujeito.lista();
		
		assertEquals(emptyList, todos);
		
	}

	@Test(expected=RuntimeException.class)
	public void deveLancarException() {
		DiretorioDeEstabelecimentos DAO = Mockito.mock(DiretorioDeEstabelecimentos.class);
		Mockito.when(DAO.todos()).thenThrow(new RuntimeException());
		
		EstabelecimentosController sujeito = new EstabelecimentosController(null, null, DAO);
		
		sujeito.lista();
	}
}
