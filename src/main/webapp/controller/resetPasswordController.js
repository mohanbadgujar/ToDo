var toDo = angular.module('toDo');

toDo.controller('resetPasswordController', function($scope,resetPasswordService, $location) {

	$scope.resetUser = function() {

		if ($scope.user.password != $scope.user.repassword && $scope.user.token == null ) {
			$scope.IsMatch = true;
			return false;
		}
		
		$scope.IsMatch = false;

		var a = resetPasswordService.resetUser($scope.user, $scope.error);

		a.then(function(response) {
			//localStorage.setItem('token', response.data.msg)
			$location.path("/login");

		}, function(response) {
			$scope.error = response.data.msg;
		});
	}

});
