var toDo = angular.module('toDo', [ 'ui.router' ]);

toDo.config([ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {

			$stateProvider.state('register', {
				url : '/register',
				templateUrl : 'template/register.html',
				controller : 'registerController'
			});

			$stateProvider.state('login', {
				url : '/login',
				templateUrl : 'template/login.html',
				controller : 'loginController'
			});
	
			$stateProvider.state('forgotPassword', {
				url : '/forgotPassword',
				templateUrl : 'template/forgotPassword.html',
				controller : 'forgotPasswordController'
			});
			
			$stateProvider.state('/resetPassword', {
				url : '/resetPassword',
				templateUrl : 'template/resetPassword.html',
				controller : 'resetPasswordController'
			});

			$stateProvider.state('/dummyPage', {
				url : '/dummyPage',
				templateUrl : 'template/dammyPage.html',
				controller : 'dammyPageController'
			});
			
			$stateProvider.state('home', {
				url : '/home',
				templateUrl : 'template/home.html',
				controller : 'homeController'
			});
			
			$stateProvider.state('archive', {
				url : '/archive',
				templateUrl : 'template/archive.html',
				controller : 'archiveController'
			});

			$urlRouterProvider.otherwise('login');
		} ]);
