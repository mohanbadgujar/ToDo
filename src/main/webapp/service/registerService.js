var toDo = angular.module('toDo');

toDo.factory('registerService', function($http, $location) {

	var abc = {};
	
	abc.registerUser = function(user) {
		return $http({
			method : "POST",
			url : 'register',
			data : user
		})
	}
	return abc;
});
