package br.com.caelum.agiletickets.models;

import java.math.BigDecimal;

public enum TipoDeEspetaculo {

	CINEMA {

		@Override
		public BigDecimal calcula(Sessao sessao, Integer quantidade) {
			BigDecimal preco = sessao.getPreco();
			// quando estiver acabando os ingressos...
			if ((sessao.getTotalIngressos() - sessao.getIngressosReservados())
					/ sessao.getTotalIngressos().doubleValue() <= 0.05) {
				preco = preco.add(preco.multiply(BigDecimal.valueOf(0.10)));
			}
			return preco.multiply(BigDecimal.valueOf(quantidade));
		}
	},
	SHOW {

		@Override
		public BigDecimal calcula(Sessao sessao, Integer quantidade) {
			BigDecimal preco = sessao.getPreco();
			// quando estiver acabando os ingressos...
			if ((sessao.getTotalIngressos() - sessao.getIngressosReservados())
					/ sessao.getTotalIngressos().doubleValue() <= 0.05) {
				preco = preco.add(preco.multiply(BigDecimal.valueOf(0.10)));
			}
			return preco.multiply(BigDecimal.valueOf(quantidade));
		}
	},
	TEATRO {

		@Override
		public BigDecimal calcula(Sessao sessao, Integer quantidade) {
			BigDecimal preco = sessao.getPreco();
			return preco.multiply(BigDecimal.valueOf(quantidade));
		}
	},
	BALLET {

		@Override
		public BigDecimal calcula(Sessao sessao, Integer quantidade) {
			BigDecimal preco = sessao.getPreco();
			// quando estiver acabando os ingressos...
			if ((sessao.getTotalIngressos() - sessao.getIngressosReservados())
					/ sessao.getTotalIngressos().doubleValue() <= 0.50) {
				preco = preco.add(preco.multiply(BigDecimal.valueOf(0.20)));
			}

			if (sessao.getDuracaoEmMinutos() > 60) {
				preco = preco.add(preco.multiply(BigDecimal.valueOf(0.10)));
			}
			return preco.multiply(BigDecimal.valueOf(quantidade));
		}
	},
	ORQUESTRA {

		@Override
		public BigDecimal calcula(Sessao sessao, Integer quantidade) {
			BigDecimal preco = sessao.getPreco();
			// quando estiver acabando os ingressos...
			if ((sessao.getTotalIngressos() - sessao.getIngressosReservados())
					/ sessao.getTotalIngressos().doubleValue() <= 0.50) {
				preco = sessao.getPreco().add(
						sessao.getPreco().multiply(BigDecimal.valueOf(0.20)));
			} else {
				preco = sessao.getPreco();
			}

			if (sessao.getDuracaoEmMinutos() > 60) {
				preco = preco.add(sessao.getPreco().multiply(
						BigDecimal.valueOf(0.10)));
			}
			return preco.multiply(BigDecimal.valueOf(quantidade));
		}
	};

	public abstract BigDecimal calcula(Sessao sessao, Integer quantidade);
/*
	private static BigDecimal calculaPrecoDaSessao(Sessao sessao, double nivelAcabandoIngressos, double agioAcabandoIngressos, Integer duracaoLonga, double agioDuracaoLonga) {

		BigDecimal preco = sessao.getPreco();
		
		// quando estiver acabando os ingressos...
		if ((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= nivelAcabandoIngressos) {
			preco = preco.add(preco.multiply(BigDecimal.valueOf(agioAcabandoIngressos)));
		}

		if (sessao.getDuracaoEmMinutos() > duracaoLonga) {
			preco = preco.add(preco.multiply(BigDecimal.valueOf(agioDuracaoLonga)));
		}
		return preco;
	}
*/	
}
