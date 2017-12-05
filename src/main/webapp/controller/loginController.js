var toDo = angular.module('toDo');

toDo.controller('loginController', function($scope, loginService,$location){
	
	$scope.loginUser = function(){
		var a=loginService.loginUser($scope.user,$scope.error);
			a.then(function(response) {
				console.log(response.data.msg);
				localStorage.setItem('token',response.data.msg)
				$location.path("/home");
			},function(response){
				$scope.error=response.data.msg;
			});
	}

	$scope.redirectreg = function() {
		$location.path("register");
	}

});



















/*var toDo = angular.module('toDo');

toDo.controller('loginController', function($scope,$http,$location){
	$scope.loginUser = function(user){
		$http({
		       method: 'POST',
		       url: 'login',
		       data: user,
		        headers: {
		            'Content-Type': 'application/json'
		   }}).then(function(data){
				console.log("data",data)
			}) 
		$http({
			method : "POST",
			url : 'login',
			headers:{
				'Content-Type': 'application/json'
			},
			data : user
		}).then(function(){
			console.log("data")
		})
		var a=loginService.loginUser($scope.user,$scope.error);
			a.then(function(response) {
				if(response.data){
					console.log("HI am in controller ");
					console.log(response.data.msg);
					localStorage.setItem('token',response.data.msg)
					$location.path("/home");
				}
				else{
					console.log(error);
				}
				
			});
	}
});*/