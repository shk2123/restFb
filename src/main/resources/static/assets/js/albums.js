angular.module('currencies', [ 'ui.bootstrap' ])
	
function CurrencyController($scope,  $http, $log,$filter) {

	/*$scope.myPosts = [{ "id": 2128495817206205}, {"id": 2161100163945770 }, {"id":  2128494830539637 }, {"id":  2161098220612631} ,{"id":   2128493807206406 }];*/
	
	

	function clone(obj) {
		return JSON.parse(JSON.stringify(obj));
	}

	

	$scope.setAlbumsView = function(viewName) {
		$scope.albumsView = "assets/templates/" + viewName + ".html";
		$scope.listView = "assets/templates/list.html";
	};

	$scope.init = function() {
		
		

		$http({
			method : 'GET',
			url : 'chintoo/posts'
		}).success(function(data, status) {
			$scope.posts = data;
			
			console.log($scope.posts);

		}).error(function(data, status, headers, config) {
			$log.warn(status);
			alert(data);
		});
		
		$scope.setAlbumsView("grid");
		//$scope.sortField = "code";
		//$scope.sortDescending = false;
		
		
		
		
	};
	
	
	$scope.mySplit = function(string, nb) {
	    var array = string.split('_');
	    return array[nb];
	}
	
	
	
	$scope.getLikedPostsByName = function () {
		
		
		$http({
			method : 'GET',
			url : 'chintoo/getLikesByUserAcrossPost/' + $scope.userName
		}).success(function(data, status) {
			$scope.myPosts = data;
			
		}).error(function(data, status, headers, config) {
			$log.warn(status);
			alert(data);
		});
		

	}
	
	$scope.getCommentsPostsByName = function () {
		
		
		$http({
			method : 'GET',
			url : 'chintoo/getCommentsByUserAcrossPost/' + $scope.userNameC
		}).success(function(data, status) {
			$scope.myPosts = data;
			
		}).error(function(data, status, headers, config) {
			$log.warn(status);
			alert(data);
		});
		

	}
	
}
