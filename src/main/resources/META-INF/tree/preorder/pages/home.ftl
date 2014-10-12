<#import "html.ftl" as html>
<!DOCTYPE html>
<html>
<@html.head title="数据库中的前序树">
<link rel="stylesheet" href="${pluginPath}/static/jstree/themes/default/style.min.css"/>
<script type="text/javascript" src="${pluginPath}/static/jstree/jstree.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('#tree').jstree({
            'core': {
                'data': {
                    'url': function (node) {
                        console.log(node)
                        return "${pluginPath}/tree/load";
                    },
                    'data': function (node) {
                        return { 'id': node.id };
                    }
                }
            }
        });
    });

</script>
</@html.head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <div class="row">
                <div class="col-md-4 col-sm-8 col-xs-8">
                    <button type="button" class="btn btn-success btn-sm" onclick="demo_create();">
                        <i class="glyphicon glyphicon-asterisk"></i> Create
                    </button>
                    <button type="button" class="btn btn-warning btn-sm" onclick="demo_rename();">
                        <i class="glyphicon glyphicon-pencil"></i> Rename
                    </button>
                    <button type="button" class="btn btn-danger btn-sm" onclick="demo_delete();">
                        <i class="glyphicon glyphicon-remove"></i> Delete
                    </button>
                </div>
            <#--<div class="col-md-2 col-sm-4 col-xs-4" style="text-align:right;">-->
            <#--<input type="text" value=""-->
            <#--style="box-shadow:inset 0 0 4px #eee; width:120px; margin:0; padding:6px 12px; border-radius:4px; border:1px solid silver; font-size:1.1em;"-->
            <#--id="demo_q" placeholder="Search">-->
            <#--</div>-->
            </div>
        </div>
        <div class="panel-body">
            <div class="row">
                <div id="tree" class="col-md-12">
                    <ul>
                        <li>Root node 1
                            <ul>
                                <li id="child_node_1">Child node 1</li>
                                <li>Child node 2</li>
                            </ul>
                        </li>
                        <li>Root node 2</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>