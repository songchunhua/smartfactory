Ext.define('MBI.view.NavFormlist', {
	extend: 'Ext.view.View',
	
	store: 'MBI.store.SecfundefNt',
	
	listeners: {
		render: function(view) {
			view.store.load();
		},
		itemclick: function(view, record, item, index, e, opt) {
			//SmartFactory.addContentView('MBI.view.FormDesign');
			SmartFactory.addContentView({
				xtype : 'mbi.formdesign',
				title : 'Form-' + record.get('func_id'),
				data : record,
				closable: true
			});
		}
	},
	
	autoScroll: true,
	
	cls: 'operation-list',
	//itemSelector: '.mbi_formlist_item',
	itemSelector: '.operation-list-item', //itemselector로 div 지정
	overItemCls: 'operation-list-item-hover',
	
	tpl:'<tpl for="."><div class="operation-list-item">[ MAP: {func_id} - {func_code}]</div></tpl>'
});