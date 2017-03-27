(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('SensordataDeleteController',SensordataDeleteController);

    SensordataDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sensordata'];

    function SensordataDeleteController($uibModalInstance, entity, Sensordata) {
        var vm = this;

        vm.sensordata = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Sensordata.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
