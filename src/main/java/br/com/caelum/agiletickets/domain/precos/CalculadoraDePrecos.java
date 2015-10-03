package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	private static class PoliticaDePreco {

		TipoDeEspetaculo tipo;
		Integer duracaoNormal;
		double taxaDuracao;
		double minimoLivre;
		double taxaLotacao;
		
		public PoliticaDePreco(TipoDeEspetaculo tipo, Integer duracaoNormal, double taxaDuracao, double minimoLivre, double taxaLotacao) {
			super();
			this.tipo = tipo;
			this.duracaoNormal = duracaoNormal;
			this.taxaDuracao = taxaDuracao;
			this.minimoLivre = minimoLivre;
			this.taxaLotacao = taxaLotacao;
		}

		public TipoDeEspetaculo getTipo() {
			return tipo;
		}

		private BigDecimal calculaTaxaLotacao(Sessao sessao) {
			
			BigDecimal taxa = new BigDecimal(0);
			
			double livre = (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue();
			if (livre <= this.minimoLivre) { 
				taxa = sessao.getPreco().multiply(BigDecimal.valueOf(this.taxaLotacao));
			}
			return taxa;
		}
		
		private BigDecimal calculaTaxaDuracao(Sessao sessao) {
			
			BigDecimal taxa = new BigDecimal(0);
			
			if (sessao.getDuracaoEmMinutos() > this.duracaoNormal) {
				taxa = sessao.getPreco().multiply(BigDecimal.valueOf(this.taxaDuracao));
			}
			return taxa;
		}

		public BigDecimal calcula(Sessao sessao) {
			
			BigDecimal preco = sessao.getPreco();
			preco.add(calculaTaxaLotacao(sessao));
			preco.add(calculaTaxaDuracao(sessao));
			return preco;
		}
	}
	
	static PoliticaDePreco[] politicas = {
		//                                               duracaoNormal  taxaDuracao  minimoLivre  taxaLotacao
		new PoliticaDePreco(TipoDeEspetaculo.CINEMA, Integer.MAX_VALUE,         0.0,         0.0,        0.10),
		new PoliticaDePreco(TipoDeEspetaculo.SHOW,   Integer.MAX_VALUE,         0.0,         0.0,        0.10),
		new PoliticaDePreco(TipoDeEspetaculo.BALLET,                60,        0.10,        0.50,        0.20),
		new PoliticaDePreco(TipoDeEspetaculo.ORQUESTRA,             60,        0.10,        0.50,        0.20)
	};
	
	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco = sessao.getPreco();
		for (PoliticaDePreco politica : politicas) {
			if (politica.getTipo().equals(sessao.getEspetaculo().getTipo())) {
				preco = politica.calcula(sessao);
				break;
			}
		}
		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

}