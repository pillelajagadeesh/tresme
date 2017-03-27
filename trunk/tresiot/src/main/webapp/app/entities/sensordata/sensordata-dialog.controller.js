(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('SensordataDialogController', SensordataDialogController);

    SensordataDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sensordata', 'Application', 'Sensor'];

    function SensordataDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sensordata, Application, Sensor) {
        var vm = this;

        vm.sensordata = entity;
        vm.clear = clear;
        vm.save = save;
        vm.applications = Application.query();
        vm.sensors = Sensor.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sensordata.id !== null) {
                Sensordata.update(vm.sensordata, onSaveSuccess, onSaveError);
            } else {
                Sensordata.save(vm.sensordata, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tresiotApp:sensordataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
