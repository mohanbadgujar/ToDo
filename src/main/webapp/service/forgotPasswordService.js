var toDo = angular.module('toDo');

toDo.factory('forgotPasswordService', function($http, $location) {

	var abc = {};
	
	abc.forgotUser = function(user) {
		return $http({
			method : "POST",
			url : 'forgotPassword',
			data : user,
			
		})
	}
	return abc;
});
