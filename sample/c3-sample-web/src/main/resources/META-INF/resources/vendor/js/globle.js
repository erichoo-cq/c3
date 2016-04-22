!function(window){
	
	$.fn.fixCSRFToken.need = false;
	
	$.fn.extend({
      attachment: function(options){
    	  return this.each(function(){
    		  var $this = $(this),
    		  	  datas = $this.data(),
    		  	  config = $.extend({
    		  		acceptFileTypes: '(\.|\/)(gif|jpe?g|png)$',
    		  		maxNumberOfFiles: 1,
    		  		messages: {
    	                maxNumberOfFiles: '上传文件数量过多',
    	                acceptFileTypes: '不支持上传此文件类型',
    	                maxFileSize: '上传文件太大',
    	                minFileSize: '上传文件太小'
    	            },
    		  		formData: {},
    		  		url: CTX + '/attachment/upload'
    		  	  }, datas, options),
		      	  $progress = $this.find('.progress'),
				  $items = $this.find('.items'),
				  $fileupload = $this.find('input'),
				  itemTemplate = '<li>\
					               <div class="alert alert-success m-b-xs p-xs">\
					                 <button type="button" class="close">\
					                   <span aria-hidden="true">&times;</span>\
					                 </button>\
					               </div>\
					             </li>';
    		  
    		  $items.on({
					click: function(){
						var $close = $(this);
						$close.closest('li').slideUp(500, function() {
							$(this).remove();
						});
					}
				}, 'li .close');
    		  
    		  $fileupload.fileupload({
    			  url: config.url,
    			  dataType: 'json',
    			  acceptFileTypes: ($.type(config.acceptFileTypes) == 'regexp') ? config.acceptFileTypes : new RegExp(config.acceptFileTypes, 'i'),
    			  maxNumberOfFiles: config.maxNumberOfFiles,
    			  messages: config.messages,
    			  formData: config.formData,
    			  done: function (e, data) {
                    var result = data.result,
                        $itemTemplate = $(itemTemplate),
                        $alert = $itemTemplate.find('.alert');
                    if(result && result.code === '0'){
                      $alert.append($('<input/>').attr({type:'hidden', name: 'attachment[]'}).val(result.id)).append(result.fileName);
                    }else{
                      $alert.append(result.desc);
                    }
                    $itemTemplate.appendTo($items);
    			  },
    			  process: function(e, data){
				  },
				  processdone: function(e, data){
				  },
				  processfail: function (e, data) {
					var files = data.files;
    				if(files && files.error){
    					var errors = $.map(files, function(file){
    						return file.error;
    					});
    					$.notify({
    				      "type": 'warning',
    				      "title": "错误提示",
    				      "text": errors.join('<br/>')
    				    });
    					return;
    				}
				  },
				  start: function(e){
				  	$progress.removeClass('hide');
				  },
				  stop: function(e){
				  	$progress.addClass('hide');
				  },
				  progress: function (e, data) {
				  },
				  progressall: function (e, data) {
				    var progress = parseInt(data.loaded / data.total * 100, 10);
				        $progress.find('.progress-bar').css('width', progress + '%').text(progress + '%');
				  },
				  fail: function(e, data){
				  }
    		  }).prop('disabled', !$.support.fileInput)
		        .parent().addClass($.support.fileInput ? undefined : 'disabled');
    	  });
      }
	});
	
	$(function(){
		//输入校验
		$(document).on('keyup',"input[data-validate-decimalplaces]",function(){
			/*if(!$(this).data('ruleDecimalplaces')){
				return false;
			}*/
			var txt = $(this).val(),str = txt,n=$(this).data('validateDecimalplaces');
			str = str.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'');
			if(n == 0){
				str = str.replace(/[^\d{1,}\d{1,}|\d{1,}]/g,'');
			} else {
				if (str.substring(0,1)=="."){
					str = "0"+str
				}
				var strs = str.split(".");
				if(strs.length-1 > 1){
					str = strs[0] + ".";
					for(var i=1;i<strs.length;i++){
						str += strs[i]
					}
				}
				if(str.split(".").length == 2){
					if(str.split(".")[1].length > n){
						str = str.substring(0,str.length-1);
					}
				}
			}
			if(txt != str){
				$(this).val(str)
			}
		});
	})
}(window);