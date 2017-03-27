(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('EventGraphController', EventGraphController);

    EventGraphController.$inject = ['$scope', 'Principal','ParseLinks', '$state','$rootScope','pagingParams', 'paginationConstants','$filter', 'EventgraphService'];

    function EventGraphController ($scope, Principal, ParseLinks, $state,$rootScope,pagingParams, paginationConstants,$filter,EventgraphService) {
        var vm = this;  
        vm.eventbargraph = eventbargraph;
        
        EventgraphService.getclienIdlist(function(response){
        	vm.clientIdList = response;
		});
        EventgraphService.getapplicationlist(function(response){
			vm.applicationList = response;
		
	    });
        EventgraphService.geteventlist(function(response){
			vm.eventList = response;
		
	    });
       
        $('#eventgraphdiv').hide();
		$('#eventgraphmessage').hide();
        function eventbargraph(){
        
        	var datainput ={"clientId":vm.clientId, "applicationId":vm.eventapplicationId, "eventId":vm.eventId};
        	EventgraphService.eventbargraph(datainput,function(data){
        		if(data.eventBarData.length>0)
            	{	
        			$('#eventgraphdiv').show();
        			$('#eventgraphmessage').hide();
  				  $scope.event = data.eventBarData;
        			$scope.count=[];
  					 $scope.label=[];
  					angular.forEach($scope.event, function(value, key){
  						$scope.count.push(value.eventcount);
  						$scope.label.push(value.eventlabel);
  						
  				   });
  					Highcharts.chart('eventbar', {
  					chart: {
  						type: 'column'
  					},
  					title: {
  						text: 'Event Data'
  					},
  					xAxis: {
  						categories: $scope.label //Getting labels
  					},
  					yAxis: {
  						min: 0,
  						 title: {
  			                text: 'Event Data Count'
  			            },
  						stackLabels: {
  							enabled: false,
  							style: {
  								fontWeight: 'bold',
  								color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
  							}
  						}
  					},
  					credits: {
  						  enabled: false
  					  },
  					tooltip: {
  						headerFormat: '<b>{point.x}</b><br/>',
  						pointFormat: 'Event Data Count: {point.stackTotal}'
  					},
  					plotOptions: {
  						column: {
  							stacking: 'normal',
  							dataLabels: {
  								enabled: false,
  								style: {
  								
  							}
  							}
  						}
  					},
  					series: [{
  						data: $scope.count,
  						color:'#4ba704'
  					}]
  				});
        	}else{
        		$('#eventgraphdiv').hide();
    			$('#eventgraphmessage').show();
  		        	}	
  				  
          	
        	});
            }
        
     
        
		}
    
})();
