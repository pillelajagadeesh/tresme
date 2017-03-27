'use strict';

describe('Controller Tests', function() {

    describe('Sensordata Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSensordata, MockApplication, MockSensor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSensordata = jasmine.createSpy('MockSensordata');
            MockApplication = jasmine.createSpy('MockApplication');
            MockSensor = jasmine.createSpy('MockSensor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Sensordata': MockSensordata,
                'Application': MockApplication,
                'Sensor': MockSensor
            };
            createController = function() {
                $injector.get('$controller')("SensordataDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tresiotApp:sensordataUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
