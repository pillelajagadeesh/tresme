(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('SensordataDetailController', SensordataDetailController);

    SensordataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sensordata', 'Application', 'Sensor'];

    function SensordataDetailController($scope, $rootScope, $stateParams, previousState, entity, Sensordata, Application, Sensor) {
        var vm = this;

        vm.sensordata = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tresiotApp:sensordataUpdate', function(event, result) {
            vm.sensordata = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
