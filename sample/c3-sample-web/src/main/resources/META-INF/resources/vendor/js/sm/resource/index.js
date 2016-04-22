!function() {

	var TREE_ROOT_ID = 1,
	    CURRENT_NODE_ID = TREE_ROOT_ID,
	    loadMenuList = function(parentId, page) {
		    CURRENT_NODE_ID = parentId+'' || CURRENT_NODE_ID;
			var params = {
			  id : CURRENT_NODE_ID
			};
			if(page){
				$.extend(params, {
					page: page
				});
			}
			$.get(CTX + '/sm/resource/menu/page', params, function(data) {
				$('#menu-page').html(data);
				
				$('#sm-resource #menu-tree').parent().css({
					height: $('#menu-page').parent().parent().height(),
					'overflow-y': 'auto'
				});
			});
		};

	$(function() {
		var $dialog = $('#dialog'), 
		    $res = $('#sm-resource'), 
		    $add = $res.find('a#add'), 
		    $tree = $res.find('#menu-tree'), 
		    $menuPage = $('#menu-page');
		
		$tree.tree({
			dataSource : function(node, callback) {
				var params = {};
				if (node && node.resourceId) {
					var resourceId = node.resourceId;
					$.extend(params, {
						id : resourceId
					});
					//loadMenuList(resourceId);
				}
				$.get(CTX + '/sm/resource/menus', params, function(data) {
					data = $.map(data, function(o){
						var attrs = {
						  id: 'tree-node-' + o.resourceId
						};
						if(o.type == 'item'){
							$.extend(attrs, {
								'data-icon': 'glyphicon glyphicon-file'
							});
						}
						return $.extend(o, {
							attr: attrs
						});
					});
					callback({
						data : data
					});
				});
			},
			cacheItems : true,
			folderSelect : true,
			multiSelect : false
		});
		
		//分页
		$menuPage.on('click', '.pagination li:not(.disabled, .active)>a', function(){
			var $this = $(this),
			    page = $this.data('page');
			loadMenuList(CURRENT_NODE_ID, page);
			return false;
		});
		
		$tree.on('selected.fu.tree', function (event, data) {
			var item = data.target;
			
			if(item.type == 'item'){
				return;
			}
			loadMenuList(item.resourceId);
		});
		
		$tree.on('refreshedFolder.fu.tree', function (event, data) {
			if(data.type == 'item'){
				return;
			}
			loadMenuList(data.resourceId);
		});
		
		$tree.on('loaded.fu.tree', function(event, el){
			if($(el).is('#menu-tree')){
				$tree.tree('openFolder', $('#tree-node-' + TREE_ROOT_ID));
			}
		});

		$add.on('click', function() {
			var items = $tree.tree('selectedItems');
			if (items.length === 0) {
				$.notify({
					"type" : 'warning',
					"title" : "提示消息",
					"text" : "请选择一个资源"
				});
				return;
			}
			var item = items[0], resourceId = item.resourceId;
			
			if (item.type === 'item') {
				$.notify({
					"type" : 'warning',
					"title" : "提示消息",
					"text" : "不能在叶子节点增加"
				});
				return;
			}
			
			$.get(CTX + '/sm/resource/menu/new', {
				id : resourceId
			}, function(data) {
				$dialog.find('.modal-content').html(data);
				$dialog.modal({
					backdrop : 'static',
					show : true
				});
			});
		});

		$dialog.on('show.bs.modal', function() {
			var $this = $(this);
			$this.find('form').validate({
				submitHandler: function(form){
					var params = $(form).serializeArray();
					$.post($(form).attr('action'), params, function(data){
						$dialog.find('.modal-content').html(data);
					});
					return false;
				}
			});
		});
		
		$menuPage.on('click', '#edit', function(){
			var $this = $(this),
				href = $this.attr('href');
			$.get(href, function(data){
				$dialog.find('.modal-content').html(data);
				$dialog.modal({
					backdrop : 'static',
					show : true
				});
			});
			return false;
		});
		
		//$tree.tree('refreshFolder', $('#tree-node-100'));
		loadMenuList(TREE_ROOT_ID);
	});

}();