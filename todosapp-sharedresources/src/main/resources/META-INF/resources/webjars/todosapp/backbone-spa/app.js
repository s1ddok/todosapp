var todosapp = todosapp || {};

todosapp.model.User = Backbone.Model.extend({
    url: '/api/users/self'
});

$(function() {	
	
	todosapp.templates = {};
    todosapp.authentificated = false;
    todosapp.fetched = false;
    todosapp.collection =  new todosapp.model.TodoCollection();   
    todosapp.router = new todosapp.TodoRouter();
    
    todosapp.collection.once('sync', function() {
        todosapp.fetched = true;
    });
    
    todosapp.collection.fetch();
    Backbone.history.start();

    var currentUser = (new todosapp.model.User()).fetch({
        success: function(response) {
            var data = response.toJSON();
            console.log(data);
            $("#username").text(data.name);
        }
    })


});