(function() {
    'use strict';

    angular
        .module('tresiotApp')
        .controller('EventdataDialogController', EventdataDialogController);

    EventdataDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Eventdata', 'Application', 'Applicationevent', 'Applicationview'];

    function EventdataDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Eventdata, Application, Applicationevent, Applicationview) {
        var vm = this;

        vm.eventdata = entity;
        vm.clear = clear;
        vm.save = save;
        vm.applications = Application.query();
        vm.applicationevents = Applicationevent.query();
        vm.applicationviews = Applicationview.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.eventdata.id !== null) {
                Eventdata.update(vm.eventdata, onSaveSuccess, onSaveError);
            } else {
                Eventdata.save(vm.eventdata, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tresiotApp:eventdataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
