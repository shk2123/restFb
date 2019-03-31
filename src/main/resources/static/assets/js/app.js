angular.module('SpringMusic', ['currencies', 'errors', 'status', 'info', 'ngRoute', 'ui.directives']).
    config(function ($locationProvider, $routeProvider) {
        // $locationProvider.html5Mode(true);

        $routeProvider.when('/errors', {
            controller: 'ErrorsController',
            templateUrl: 'assets/templates/errors.html'
        });
        $routeProvider.otherwise({
            controller: 'CurrencyController',
            templateUrl: 'assets/templates/albums.html'
        });
    }
);



$(document).ready(function(){
	
});

/*function getStockQuote(){

	
	$.ajax({url: "/getstockquote/AAPL", success: function(result){
        console.log(result);
       // var data = JSON.parse(result.toString());
        $("#dataTable").jsonTable({
        	head : ['#','Meta Data','Time Series (Daily)']
        	});
        alert(result.length);
    	$("#dataTable").jsonTableUpdate({source : result,
    		rowClass : "rowClass",
    		callback : function(){
    		}});
    	//alert("completed");
    }, error: function(result){
    	alert(result);
    }});
	
	
	
	
}
*/