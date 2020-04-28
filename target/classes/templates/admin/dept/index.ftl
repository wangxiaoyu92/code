<!DOCTYPE HTML>
<html>

<head>
    <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">


        <title>部门列表</title>
        <meta name="keywords" content="">
        <meta name="description" content="">
    <title>系统管理</title>

    <link rel="stylesheet" href="https://cdn.bootcss.com/jquery-treegrid/0.2.0/css/jquery.treegrid.min.css">
    <link href="${ctx!}/assets/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
        <link href="${ctx!}/assets/css/font-awesome.css?v=4.4.0" rel="stylesheet">

        <link href="${ctx!}/assets/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
        <link href="${ctx!}/assets/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">

        <link href="${ctx!}/assets/css/animate.css" rel="stylesheet">
        <link href="${ctx!}/assets/css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">


                <div class="ibox ">
                    <div class="ibox-title">
                        <h5>部门管理</h5>
                        <input  id="parent.id" name="parent.id" >
                    </div>
                    <div class="ibox-content">
                        <p>
                        	<@shiro.hasPermission name="system:dept:add">
                        		<button class="btn btn-success " type="button" onclick="add();"><i class="fa fa-plus"></i>&nbsp;添加</button>
                        	</@shiro.hasPermission>
                        </p>
                        <hr>
                        <div class="row row-lg">
		                    <div class="col-sm-12">
		                        <!-- Example Card View -->
		                        <div class="example-wrap">
		                            <div class="example">
		                            	<table id="table"></table>
		                            </div>
		                        </div>
		                        <!-- End Example Card View -->
		                    </div>
	                    </div>
                    </div>
                </div>
                </div>
            </div>
        </div>

</body>
 <script src="${ctx!}/assets/js/jquery.min.js?v=2.1.4"></script>
<script src="https://cdn.bootcss.com/bootstrap-table/1.12.1/bootstrap-table.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-table/1.12.0/extensions/treegrid/bootstrap-table-treegrid.js"></script>
<script src="https://cdn.bootcss.com/jquery-treegrid/0.2.0/js/jquery.treegrid.min.js"></script>
 <script src="${ctx!}/assets/js/plugins/layer/layer.min.js"></script>
 <!-- 自定义js -->
     <script src="${ctx!}/assets/js/content.js?v=1.0.0"></script>









