(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('SensorDetailController', SensorDetailController);

    SensorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sensor'];

    function SensorDetailController($scope, $rootScope, $stateParams, previousState, entity, Sensor) {
        var vm = this;

        vm.sensor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tresiotApp:sensorUpdate', function(event, result) {
            vm.sensor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
