Ext.define('WIP.controller.WIPController', {
	extend : 'Ext.app.Controller',

	stores : [ 'WIP.store.OperationStore' ],
	models : [ 'WIP.model.Operation' ],
	views : [ 'WIP.view.oper.Operation' ],

	init : function() {
		this.control({
			'viewport' : {
				afterrender : this.onViewportRendered
			}
		});
	},

	onViewportRendered : function() {
		SmartFactory.addNav('WIP.view.NavOperation', {
			iconCls : 'iconsetDockOperation',
			itemId : 'navOperation',
			title : 'operation'
		});

		SmartFactory.selector.register('Operation', {
			title : 'Select Operation',
			selects : [ 'FACTORY', 'OPER', 'OPER_DESC' ],
			sorters : [ {
				property : 'OPER',
				direction : 'ASC'
			} ],
			table : 'MWIPOPRDEF',
			columns : [ {
				header : 'Operation',
				dataIndex : 'OPER',
				flex : 1
			}, {
				header : 'Description',
				dataIndex : 'OPER_DESC',
				flex : 2
			} ],
			valueField : 'OPER',  //submit value
			displayField : ['OPER', 'OPER_DESC'] //display value
		});

		SmartFactory.selector.register('Material', {
			title : 'Select Material',
			selects : [ 'FACTORY', 'MAT_ID', 'MAT_VER', 'MAT_DESC' ],
			sorters : [ {
				property : 'MAT_ID',
				direction : 'ASC'
			} ],
			table : 'MWIPMATDEF',
			columns : [ {
				header : 'Material',
				dataIndex : 'MAT_ID',
				flex : 2
			}, {
				header : 'Version',
				dataIndex : 'MAT_VER',
				flex : 1
			}, {
				header : 'Description',
				dataIndex : 'MAT_DESC',
				flex : 3
			} ],
			valueField : ['MAT_ID', 'MAT_VER']
		});
	}
});