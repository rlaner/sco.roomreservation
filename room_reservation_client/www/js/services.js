angular.module('starter.services', [])

.factory('Login', function ($http) {
  return {
    doLogin: function(user) {
      localStorage.roomReservation_user = user.username;
      localStorage.roomReservation_email = user.email;
    },
    getUser: function() {
      if (localStorage.roomReservation_user) {
        return {username: localStorage.roomReservation_user, email: localStorage.roomReservation_email}
      }
      return {};
    }
  };
})

.factory('RoomList', function ($http) {

  var SERVER_URL = "https://roomreservation.welive.smartcommunitylab.it";
//  var SERVER_URL = "http://localhost:8080"

  return {
    getServerURL: function() {
      return SERVER_URL;
    },
    getRoomList: function() {
        return $http({
          method: 'GET',
          url: SERVER_URL+'/roomList/'
        });

    },

    getRoom: function(nodeId) {
        // Simple GET request example:
      return $http({
          method: 'GET',
          url: SERVER_URL+'/room/' + nodeId + '/'
        });
    },


    addEvent: function(calendarId, event) {
        return $http({
             method: 'POST',
             url: SERVER_URL+'/eventAdd/' + calendarId + '/',
             headers: {
               'Content-Type': 'application/json'
             },
             data: event
        });
//          .then(function successCallback(response) {
//            // this callback will be called asynchronously
//            // when the response is available
//            console.log("received " + JSON.stringify(response));
//            $scope.message = "Evento creato correttamente";
//
//          }, function errorCallback(response) {
//            // called asynchronously if an error occurs
//            // or server returns response with an error status.
//            console.error('ERR', response.error);
//            $scope.message = "Errore nelal creazione dell'evento";
//        });
    },
    deleteEvent: function(calendarId, email, eventId) {
        return $http({
             method: 'DELETE',
             url: SERVER_URL+'/eventDelete/' + calendarId + '/' + eventId + '/'+ email + '/'
        });
    }

  }
});
