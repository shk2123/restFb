angular.module('currencies', [ 'ui.bootstrap' ])
	
function CurrencyController($scope,  $http, $log,$filter) {
	$scope.stocks = [];
	$scope.rates = [];

	
	

	function clone(obj) {
		return JSON.parse(JSON.stringify(obj));
	}

	$scope.getStockQuote = function () {
		$scope.resultString = "";
		
		$http({
			method : 'GET',
			url : 'getComments'
		}).success(function(data, status) {
			$scope.resultString = "<table class='table table-striped table-bordered'>";
			$scope.parseData($scope.stocks,data );
			$scope.loadData();

		}).error(function(data, status, headers, config) {
			$log.warn(status);
			alert(data);
		});
		

	}

	$scope.setAlbumsView = function(viewName) {
		$scope.albumsView = "assets/templates/" + viewName + ".html";
	};

	$scope.init = function() {
		
		$scope.setAlbumsView("list");
		$scope.sortField = "code";
		$scope.sortDescending = false;
	};
	
	$scope.resultString = "";
	$scope.parseData = function (array,data){
	
		for ( var key in data) {
			var obj = {};
			obj.code = key;
			$scope.resultString += "<tr><td>" + key + "</td>";
			obj.data = [];
			//obj.data = data[key];
			if(typeof data[key] == "string")
	        {
				$scope.resultString += "<td>" + data[key]; + "</td></tr>";
				obj.data = data[key];
	        }
	        else if(typeof data[key] == "object")
	        {
	        	$scope.resultString += "<td><table class='table table-striped table-bordered'>";
	        	var tempData = data[key];
	        	$scope.parseData(obj.data,tempData);
	        	$scope.resultString += "</table></td></tr>";
	        }
			//return obj;
			array .push(obj);
		}
		
	
	}
	
	
	
	$scope.loadData= function (){
		console.log($scope.resultString);
		$scope.resultString += "</table>"
		$("#table-div").html( $scope.resultString );
	}
	
	
	
}
