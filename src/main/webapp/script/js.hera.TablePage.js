$package("js.hera");

/**
 * TablePage abstract class.
 * 
 * @author Iulian Rotaru
 * 
 * @constructor Construct an instance of TablePage class.
 */
js.hera.TablePage = function() {
	this.$super();

	this._tableView = this.getByClass(js.widget.TableView);
	this._tableView.on("selection-update", this._onSelectionUpdate, this);

	this._dialog = this.getByName("dialog");
	this._form = this.getByTag("form");

	this._editButton = this.getByName("edit").hide();
	this._removeButton = this.getByName("remove").hide();

	this.on(this, {
		"&create" : this._onCreate,
		"&edit" : this._onEdit,
		"&remove" : this._onRemove,
		"&save" : this._onSave,
		"&cancel" : this._onCancel
	});

	this.findByCss("[data-load]").forEach(function(item) {
		item.toString();
	});
};

js.hera.TablePage.prototype = {
	/**
	 * Remove object from server.
	 * 
	 * @param Number objectId object ID,
	 * @param Function callback function executed on remove complete,
	 * @param Object scope callback execution scope.
	 */
	_doRemove : function(objectId, callback, scope) {
		throw "Should override js.hera.TablePage#_doRemove";
	},

	/**
	 * Create object on server.
	 * 
	 * @param Object object object to create, with zero ID,
	 * @param Function callback function executed on create complete,
	 * @param Object scope callback execution scope.
	 */
	_doCreate : function(object, callback, scope) {
		throw "Should override js.hera.TablePage#_doCreate";
	},

	/**
	 * Update object on server.
	 * 
	 * @param Object object object to update, with non zero ID,
	 * @param Function callback function executed on update complete,
	 * @param Object scope callback execution scope.
	 */
	_doUpdate : function(object, callback, scope) {
		throw "Should override js.hera.TablePage#_doUpdate";
	},

	/**
	 * Test if objects are removable.
	 * 
	 * @param Array objects objects list.
	 * @return Boolean true if all objects are removable.
	 */
	_isRemoveable : function(objects) {
		return true;
	},

	_onSelectionUpdate : function(selectedItems) {
		this._editButton.show(selectedItems.length);
		this._removeButton.show(selectedItems.length);
	},

	_onCreate : function(ev) {
		this._create = true;
		this._dialog.show();
		this._form.reset();
	},

	_onEdit : function(ev) {
		this._create = false;
		this._removeButton.hide();
		this._dialog.show();
		this._form.setObject(this._tableView.getValue());
	},

	_onRemove : function(ev) {
		if (this._isRemoveable(this._tableView.getValue())) {
			js.ua.System.confirm("Remove selected item?", function(ok) {
				if (ok) {
					this._doRemove(this._tableView.getValue().id, function() {
						this._tableView.removeSelected();
						this._editButton.hide();
						this._removeButton.hide();
					}, this);
				}
			}, this);
		}
	},

	_onSave : function(ev) {
		if (!this._form.isValid()) {
			return;
		}

		var object;

		if (this._create) {
			object = this._form.getObject();
			this._doCreate(object, function(object) {
				this._dialog.hide();
				this._tableView.add(object);
			}, this);
		}
		else {
			object = this._form.getObject(this._tableView.getValue());
			this._doUpdate(object, function(object) {
				this._dialog.hide();
				this._tableView.update(object);
				this._tableView.deselectAll();
			}, this);
		}
	},

	_onCancel : function(ev) {
		this._dialog.hide();
		this._tableView.deselectAll();
		this._editButton.hide();
		this._removeButton.hide();
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.hera.TablePage";
	}
};
$extends(js.hera.TablePage, js.ua.Page);
