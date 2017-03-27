(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('EventdataDeleteController',EventdataDeleteController);

    EventdataDeleteController.$inject = ['$uibModalInstance', 'entity', 'Eventdata'];

    function EventdataDeleteController($uibModalInstance, entity, Eventdata) {
        var vm = this;

        vm.eventdata = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Eventdata.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
