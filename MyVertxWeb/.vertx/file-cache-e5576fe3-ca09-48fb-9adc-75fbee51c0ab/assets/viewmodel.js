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