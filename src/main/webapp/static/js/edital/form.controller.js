App.controller("formController", function ($scope, formEditalDataService, $log, $window) {
	var self = this;

	/**
	 * Envia o edital e volta para a página de lista
	 */
	this.submeteEdital = function() {
		formEditalDataService.atualiza(self.edital).then(function(data) {
			if (!checkForErrors(data.data)) {
				window.location = contextPath + "/edital/list?message=edital.form.dados.salvos.sucesso";
			}
		});
	}

	/**
	 * Volta para a pagina de lista
	 */
	this.retornaLista = function() {
		window.location = contextPath + "/edital/list";
	}
	
	/**
	 * Programa principal
	 */
	self.edital = { id: id, nome: "Nome", status: 0, notaMinimaAlinhamento: 70 };
	self.edital.provas = [{codigo: "CODI", nome: "Nome", notaMinima: 70, dispensavel: false, questoes: [10, 20, 30, 40] }, {codigo: "COD2", nome: "Nome 2", notaMinima: 80, dispensavel: true, questoes: [20, 80] }];

	if (id != -1) {
		formEditalDataService.carrega(id).then(function(data) {
			self.edital = data.data.data;
			$log.info(self.edital);
		});
	}
});