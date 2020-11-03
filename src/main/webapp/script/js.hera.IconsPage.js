$package("js.hera");

$include("js.hera.hub.Service");

/**
 * IconsPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of IconsPage class.
 */
js.hera.IconsPage = function() {
	this.$super();

	this._iconsListView = this.getByName("icons-list-view");
	this._iconsListView.setAutoSelect(js.widget.ListControl.Select.CLICK);
	this._iconsListView.on("item-select", this._onIconSelect, this);

	this._uploadDialog = this.getByName("upload-dialog");
	this._uploadForm = this._uploadDialog.getByTag("form");

	this._editDialog = this.getByName("edit-dialog");
	this._editForm = this._editDialog.getByTag("form");

	this._loadIcons();

	this.on(this, {
		"&upload" : this._onUpload,
		"&save" : this._onSave,
		"&remove" : this._onRemove,
		"&cancel" : this._onCancel
	});
};

js.hera.IconsPage.prototype = {
	_loadIcons : function() {
		this._reset();
		Service.getIcons(this._onIconsLoaded, this);
	},

	_onIconsLoaded : function(icons) {
		this._iconsListView.setObject(icons);
	},

	_onIconSelect : function(ev) {
		if (ev.selected) {
			this._editDialog.show();
			this._editForm.setObject(ev.value);
			this._uploadDialog.hide();
		}
	},

	_onUpload : function() {
		if (this._uploadForm.isValid()) {
			Service.uploadIcon(this._uploadForm, this._loadIcons, this);
		}
	},

	_onRemove : function() {
		var iconName = this._iconsListView.getValue().name;
		Service.isIconUsed(iconName, function(used) {
			if (used) {
				js.ua.System.alert("Cannot remove used icon.");
				this._reset();
			}
			else {
				js.ua.System.confirm("Please confirm icon remove.", function(ok) {
					if (ok) {
						Service.removeIcon(iconName, this._loadIcons, this);
					}
					else {
						this._reset();
					}
				}, this);
			}
		}, this);
	},

	_onSave : function() {
		if (this._editForm.isValid()) {
			Service.updateIconName(this._iconsListView.getValue().name, this._editForm.getObject().name, this._loadIcons, this);
		}
	},

	_onCancel : function() {
		this._reset();
	},

	_reset : function() {
		this._editDialog.hide();
		this._uploadDialog.show();
		this._uploadForm.reset();
		this._editForm.reset();
		this._iconsListView.deselectAll();
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.hera.IconsPage";
	}
};
$extends(js.hera.IconsPage, js.ua.Page);
