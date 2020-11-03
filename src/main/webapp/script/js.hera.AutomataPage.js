$package("js.hera");

$include("js.hera.hub.Service");

js.hera.AutomataPage = function() {
	this.$super();

	this._actionsListView = this.getByName("actions-list-view");
	this._actionsListView.setAutoSelect(js.widget.ListControl.Select.CLICK);
	this._actionsListView.on("item-select", this._onActionSelect, this);

	this._actionDialog = this.getByName("action-dialog");
	this._actionForm = this._actionDialog.getByTag("form");

	this._loadActions();
};

js.hera.AutomataPage.prototype = {
	_loadActions : function() {
		this._reset();
		Service.getActionClassNames(this._onActionsLoaded, this);
	},

	_onActionsLoaded : function(actionClassNames) {
		var index = 0;
		var actions = actionClassNames.map(function(className) {
			return {
				id : ++index,
				name : className,
				description : className,
				picture : "/icons/lights.png"
			}
		});
		this._actionsListView.setObject(actions);
	},

	_onActionSelect : function(ev) {
		if (ev.selected) {
			var action = ev.value;
			Service.getSourceCode(action.name, function(code) {
				action.code = code;
				this._actionDialog.show();
				this._actionForm.setObject(action);
			}, this);
		}
	},

	_reset : function() {
		this._actionDialog.hide();
		this._actionForm.reset();
		this._actionsListView.deselectAll();
	},

	toString : function() {
		return "js.hera.AutomataPage";
	}
};
$extends(js.hera.AutomataPage, js.ua.Page);
