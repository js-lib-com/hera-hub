$package("js.hera");

$include("js.hera.hub.Service");

js.hera.AutomataPage = function () {
	this.$super();

	this._actionsListView = this.getByName("actions-list-view");
	this._actionsListView.setAutoSelect(js.widget.ListControl.Select.CLICK);
	this._actionsListView.on("item-select", this._onActionSelect, this);

	this._actionDialog = this.getByName("action-dialog");
	this._actionForm = this._actionDialog.getByTag("form");

	this._rulesListView = this.getByName("rules-list-view");
	this._rulesListView.setAutoSelect(js.widget.ListControl.Select.CLICK);
	this._rulesListView.on("item-select", this._onRuleSelect, this);

	this._ruleDialog = this.getByName("rule-dialog");
	this._ruleForm = this._ruleDialog.getByTag("form");

	this.getByTag("textarea").on("keydown", function (ev) {
		if (ev.key == 9) {
			ev.halt();

			var textarea = ev.target._node;
			var selectionStart = textarea.selectionStart;
			textarea.value = textarea.value.substring(0, textarea.selectionStart) + "\t" + textarea.value.substring(textarea.selectionEnd);
			textarea.selectionEnd = selectionStart + 1;
		}
	});

	this._loadActions();
	this._loadRules();

	Service.getDevices(function (devices) {
		var deviceNames = devices.map(function (device) { return device.name; });
		deviceNames.unshift("");
		this._ruleForm.getByName("events.0.deviceName").setOptions(deviceNames);
		this._ruleForm.getByName("events.1.deviceName").setOptions(deviceNames);
	}, this);

	Service.getIconNames(function (icons) {
		this._actionForm.getByName("icon").setOptions(icons);
		this._ruleForm.getByName("icon").setOptions(icons);
	}, this);

	this.on(this, {
		"&create-action": this._onCreateAction,
		"&save-action": this._onSaveAction,
		"&remove-action": this._onRemoveAction,
		"&cancel-action": this._onCancelAction,
		"&create-rule": this._onCreateRule,
		"&save-rule": this._onSaveRule,
		"&remove-rule": this._onRemoveRule,
		"&cancel-rule": this._onCancelRule
	});
};

js.hera.AutomataPage.prototype = {
	_loadActions: function () {
		Service.getActions(this._onActionsLoaded, this);
	},

	_onActionsLoaded: function (actions) {
		this._ruleForm.getByName("actionClassName").setOptions(actions.map(function (action) { return action.className; }));

		var index = 0;
		actions.forEach(function (action) {
			action.id = ++index;
			action.picture = this._picture(action.icon);
		}.bind(this));
		this._actionsListView.setObject(actions);
	},

	_onActionSelect: function (ev) {
		if (ev.selected) {
			this._actionDialog.show();
			this._actionForm.setObject(ev.value);
		}
	},

	_onCreateAction: function () {
		js.ua.System.prompt("Action Display", function (actionDisplay) {
			Service.createActionCode(actionDisplay, function (action) {
				this._actionDialog.show();
				this._actionForm.setObject(action);
			}, this);
		}, this);
	},

	_onSaveAction: function () {
		if (this._actionForm.isValid()) {
			var action = this._actionForm.getObject();
			Service.saveAction(action, function () {
				this._loadActions();
				this._onCancelAction();
			}, this);
		}
	},

	_onRemoveAction: function () {
		Service.removeAction(this._actionForm.getObject().className, function () {
			this._actionsListView.removeSelected();
			this._onCancelAction();
		}, this);
	},

	_onCancelAction: function () {
		this._actionDialog.hide();
		this._actionForm.reset();
		this._actionsListView.deselectAll();
	},

	// --------------------------------------------------------

	_loadRules: function () {
		Service.getRules(this._onRulesLoaded, this);
	},

	_onRulesLoaded: function (rules) {
		var index = 0;
		rules.forEach(function (rule) {
			rule.id = ++index;
			rule.picture = this._picture(rule.icon);
		}.bind(this));
		this._rulesListView.setObject(rules);
	},

	_onRuleSelect: function (ev) {
		if (ev.selected) {
			var rule = ev.value;
			this._ruleDialog.show();
			this._ruleForm.setObject(rule);
		}
	},

	_onCreateRule: function () {
		this._ruleDialog.show();
		this._ruleForm.reset();
	},

	_onSaveRule: function () {
		if (this._ruleForm.isValid()) {
			var rule = this._rulesListView.getValue();
			if (rule == null) {
				rule = {};
			}
			Service.saveRule(this._ruleForm.getObject(rule), function () {
				this._loadRules();
				this._onCancelRule();
			}, this);
		}
	},

	_onRemoveRule: function () {
		Service.removeRule(this._ruleForm.getObject().name, function () {
			this._rulesListView.removeSelected();
			this._onCancelRule();
		}, this);
	},

	_onCancelRule: function () {
		this._ruleDialog.hide();
		this._rulesListView.deselectAll();
	},

	// --------------------------------------------------------

	_picture: function (icon) {
		return $format("/icons/%s.png", icon);
	},

	toString: function () {
		return "js.hera.AutomataPage";
	}
};
$extends(js.hera.AutomataPage, js.ua.Page);
