var toDo = angular.module('toDo');

toDo.factory('homeService', function($http, $location) {

	var abc = {};
	
	
	//check user status
	abc.checkLoginStatus = function(url,method) {
		return $http({
			method : method,
			url : url,
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	//get all notes form database
	abc.getData = function(url,method) {
		return $http({
			method : method,
			url : url,
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	//add note to database
	abc.addData = function(url,method,object) {
		return $http({
			method : method,
			url : url,
			data: object,
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	//Delete note from database
	abc.deleteNote = function(url,method) {
		return $http({
			method : method,
			url : url,
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	//Update note from database
	abc.updateNote = function(url,method,object) {
		return $http({
			method : method,
			url : url,
			data: object,
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	//archive note
	abc.archiveNote = function(url,method) {
		return $http({
			method : method,
			url : url,
			headers: {
				'token':localStorage.getItem('token')
			}
		})
	}
	
	return abc;

});