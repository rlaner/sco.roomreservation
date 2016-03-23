
angular.module('starter.controllers', ['starter.services'])

.controller('AppCtrl',  function($scope, $http, $ionicModal, $ionicPopup, $timeout, Login) {

  $scope.loginData = {};
  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.login_modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.login_modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.login_modal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    console.log('Doing login', JSON.stringify($scope.loginData));
    Login.doLogin($scope.loginData);
    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
    }, 200);
  };

})

.controller('EventCtrl', function($http, $state, $scope, $timeout, $ionicModal, $ionicLoading, RoomList, $stateParams, Login) {
  $scope.room = $stateParams.room;

    /* event sources array*/

    $scope.master = {};

    $scope.addEvent = function(event) {

        var loginData = Login.getUser();
        $scope.master = angular.copy(event);
        $scope.master.roomName = $scope.room.fields.titolo.value;
        $scope.master.title = 'Richiesta sala a nome ' + loginData.username;
        $scope.master.name = loginData.username;
        $scope.master.email = loginData.email;
        console.log("add event " + JSON.stringify($scope.master));
        $ionicLoading.show();
        RoomList.addEvent($scope.room.fields.calendario_richieste.value, $scope.master).then(function() {
          $scope.message = "Evento creato correttamente";
        }, function(err) {
          console.log(err);
          $scope.message = "Errore nella creazione dell'evento";
        }).finally(function() {
          $ionicLoading.hide();
        });
    };


})

.controller('RoomlistCtrl', function($http, $scope, $ionicPopup, $ionicLoading, RoomList) {

    $ionicLoading.show();
    RoomList.getRoomList().then(function(response) {
      $scope.roomlist = response.data;
    }).finally(function(){
      $ionicLoading.hide();
    });

})

.controller('RoomCtrl', function($scope, $state, RoomList, $ionicPopup, $timeout, $ionicModal, $ionicLoading, $stateParams, Login, uiCalendarConfig) {
    console.log($stateParams);

    $ionicLoading.show();
    RoomList.getRoom($stateParams.nodeId).then(function(response){
      $scope.room = response.data;
      console.log("received " + JSON.stringify($scope.room));
    }).finally(function(){
      $ionicLoading.hide();
    });

    $scope.addEvent = function() {

        if ($scope.room.fields == undefined) {
            console.log("selectedRoom undefined");
            $state.go('app.roomlist');
            return;
        }

        $scope.loginData = Login.getUser();
        if (!$scope.loginData || !$scope.loginData.email) {
            console.log("email undefined");
            $scope.login();
            return;
        }

        //$scope.showEvent();
        $state.go('app.event', {room: $scope.room});

    };

    $scope.initCalendars = function() {
      $scope.eventSourceRichieste = {
              url: RoomList.getServerURL()+"/eventList/" + $scope.room.fields.calendario_richieste.value+ '/',
              className: 'gcal-event',           // an option!
              currentTimezone: 'Europe/Rome', // an option!
              color: 'green'
      };

      $scope.eventSourcePrenotazioni = {
              url: RoomList.getServerURL()+"/eventList/" + $scope.room.fields.calendario_prenotazioni.value+ '/',
              className: 'gcal-event',           // an option!
              currentTimezone: 'Europe/Rome', // an option!
              color: 'orange'
      };
      $scope.eventSources = [$scope.eventSourceRichieste, $scope.eventSourcePrenotazioni];
    }


    $scope.clickEvent = function(event, jsEvent, view) {
        console.log('clickEvent ' + JSON.stringify(event));
                          uiCalendarConfig.calendars.calendar.fullCalendar('render');


        if ($scope.room.fields == undefined) {
            console.log("selectedRoom undefined");
            $state.go('app.roomlist');
            return;
        }

        $scope.loginData = Login.getUser();
        if ($scope.loginData.email == undefined) {
            console.log("email undefined");
            $scope.login();
            return;
        }


        var confirmPopup = $ionicPopup.confirm({
            title: 'Cancella prenotazione',
            template: '<div class="text-center">Sei sicuro di cancellare la prenotazione \'' + event.title+ '\'?</div>'
         });

        confirmPopup.then(function(res) {
             if(res) {
                console.log('ok pressed', res);
                console.log("delete event " + JSON.stringify($scope.master));

                var res = event.source.url.split('\/');
                console.log(res);
                var calendarId = res[res.length-2];
                console.log("calendarId -> " + calendarId);

                $ionicLoading.show();
                RoomList.deleteEvent(calendarId, $scope.loginData.email, event.id)
                .then(function(response) {
                    console.log("received " + JSON.stringify(response));
                    $scope.message = "Evento rimosso correttamente";
                    var room = $scope.room;
                    $scope.room = null;
                    $timeout(function() {
                      $scope.room = room;
                    }, 100);
                }, function(response) {
                    console.error('ERR' + JSON.stringify(response));
                    $scope.message = "Errore mella cancellazione dell'evento";
                }).finally(function() {
                  $ionicLoading.hide();
                });
             } else {
               console.log('You are not sure');
             }
        });






    };

    $scope.clickDay = function(date, allDay, jsEvent, view) {
      console.log('clickDay ' + JSON.stringify(date));
    };

    /* config object */
    $scope.uiConfig = {
        calendar:{
            lang: 'it',
            height: 380,
            selectable: false,
            editable: true,
            header:{
              left: 'month agendaWeek agendaDay',
              center: 'title',
              right: 'today prev,next'
            },
            eventClick: $scope.clickEvent,
            dayClick: $scope.clickDay
        }
    };

})
