/**
 * 异步提交表单.
 * @param id 表单id
 * @param validateOptions 验证参数
 * @param success 成功后callback
 * @param beforeSubmit 提交前callback
 * @param url 提交的url，默认表单的action
 */
function validateAndSubmit(id, validateOptions, success, beforeSubmit, url, errorPlacement) {
	$.validator.setDefaults({ ignore: ":hidden:not(select)" });
	var formId;
	if (id.indexOf("#") == 0) {
		formId = id;
	} else {
		formId = "#" + id;
	}
	var rules;
	var messages;
	if (validateOptions) {
		rules = validateOptions.rules || {}; 
		messages = validateOptions.messages || {};
	}
	var v = $(formId).validate({
			rules : rules,
			messages : messages,
			submitHandler: function(form) {
				ajaxFormSubmit(id, success, beforeSubmit, url);
			},
		   // errorPlacement: errorPlacement
			errorPlacement: function(error, element) {
				console.log(element);
				if(element.hasClass('chosen-select')) {
					var id = element.attr("id");
					$("#"+id+"_chosen a").addClass("error");
				}
				error.appendTo(element.parent());
			}
		});
}

/**
 * 异步提交表单.
 * @param id 表单id
 * @param success 成功后callback
 * @param beforeSubmit 提交前callback
 * @param url 提交的url，默认表单的action
 */
function ajaxFormSubmit (id, success, beforeSubmit, url) {
	var t;
	var timeout = 10000;
	if('undefined' != typeof layer) {
		var loadIndex = layer.load();
		t=setTimeout(function(){
			layer.close(loadIndex);
		}, timeout);
		var complete = function() {
			layer.close(loadIndex);
			clearTimeout(t);
		}
	}
	var formId;
	if (id.indexOf("#") == 0) {
		formId = id;
	} else {
		formId = "#" + id;
	}
	var formUrl = url || $(formId).attr('action');
	var options = {
		type : "post",
		url : formUrl,
		dataType : "json",
		beforeSubmit : function(a, b, c) {
			if($.isFunction(beforeSubmit)) {
				var submit = beforeSubmit(a, b, c);
				if(!submit) {
					complete();
				}
				return submit;
			}
		},
		success:success,
		complete: complete,
		/*function() {
			//关闭
			layer.close(loadIndex); 
		},*/
		timeout: timeout
	};
	var forsubmit = $(formId).ajaxSubmit(options);
	console.log(forsubmit);
}

