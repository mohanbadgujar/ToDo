var toDo = angular.module('toDo');

toDo.factory('resetPasswordService', function($http, $location) {

	var abc = {};
	
	abc.resetUser = function(user) {
		return $http({
			method : "POST",
			url : 'resetPasswords',
			data : user,
			headers:{'resettoken': user.token}
		})
	}
	return abc;
});
