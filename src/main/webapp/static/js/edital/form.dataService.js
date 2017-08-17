App.factory("dataService", ["$http", function ($http) {
	return {
		carrega: function(id) {
			return $http.get(contextPath + "/edital/" + id);
		}
	};
}]);
