App.factory("formEditalDataService", ["$http", function ($http) {
	return {
		carrega: function(id) {
			return $http.get(contextPath + "/edital/" + id);
		},
		
		atualiza: function(edital) {
			return $http.post(contextPath + "/edital/", edital, { headers: { "X-CSRF-TOKEN": csrf.value }});
		}
	};
}]);
