(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('SensorGraphController', SensorGraphController);

    SensorGraphController.$inject = ['$scope', 'Principal','ParseLinks', '$state','$rootScope','pagingParams', 'paginationConstants','$filter', 'SensorgraphService'];

    function SensorGraphController ($scope, Principal, ParseLinks, $state,$rootScope,pagingParams, paginationConstants,$filter,SensorgraphService) {
        var vm = this;  
        vm.sensorbargraph = sensorbargraph;
        vm.sensorlinegraph = sensorlinegraph;
        vm.openCalendar = openCalendar;
        vm.datePickerOpenStatus = {};
        vm.today = today;
        vm.today();
        vm.fromDate = null;
        vm.toDate = null;
        var dateFormat = 'hh:mm';
        vm.bargraphpresent= false;
        
        function today () {
            var today = new Date();
            vm.toDate = new Date(today.getFullYear(), today.getMonth(), today.getDate());
            vm.fromDate = new Date(today.getFullYear(), today.getMonth(), today.getDate());
        }
        $scope.tab = 1;
        

		$scope.setTab = function(newTab){
			$scope.tab = newTab;
		};
       $scope.isSet = function(tabNum){
    	  return $scope.tab === tabNum;
		  
		};
		
		SensorgraphService.getclienIdlist(function(response){
			vm.clientIdList = response;
		});
		SensorgraphService.getapplicationlist(function(response){
			vm.applicationList = response;
	    });
		SensorgraphService.getsensorlist(function(response){
			vm.sensorList = response;
		
	    });
		
		$('#bargraphdiv').hide();
		$('#bargraphmessage').hide();
        function sensorbargraph(){
        	
        	var datainput ={"clientId":vm.barclientId, "applicationId":vm.barapplicationId,"sensorId":vm.barsensorId};
        	
        	SensorgraphService.sensorbargraph(datainput,function(data){
        		 $scope.sensor=null;
        		
        		if(data.sensorBarData.length>0)
            	{
        			vm.bargraphpresent=true;
        			
        			$('#bargraphdiv').show();
        			$('#bargraphmessage').hide();
  				  $scope.sensor = data.sensorBarData;
        			$scope.count=[];
  					 $scope.label=[];
  					angular.forEach($scope.sensor, function(value, key){
  						$scope.count.push(value.sensorcount);
  						$scope.label.push(value.sensorlabel);
  						
  				   });
  					Highcharts.chart('sensorbar', {
  					chart: {
  						type: 'column'
  					},
  					title: {
  						text: 'Sensor Data'
  					},
  					xAxis: {
  						categories: $scope.label //Getting labels
  					},
  					yAxis: {
  						min: 0,
  						 title: {
  			                text: 'Sensor Data Count'
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
  						pointFormat: 'Sensor Data Count: {point.stackTotal}'
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
        		$('#bargraphdiv').hide();
        		$('#bargraphmessage').show();
        	}
  				  
          	
        	});
            }
        
        $('#linegraphdiv').hide();
		$('#linegraphmessage').hide();
        function sensorlinegraph(){
        		//var datainput ={"applicationId":vm.applicationId, "fromTime":1477938600000, "toTime": 1483554600000};
        	var datainput ={"applicationId":vm.lineapplicationId, "fromTime":vm.fromDate.getTime(), "toTime": vm.toDate.getTime(),
        			"clinetId":vm.lineclientId,"sensorId":vm.linesensorId};
        	
        	SensorgraphService.sensorlinegraph(datainput,function(data){                         
        		if(data.length>0)
            	{
        			$('#linegraphdiv').show();
        			$('#linegraphmessage').hide();
        			
  				  $scope.sensorline = data;
        		     $scope.sensorName=[];
  					 $scope.sensorValue=[];
  					 $scope.createTime=[];
  					angular.forEach($scope.sensorline, function(value, key){
  						$scope.sensorName.push(value.sensorName);
  						$scope.sensorValue.push(value.sensorValue);
  						$scope.createTime.push($filter('date')(value.createTime, dateFormat));
  				   });
  					
        		Highcharts.chart('sensorline', {
        	        title: {
        	            text: 'Sensor Data',
        	            x: -20 //center
        	        },
        	        xAxis: {
        	            categories: $scope.createTime
        	        },
        	        yAxis: {
        	            title: {
        	                text: 'Sensor Data Value'
        	            },
        	            plotLines: [{
        	                value: 0,
        	                width: 1,
        	                color: '#808080'
        	            }]
        	        },
        	        tooltip: {
        	        	headerFormat: '<b>{point.x}</b><br/>',
  						pointFormat: 'Sensor Data Count: {point.y}'
        	            
        	        },
        	        legend: {
        	            layout: 'vertical',
        	            align: 'right',
        	            verticalAlign: 'middle',
        	            borderWidth: 0
        	        },
        	        series: [{
        	            name: '',
        	            data: $scope.sensorValue
        	        }]
        	    });
        	}else{
        		$('#linegraphdiv').hide();
        		$('#linegraphmessage').show();
            	}
  					
  				
  				  
          	
        	});
            }
        
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
		}
    
})();
