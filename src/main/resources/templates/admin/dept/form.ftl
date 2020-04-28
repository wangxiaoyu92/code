<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - 表单验证 jQuery Validation</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${ctx!}/assets/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx!}/assets/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctx!}/assets/css/animate.css" rel="stylesheet">
    <link href="${ctx!}/assets/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="${ctx!}/assets/css/plugins/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet">


</head>

<body class="gray-bg" style="height:100%;">
    <div class="wrapper wrapper-content animated fadeInRight">

        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>部门表单</h5>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/admin/dept/edit">
                        	<input type="hidden" id="id" name="id" value="${dept.id}">
                        	<input type="hidden" id="parent.id" name="parent.id" value="${dept.parent.id}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">部门名称：</label>
                                <div class="col-sm-8">
                                    <input id="deptname" name="deptname" class="form-control" type="text" value="${dept.deptname}" >
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-3 control-label">部门人数：</label>
                                <div class="col-sm-8">
                                    <input id="number" name="number" class="form-control" type="text" value="${dept.number}">
                                </div>
                            </div>
                            <div class="form-group">
                                  <label class="col-sm-3 control-label">部门地址：</label>
                                  <div class="col-sm-8">
                                       <input id="address" name="address" class="form-control" type="text" value="${dept.address}">
                                  </div>
                             </div>
                             <div class="form-group">
                                   <label class="col-sm-3 control-label">部门电话：</label>
                                   <div class="col-sm-8">
                                        <input id="telephone" name="telephone" class="form-control" type="text" value="${dept.telephone}">
                                   </div>
                              </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">成立日期：</label>
                                <div class="col-sm-8">
                                    <input id="createtime" name="createtime" readonly="readonly" class="laydate-icon form-control layer-date" value="${dept.createtime}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">部门描述：</label>
                                <div class="col-sm-8">
                                    <input id="description" name="description" class="form-control" value="${dept.description}">
                                </div>
                            </div>
                             <div class="form-group">
                                                            <label class="col-sm-3 control-label">上级部门：</label>
                                                         <div class="col-sm-8">
                                                                                                                              <ul id="tree" class="ztree"></ul>
                                                                                      </div>
                                                        </div>

                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <button class="btn btn-primary" type="submit">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </div>


    <!-- 全局js -->
    <script src="${ctx!}/assets/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctx!}/assets/js/bootstrap.min.js?v=3.3.6"></script>

    <!-- 自定义js -->
    <script src="${ctx!}/assets/js/content.js?v=1.0.0"></script>

    <!-- jQuery Validation plugin javascript-->
    <script src="${ctx!}/assets/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${ctx!}/assets/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${ctx!}/assets/js/plugins/layer/layer.min.js"></script>
    <script src="${ctx!}/assets/js/plugins/layer/laydate/laydate.js"></script>
    <script src="${ctx!}/assets/js/plugins/zTree/jquery.ztree.all.min.js"></script>
    <script type="text/javascript">

	var setting = {
		check : {
			enable : true,
			chkStyle: "radio",
                                radioType: "all"
		},
		data : {
			simpleData : {
				enable : true
			}
		},async:{
                    			enable:true,
                    			url:"${ctx!}/admin/dept/tree/" + 0,
                    			autoParam:["id"],
                    			dataType:"json",
                    		},
                    		view: {
                    			selectedMulti: false
                    		}
	};
	setting.check.chkboxType = {
		"Y" : "",
		"N" : ""
	};
    $(document).ready(function () {


            $.ajax({
            			type : "POST",
            			url : "${ctx!}/admin/dept/tree/" + 0,
            			dataType : 'json',
            			success : function(msg) {
            				$.fn.zTree.init($("#tree"), setting, msg);
            				var treeObj = $.fn.zTree.getZTreeObj("tree");
            				var id="${dept.parent.id}";
                                                 var node = treeObj.getNodeByParam("id", id);
                                                  treeObj.checkNode(node, true, true);
            			}
            		});



	  	//外部js调用
	    laydate({
	        elem: '#createtime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	        event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	    });

	  	
	    $("#frm").validate({
    	    rules: {
    	    	deptname: {
    	        required: true,
    	        minlength: 2,
    	    	maxlength: 10
    	      },
    	      	createtime: {
    	      	date:true,
    	        required: true
    	      },
    	      	address: {
    	        required: true,
    	        maxlength: 40
    	      },
    	      	description: {
    	        required: true,
    	        maxlength: 40
    	      }
    	    },
    	    messages: {},
    	    submitHandler:function(form){
    	    var treeObj = $.fn.zTree.getZTreeObj("tree");
            			var nodes = treeObj.getCheckedNodes(true);
            			var selectIds="";
            			for(var index in nodes){
            			$("input[name='parent.id']").val(nodes[index].id);

            			}
               $.ajax({
   	    		   type: "POST",
   	    		   dataType: "json",
   	    		   url: "${ctx!}/admin/dept/edit",
   	    		   data: $(form).serialize(),
   	    		   success: function(msg){
	   	    			layer.msg(msg.message, {time: 2000},function(){
	   						var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	   						parent.layer.close(index);
	   					});
   	    		   }
   	    		});
            } 
    	});
    });
    </script>

</body>

</html>
