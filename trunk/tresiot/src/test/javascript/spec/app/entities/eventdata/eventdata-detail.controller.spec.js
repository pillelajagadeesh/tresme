'use strict';

describe('Controller Tests', function() {

    describe('Eventdata Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEventdata, MockApplication, MockApplicationevent, MockApplicationview;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEventdata = jasmine.createSpy('MockEventdata');
            MockApplication = jasmine.createSpy('MockApplication');
            MockApplicationevent = jasmine.createSpy('MockApplicationevent');
            MockApplicationview = jasmine.createSpy('MockApplicationview');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Eventdata': MockEventdata,
                'Application': MockApplication,
                'Applicationevent': MockApplicationevent,
                'Applicationview': MockApplicationview
            };
            createController = function() {
                $injector.get('$controller')("EventdataDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tresiotApp:eventdataUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
