App.factory("dataService", ["$http", function ($http) {
	return {
		carrega: function() {
			return $http.get(contextPath + "/edital/summary");
		}
	};
}]);
