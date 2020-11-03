$package("js.hera");

$include("js.hera.hub.Service");

/**
 * CategoriesPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of CategoriesPage class.
 */
js.hera.CategoriesPage = function() {
	this.$super();
	Service.getCategories(this._onCategories, this);

	this._doRemove = Service.deleteCategory;
	this._doCreate = Service.createCategory;
	this._doUpdate = Service.updateCategory;
};

js.hera.CategoriesPage.prototype = {
	_onCategories : function(categories) {
		this._tableView.setObject(categories);
	},

	_isRemoveable : function(category) {
		if (category.devicesCount > 0) {
			js.ua.System.alert("Category %s has %d devices and cannot be removed.", category.display, category.devicesCount);
			return false;
		}
		return true;
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "js.hera.CategoriesPage";
	}
};
$extends(js.hera.CategoriesPage, js.hera.TablePage);
