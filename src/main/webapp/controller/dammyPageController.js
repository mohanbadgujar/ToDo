var toDo = angular.module('toDo');

toDo.controller('dammyPageController', function($scope, dummyService,$location){
	
	redirect();
	
	function redirect (){
		var a=dummyService.redirect();
			a.then(function(response) {
				localStorage.setItem('token',response.data.msg)
				$location.path("/home");
			},function(response){
				$scope.error=response.data.msg;
			});
	}
});