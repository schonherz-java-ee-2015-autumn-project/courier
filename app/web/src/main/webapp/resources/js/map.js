var destination;
var currentPosition;
var directionsDisplay;
var directionsService = new google.maps.DirectionsService();
var map;
var service = new google.maps.DistanceMatrixService;
function initialize() {
	directionsDisplay = new google.maps.DirectionsRenderer();
	map = new google.maps.Map(document.getElementById('map'), {
		center : {
			lat : -34.397,
			lng : 150.644
		},
		zoom : 14
	});
	directionsDisplay.setMap(map);

	// Try HTML5 geolocation.
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			currentPosition = {
				lat : position.coords.latitude,
				lng : position.coords.longitude
			};
			/*
			 * var marker = new google.maps.Marker({ position : currentPosition,
			 * map : map, title : 'Hello World!' });
			 */

			map.setCenter(currentPosition);
		}, function() {
			handleLocationError(true, infoWindow, map.getCenter());
		});
	} else {
		// Browser doesn't support Geolocation
		handleLocationError(false, infoWindow, map.getCenter());
	}
	var geocoder = new google.maps.Geocoder();
	geocodeAddress(geocoder, map);
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
	infoWindow.setPosition(pos);
	infoWindow
			.setContent(browserHasGeolocation ? 'Error: The Geolocation service failed.'
					: 'Error: Your browser doesn\'t support geolocation.');
}

function geocodeAddress(geocoder, resultsMap) {
	var address = '#{mapBean.selectedCargo.restaurant.address}';
	geocoder.geocode({
		'address' : address
	}, function(results, status) {
		destination = results[0].geometry.location;
		if (status === google.maps.GeocoderStatus.OK) {
			/*
			 * resultsMap.setCenter(destination); var marker = new
			 * google.maps.Marker({ map : resultsMap, position : destination });
			 */
			calcRoute();
		} else {
			alert('Geocode was not successful for the following reason: '
					+ status);
		}
	});
}
function calcRoute() {
	var start = currentPosition;
	var end = destination;
	var request = {
		origin : start,
		destination : end,
		travelMode : google.maps.TravelMode.DRIVING
	};
	directionsService.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
			service.getDistanceMatrix({
				origins : [ currentPosition ],
				destinations : [ destination ],
				travelMode : google.maps.TravelMode.DRIVING,

			}, callback);

			function callback(response, status) {
				if (status == google.maps.DistanceMatrixStatus.OK) {
					var origins = response.originAddresses;
					var destinations = response.destinationAddresses;

					for (var i = 0; i < origins.length; i++) {
						var results = response.rows[i].elements;
						for (var j = 0; j < results.length; j++) {
							var element = results[j];
							var distance = element.distance.text;
							var duration = element.duration.text;
							var from = origins[i];
							var to = destinations[j];
							document.getElementById('output').innerHTML = 'hi'
									+ distance + ' duration ' + duration;
						}
					}
				}
			}

		}
	});
}