<script type="text/javascript">
    var $table = $('#table');
    var datanew =
        [ { "address":"713室", "createtime":"2017-04-23", "deptname":"研发部1", "description":"负责研发产品", "id":3, "number":"20", "parent_id":0, "sfsc":"0", "telephone":"0253-2111111" }, { "address":"715室", "createtime":"2016-04-23", "deptname":"人事部", "description":"负责人事工作", "id":4, "number":"5", "parent_id":0, "sfsc":"0", "telephone":"0263-21212112" }, { "address":"712室", "createtime":"2019-04-24", "deptname":"java开发", "description":"123", "id":5, "number":"10", "parent_id":3, "sfsc":"0", "telephone":"0273-12121331" }, { "address":"711", "createtime":"2019-04-24", "deptname":"行政部", "description":"2123", "id":6, "number":"3", "parent_id":4, "sfsc":"0", "telephone":"0273-121133311" }, { "address":"112", "createtime":"2019-04-25", "deptname":"11", "description":"223", "id":7, "number":"11", "parent_id":18, "sfsc":"0", "telephone":"11" }, { "address":"11", "createtime":"2019-04-10", "deptname":"111", "description":"223", "id":18, "number":"11", "parent_id":0, "sfsc":"0", "telephone":"11" }, { "address":"11", "createtime":"2019-04-18", "deptname":"223", "description":"111", "id":19, "number":"111", "parent_id":0, "sfsc":"0", "telephone":"11" }, { "address":"22", "createtime":"2019-04-18", "deptname":"财务部", "description":"113", "id":21, "number":"111", "parent_id":0, "sfsc":"0", "telephone":"33" }, { "address":"22", "createtime":"2019-04-05", "deptname":"冻豆腐", "description":"123", "id":22, "number":"11", "parent_id":5, "sfsc":"0", "telephone":"33" } ];

    $(function() {

     $.ajax({
                			type : "POST",
                			url : "${ctx!}/admin/dept/listnew",
                			dataType : 'json',
                			success : function(data) {
                				datanew=JSON.stringify(data);
                				$("input[name='parent.id']").val(datanew);

                			}
                		});


open();

    });

 function open(){
 $table.bootstrapTable({
             data:datanew,
             idField: 'id',
             dataType:'jsonp',
             columns: [
                 { field: 'check',  checkbox: true, formatter: function (value, row, index) {
                         if (row.check == true) {
                            // console.log(row.serverName);
                             //设置选中
                             return {  checked: true };
                         }
                     }
                 },

                 { field: 'deptname',  title: '部门名称' },
                 { field: 'cj',  title: '层级' },
                 { field: 'number',  title: '部门人数' },
                 { field: 'telephone', title: '部门电话'  },
                 { field: 'address',  title: '部门地址' },
                 { field: 'createtime',  title: '成立日期' },
                 { field: 'description', title: '部门描述'},
                 { title: "操作",
                                  			        field: "empty",
                                                      formatter: function (value, row, index) {
                                                      	var operateHtml = '<@shiro.hasPermission name="system:dept:add"><button class="btn btn-primary btn-xs" type="button" onclick="edit(\''+row.id+'\')"><i class="fa fa-edit"></i>&nbsp;修改</button> &nbsp;</@shiro.hasPermission>';
                                                      	operateHtml = operateHtml + '<@shiro.hasPermission name="system:dept:deleteBatch"><button class="btn btn-danger btn-xs" type="button" onclick="del(\''+row.id+'\')"><i class="fa fa-remove"></i>&nbsp;删除</button></@shiro.hasPermission>';
                                                          return operateHtml;
                                                      } },
             ],

             // bootstrap-table-treegrid.js 插件配置 -- start

             //在哪一列展开树形
             treeShowField: 'deptname',
             //指定父id列
             parentIdField: 'parent_id',

             onResetView: function(data) {
                 //console.log('load');
                 $table.treegrid({
                     initialState: 'collapsed',// 所有节点都折叠
                     // initialState: 'expanded',// 所有节点都展开，默认展开
                     treeColumn: 1,
                     // expanderExpandedClass: 'glyphicon glyphicon-minus',  //图标样式
                     // expanderCollapsedClass: 'glyphicon glyphicon-plus',
                     onChange: function() {
                         $table.bootstrapTable('resetWidth');
                     }
                 });

                 //只展开树形的第一级节点
                 $table.treegrid('getRootNodes').treegrid('expand');

             },
             onCheck:function(row){
                 var datas = $table.bootstrapTable('getData');
                 // 勾选子类
                 selectChilds(datas,row,"id","parent_id",true);

                 // 勾选父类
                 selectParentChecked(datas,row,"id","parent_id")

                 // 刷新数据
                 $table.bootstrapTable('load', datas);
             },

             onUncheck:function(row){
                 var datas = $table.bootstrapTable('getData');
                 selectChilds(datas,row,"id","parent_id",false);
                 $table.bootstrapTable('load', datas);
             },
             // bootstrap-table-treetreegrid.js 插件配置 -- end
         });

 }



</script>
<script>
    /**
     * 选中父项时，同时选中子项
     * @param datas 所有的数据
     * @param row 当前数据
     * @param id id 字段名
     * @param pid 父id字段名
     */
    function selectChilds(datas,row,id,parent_id,checked) {
        for(var i in datas){
            if(datas[i][parent_id] == row[id]){
                datas[i].check=checked;
                selectChilds(datas,datas[i],id,parent_id,checked);
            };
        }
    }

    function selectParentChecked(datas,row,id,parent_id){
        for(var i in datas){
            if(datas[i][id] == row[parent_id]){
                datas[i].check=true;
                selectParentChecked(datas,datas[i],id,parent_id);
            };
        }
    }

    function test() {
        var selRows = $table.bootstrapTable("getSelections");
        if(selRows.length == 0){
            alert("请至少选择一行");
            return;
        }

        var postData = "";
        $.each(selRows,function(i) {
            postData +=  this.id;
            if (i < selRows.length - 1) {
                postData += "， ";
            }
        });
        alert("你选中行的 id 为："+postData);

    }

    function add(){
            	layer.open({
            	      type: 2,
            	      title: '部门添加',
            	      shadeClose: true,
            	      shade: false,
            	      area: ['893px', '600px'],
            	      content: '${ctx!}/admin/dept/add',
            	      end: function(index){

           	    	  }
            	    });
            }

      function edit(id){

             	layer.open({
             	      type: 2,
             	      title: '部门修改',
             	      shadeClose: true,
             	      shade: false,
             	      area: ['893px', '600px'],
             	      content: '${ctx!}/admin/dept/edit/' + id,
             	      end: function(index){

            	    	  }
             	    });
             }

function del(id){
        	layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
        		$.ajax({
    	    		   type: "POST",
    	    		   dataType: "json",
    	    		   url: "${ctx!}/admin/dept/delete/" + id,
    	    		   success: function(msg){
	 	   	    			layer.msg(msg.message, {time: 2000},function(){

	 	   					});
    	    		   }
    	    	});
       		});
        }




</script>
</html>
