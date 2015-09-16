<%-- 
    Document   : geojson
    Created on : 15 sept. 2015, 14:08:06
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>GeoJSON example</title>
        <script src="js/jquery-1.11.2.min.js"></script>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <script src="js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="css/ol.css" type="text/css">
        <script src="js/ol.js"></script>
    </head>
    <body>
        <div class="container-fluid">

            <div class="row-fluid">
                <div class="span12">
                    <div id="map" class="map"></div>
                </div>
            </div>

            <div>
                <button id="send">Send to postgis</button>
            </div>
        </div>
        <script>

            var image = new ol.style.Circle({
                radius: 5,
                fill: null,
                stroke: new ol.style.Stroke({color: 'red', width: 1})
            });

            var styles = {
                'Point': [new ol.style.Style({
                        image: image
                    })],
                'LineString': [new ol.style.Style({
                        stroke: new ol.style.Stroke({
                            color: 'green',
                            width: 1
                        })
                    })],
                'MultiLineString': [new ol.style.Style({
                        stroke: new ol.style.Stroke({
                            color: 'green',
                            width: 1
                        })
                    })],
                'MultiPoint': [new ol.style.Style({
                        image: image
                    })],
                'MultiPolygon': [new ol.style.Style({
                        stroke: new ol.style.Stroke({
                            color: 'yellow',
                            width: 1
                        }),
                        fill: new ol.style.Fill({
                            color: 'rgba(255, 255, 0, 0.1)'
                        })
                    })],
                'Polygon': [new ol.style.Style({
                        stroke: new ol.style.Stroke({
                            color: 'blue',
                            lineDash: [4],
                            width: 3
                        }),
                        fill: new ol.style.Fill({
                            color: 'rgba(0, 0, 255, 0.1)'
                        })
                    })],
                'GeometryCollection': [new ol.style.Style({
                        stroke: new ol.style.Stroke({
                            color: 'magenta',
                            width: 2
                        }),
                        fill: new ol.style.Fill({
                            color: 'magenta'
                        }),
                        image: new ol.style.Circle({
                            radius: 10,
                            fill: null,
                            stroke: new ol.style.Stroke({
                                color: 'magenta'
                            })
                        })
                    })],
                'Circle': [new ol.style.Style({
                        stroke: new ol.style.Stroke({
                            color: 'red',
                            width: 2
                        }),
                        fill: new ol.style.Fill({
                            color: 'rgba(255,0,0,0.2)'
                        })
                    })]
            };

            var styleFunction = function(feature, resolution) {
                return styles[feature.getGeometry().getType()];
            };

  /*          var geojsonObject = {
                'type': 'FeatureCollection',
                'crs': {
                    'type': 'name',
                    'properties': {
                        'name': 'EPSG:3857'
                    }
                },
                'features': [
                    {
                        'type': 'Feature',
                        'geometry': {
                            'type': 'Point',
                            'coordinates': [0, 0]
                        }
                    },
                    {
                        'type': 'Feature',
                        'geometry': {
                            'type': 'LineString',
                            'coordinates': [[4e6, -2e6], [8e6, 2e6]]
                        }
                    },
                    {
                        'type': 'Feature',
                        'geometry': {
                            'type': 'LineString',
                            'coordinates': [[4e6, 2e6], [8e6, -2e6]]
                        }
                    },
                    {
                        'type': 'Feature',
                        'geometry': {
                            'type': 'Polygon',
                            'coordinates': [[[-5e6, -1e6], [-4e6, 1e6], [-3e6, -1e6]]]
                        }
                    },
                    {
                        'type': 'Feature',
                        'geometry': {
                            'type': 'MultiLineString',
                            'coordinates': [
                                [[-1e6, -7.5e5], [-1e6, 7.5e5]],
                                [[1e6, -7.5e5], [1e6, 7.5e5]],
                                [[-7.5e5, -1e6], [7.5e5, -1e6]],
                                [[-7.5e5, 1e6], [7.5e5, 1e6]]
                            ]
                        }
                    },
                    {
                        'type': 'Feature',
                        'geometry': {
                            'type': 'MultiPolygon',
                            'coordinates': [
                                [[[-5e6, 6e6], [-5e6, 8e6], [-3e6, 8e6], [-3e6, 6e6]]],
                                [[[-2e6, 6e6], [-2e6, 8e6], [0, 8e6], [0, 6e6]]],
                                [[[1e6, 6e6], [1e6, 8e6], [3e6, 8e6], [3e6, 6e6]]]
                            ]
                        }
                    }
                ]
            };
*/

            var geojsonObject =  {
                "type":"FeatureCollection",
                "features":[
                        {
                            "type":"Feature",
                            "geometry":{"type":"Point","coordinates":[-850441.5440293477,3973171.6279677567]},
                            "properties":null
                        }]
            };
            var vectorSource = new ol.source.Vector({
                features: (new ol.format.GeoJSON()).readFeatures(geojsonObject)
            });

            vectorSource.addFeature(new ol.Feature(new ol.geom.Circle([5e6, 7e6], 1e6)));

            var vectorLayer = new ol.layer.Vector({
                source: vectorSource,
                style: styleFunction
            });

            var map = new ol.Map({
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.OSM()
                    }),
                    vectorLayer
                ],
                target: 'map',
                controls: ol.control.defaults({
                    attributionOptions: /** @type {olx.control.AttributionOptions} */ ({
                        collapsible: false
                    })
                }),
                view: new ol.View({
                    center: [0, 0],
                    zoom: 2
                })
            });

            $(document).ready(function() {
                $('#send').click(function() {
                    //console.log(geojsonObject);
                    var geometries = geojsonObject.features;
                    var geoJson = [];
                    for (var index = 0, len = geometries.length; index < len; ++index) {
                        var geometry = geometries[index].geometry;
                        geoJson.push({
                            type: geometry.type,
                            coordinates: geometry.coordinates
                        });
                    }
                    //console.log(geoJson);
                    $.ajax({
                        url: "/PostGisWeb/geojson",
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            geo: JSON.stringify(geoJson)
                        },
                        success: function(data) {
                            console.log(data);
                        }
                    });
                });
            });

        </script>
    </body>
</html>