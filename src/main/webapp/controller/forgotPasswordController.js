var toDo = angular.module('toDo');

toDo.controller('forgotPasswordController', function($scope, forgotPasswordService,$location){
	$scope.forgotUser = function(){
		var a=forgotPasswordService.forgotUser($scope.user,$scope.error);
			a.then(function(response) {
				
				var token = response.data.msg;
				
				//localStorage.setItem('resettoken',response.data.msg)
				
				$location.path("/resetPassword");
				
			},function(response){
				$scope.error=response.data.msg;
			});
	}
	
});
