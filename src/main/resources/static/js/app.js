"use strict";

(function(app_name) {
    var app = angular.module('app', ['ui.router'])
        .config(function($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.otherwise('/');
            $stateProvider.state('home', {
                url: '/',
                views: {
                    'item': {
                        templateUrl: 'view/item.html',
                        controller: 'ItemController',
                        controllerAs: 'ctl'
                    },
                    'items': {
                        templateUrl: 'view/items.html',
                        controller: 'ItemsController',
                        controllerAs: 'ctl'
                    }
                }
            })
        })
        .service('$items', ['$q', '$http', ItemsService])
        .controller('NavbarController', ['$q', '$scope', NavbarController])
        .controller('ItemsController', ['$q', '$scope', '$items', ItemsController])
        .controller('ItemController', ['$q', '$scope', '$items', ItemController]);

    function ItemsService($q, $http) {
        var url = '../api';
        var data = {};
        var current = {};
        return {
            current: getCurrent,
            setCurrent: setCurrent,
            data: getData,
            sync: getItems,
            del: delItems
        };

        function getItems() {
            $http({
                    method: 'GET',
                    url: url
                })
                .then(function(result) {
                    console.log(result);
                    _.assign(data, result.data);
                    return data;
                })
                .catch(function(result) {
                    console.error(result);
                    return result;
                })
        }

        function delItems(item) {
                    var urlDel = url + '/' + current.id;
                    $http({
                            method: 'DELETE',
                            url: urlDel
                        })
                        .then(function(result){
                            console.log(result);
                            getItems();
                        })
                        .catch(function(result) {
                            console.error(result);
                            return result;
                        })
                }


        function getCurrent() {
            return current;
        }

        function getData() {
            return data;
        }

        function setCurrent(item) {
            _.assign(current, item);
        }

    }


    function NavbarController($q, $scope) {
        var ctl = this;
        ctl.title = 'Peer Chat';
        ctl.peerlist = 'List of peers';
        ctl.home = 'Start';
        ctl.chats = 'Current Chats';
    }


    function ItemController($q, $scope, $items) {
        var ctl = this;
        ctl.items = $items.data();
        ctl.current = $items.current();
        ctl.items_filter = "";
        ctl.deleteItem = deleteItem;
        $items.sync();

        function deleteItem(item) {
              $items.del(item);
        }
    }

    function ItemsController($q, $scope, $items) {
        var ctl = this;
        ctl.items = $items.data();
        ctl.items_filter = "";
        ctl.showDetail = showDetail;
        ctl.refresh = refresh;
        $items.sync();

        function refresh() {
            $items.sync();
        }

        function showDetail(item) {
            $items.setCurrent(item);
        }
    }



})('app');
