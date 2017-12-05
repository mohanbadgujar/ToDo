var toDo = angular.module('toDo');

toDo
		.controller(
				'homeController',
				function($scope, homeService, $location) {

					getAllNotes();

					// checkLoginStatus();

					// Check Login Status of user
					function checkLoginStatus() {
						var b = homeService.checkLoginStatus('home', 'GET');
						b.then(function(response) {
							getAllNotes();
							console.log('User is authorized');

						}, function(response) {
							$location.path("/login");
						});
					}

					// Get All Notes from Database
					function getAllNotes() {
						var b = homeService.getData('note/allnotes', 'GET');
						b.then(function(response) {
							$scope.userNotes = response.data;
							console.log($scope.userNotes);

						}, function(response) {
							$scope.msg = response.data;
						});
					}

					// toggle side bar
					$scope.showSideBar = false;
					$scope.sidebarToggle = function() {
						if ($scope.showSideBar) {
							$scope.showSideBar = false;
							document.getElementById("mainWrapper").style.paddingLeft = "200px";
						} else {
							$scope.showSideBar = true;
							document.getElementById("mainWrapper").style.paddingLeft = "70px";
						}
					}

					// toggle AddNote box
					$scope.AddNoteBox = false;
					$scope.ShowAddNote = function() {
						$scope.AddNoteBox = true;
					}

					// addNote to database
					$scope.createNote = function() {

						$scope.notes = {};
						$scope.notes.title = document
								.getElementById("notetitle").innerHTML;
						$scope.notes.description = document
								.getElementById("noteDescription").innerHTML;

						if ($scope.notes.title != ""
								&& $scope.notes.description != "") {

							var b = homeService.addData('note/createnote','POST', $scope.notes);
							b.then(
											function(response) {
												document
														.getElementById("notetitle").innerHTML = "";
												document
														.getElementById("noteDescription").innerHTML = "";
												console.log('note added');
												getAllNotes();

											}, function(response) {
												$scope.msg = response.data;
											});
						}
					}

					// delete note from database
					$scope.deleteNote = function(note) {

						var url = "note/deletenote/" + note.noteId;

						var c = homeService.deleteNote(url, 'DELETE');
						c.then(function(response) {

							console.log('note deleted');
							getAllNotes();

						}, function(response) {
							$scope.msg = response.data;
						});
					}

					// Update note from database
					$scope.updateNote = function(note) {

						note.title = document.getElementById('edittitle').innerHTML;
						note.description = document.getElementById('editdescription').innerHTML;

						var b = homeService.updateNote('note/updatenote',
								'PUT', note);
						b.then(function(response) {

							console.log('note Updated');
							getAllNotes();

						}, function(response) {
							$scope.msg = response.data;
						});
					}

					// logout user
					$scope.logout = function() {
						localStorage.removeItem('token');
					}
					
					// archive note
					$scope.archiveNote = function(note) {

						var url = "note/archive/" + note.noteId;

						var c = homeService.archiveNote(url, 'PUT');
						c.then(function(response) {

							console.log('note archive');
							getAllNotes();

						}, function(response) {
							$scope.msg = response.data;
						});
					}	
					
					
					
				});
