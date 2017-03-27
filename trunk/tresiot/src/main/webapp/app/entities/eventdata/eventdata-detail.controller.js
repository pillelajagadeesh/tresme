(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('EventdataDetailController', EventdataDetailController);

    EventdataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Eventdata', 'Application', 'Applicationevent', 'Applicationview'];

    function EventdataDetailController($scope, $rootScope, $stateParams, previousState, entity, Eventdata, Application, Applicationevent, Applicationview) {
        var vm = this;

        vm.eventdata = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tresiotApp:eventdataUpdate', function(event, result) {
            vm.eventdata = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
