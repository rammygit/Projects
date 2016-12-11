function Task(data) {
    this.title = ko.observable(data.title);
    this.isDone = ko.observable(data.isDone);
}

function AppViewModel(){
	this.firstName = ko.observable("ram");
	this.lastName = ko.observable("kumar");
	this.fullName = ko.computed(function(){
		return this.firstName()+" "+this.lastName();
	},this);
	this.capitalizeLastName = function(){
		var currentVal = this.lastName();
		this.lastName(currentVal.toUpperCase());
	};
}



ko.applyBindings(new AppViewModel());